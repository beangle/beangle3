/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.cas.auth;

import static org.beangle.security.cas.auth.CasAuthentication.STATELESS_ID;

import org.beangle.commons.bean.Initializing;
import org.beangle.commons.lang.Assert;
import org.beangle.commons.lang.Strings;
import org.beangle.security.auth.AuthenticationProvider;
import org.beangle.security.auth.BadCredentialsException;
import org.beangle.security.cas.validation.Assertion;
import org.beangle.security.cas.validation.TicketValidationException;
import org.beangle.security.cas.validation.TicketValidator;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.AuthenticationException;
import org.beangle.security.core.userdetail.AccountStatusChecker;
import org.beangle.security.core.userdetail.UserDetail;
import org.beangle.security.core.userdetail.UserDetailChecker;
import org.beangle.security.core.userdetail.UserDetailService;
import org.beangle.security.core.userdetail.UsernameNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An {@link AuthenticationProvider} implementation that integrates with JA-SIG
 * Central Authentication Service (CAS).
 * <p>
 * This <code>AuthenticationProvider</code> is capable of validating UsernamePasswordAuthentication
 * requests which contain a <code>principal</code> name equal to either
 * {@link CasAuthentication#STATEFUL_ID} or {@link CasAuthentication#STATELESS_ID} . It can also
 * validate a previously created {@link CasAuthentication}.
 * 
 * @author chaostone
 */
public class CasAuthenticationProvider implements AuthenticationProvider, Initializing {
  private static final Logger logger = LoggerFactory.getLogger(CasAuthenticationProvider.class);
  private UserDetailService userDetailService;
  private UserDetailChecker userDetailChecker;
  private StatelessTicketCache statelessTicketCache = new NullTicketCache();
  private String key = "an_id_for_this_auth_provider_only";
  private TicketValidator ticketValidator;

  public void init() throws Exception {
    Assert.notNull(this.userDetailService, "A userDetailsService must be set");
    Assert.notNull(this.ticketValidator, "A ticketValidator must be set");
    Assert.notNull(this.statelessTicketCache, "A statelessTicketCache must be set");
    Assert.notEmpty(this.key,
        "A Key is required so CasAuthenticationProvider can identify tokens it previously authenticated");
    if (null == userDetailChecker) userDetailChecker = new AccountStatusChecker();
  }

  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    CasAuthentication casauth = (CasAuthentication) authentication;
    // If an existing CasAuthenticationToken, just check we created it
    if (casauth.isAuthenticated()) {
      if (key.hashCode() == casauth.getKeyHash()) {
        return authentication;
      } else {
        throw new BadCredentialsException("CasAuthenticationProvider.incorrectKey");
      }
    }
    // Ensure credentials are presented
    if (Strings.isEmpty(String.valueOf(casauth.getCredentials()))) { throw new BadCredentialsException(
        "CasAuthenticationProvider.noServiceTicket"); }
    boolean stateless = false;
    if (STATELESS_ID.equals(casauth.getPrincipal())) {
      stateless = true;
    }
    CasAuthentication result = null;
    // Try to obtain from cache
    if (stateless) {
      result = statelessTicketCache.get(casauth.getCredentials().toString());
    }
    if (result == null) {
      result = authenticateNow(casauth);
      result.setDetails(casauth.getDetails());
    }
    // Add to cache
    if (stateless) {
      statelessTicketCache.put(result);
    }
    return result;
  }

  private CasAuthentication authenticateNow(CasAuthentication auth) throws AuthenticationException {
    try {
      final Assertion assertion = ticketValidator.validate(auth.getCredentials().toString(),
          auth.getLoginUrl());
      String name = assertion.getPrincipal();
      final UserDetail userDetail = userDetailService.loadDetail(name);
      if (null == userDetail) {
        logger.error("cannot load {}'s detail from system", name);
        throw new UsernameNotFoundException(Strings.concat("user ", name, " not found in system"));
      }
      userDetailChecker.check(userDetail);
      return new CasAuthentication(key, userDetail, auth.getCredentials(), userDetail.getAuthorities(),
          userDetail, assertion);
    } catch (final TicketValidationException e) {
      throw new BadCredentialsException("Bad credentials :" + auth.getCredentials().toString(), e);
    }
  }

  protected UserDetailService getUserDetailService() {
    return userDetailService;
  }

  public void setUserDetailService(UserDetailService userDetailsService) {
    this.userDetailService = userDetailsService;
  }

  protected String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public StatelessTicketCache getStatelessTicketCache() {
    return statelessTicketCache;
  }

  protected TicketValidator getTicketValidator() {
    return ticketValidator;
  }

  public void setStatelessTicketCache(StatelessTicketCache statelessTicketCache) {
    this.statelessTicketCache = statelessTicketCache;
  }

  public void setTicketValidator(TicketValidator ticketValidator) {
    this.ticketValidator = ticketValidator;
  }

  public boolean supports(final Class<? extends Authentication> authentication) {
    if (CasAuthentication.class.isAssignableFrom(authentication)) {
      return true;
    } else {
      return false;
    }
  }
}
