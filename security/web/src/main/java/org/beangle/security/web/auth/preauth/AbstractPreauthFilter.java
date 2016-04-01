/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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
package org.beangle.security.web.auth.preauth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.commons.lang.Assert;
import org.beangle.commons.web.filter.GenericHttpFilter;
import org.beangle.security.auth.AccountStatusException;
import org.beangle.security.auth.AnonymousAuthentication;
import org.beangle.security.auth.AuthenticationDetailsSource;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.AuthenticationException;
import org.beangle.security.core.context.SecurityContextHolder;
import org.beangle.security.core.userdetail.UsernameNotFoundException;
import org.beangle.security.web.auth.AbstractAuthenticationFilter;
import org.beangle.security.web.auth.AuthenticationService;
import org.beangle.security.web.auth.WebAuthenticationDetailsSource;

/**
 * Base class for processing filters that handle pre-authenticated
 * authentication requests. Subclasses must implement the
 * getPreAuthenticatedPrincipal() and getPreAuthenticatedCredentials() methods.
 * <p>
 * By default, the filter chain will proceed when an authentication attempt fails in order to allow
 * other authentication mechanisms to process the request. To reject the credentials immediately,
 * set the <tt>continueFilterChainOnUnsuccessfulAuthentication</tt> flag to false. The exception
 * raised by the <tt>AuthenticationManager</tt> will the be re-thrown. Note that this will not
 * affect cases where the principal returned by {@link #getPreauthAuthentication} is null, when the
 * chain will still proceed as normal.
 */
public abstract class AbstractPreauthFilter extends GenericHttpFilter {

  private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

  private AuthenticationService authenticationService = null;

  private AuthenticationAliveChecker authenticationAliveChecker;

  private boolean enableAliveCheck = false;

  /** fail for invalid cookie/ticket etc. */
  private boolean continueOnFail = true;

  /** Check whether all required properties have been set. */
  protected void initFilterBean() {
    Assert.notNull(authenticationService, "authenticationService must be set");
  }

  /**
   * 是否应该尝试进行认证
   * 
   * @param request
   * @param response
   */
  protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null) return true;
    if (!(auth instanceof PreauthAuthentication)) return false;
    if (enableAliveCheck && null != authenticationAliveChecker
        && !authenticationAliveChecker.check(auth, request)) {
      unsuccessfulAuthentication(request, response, null);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Try to authenticate a pre-authenticated user if the
   * user has not yet been authenticated.
   */
  public final void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
      ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;
    boolean requireAuth = requiresAuthentication(request, response);
    if (requireAuth) {
      Authentication newAuth = doAuthenticate(request, response);
      // Create anonymous authentication
      if (null == newAuth) {
        AnonymousAuthentication anonymous = AnonymousAuthentication.Instance;
        SecurityContextHolder.getContext().setAuthentication(anonymous);
        logger.debug("Populated SecurityContextHolder with anonymous token: '{}'", anonymous);
      }
    }
    try {
      chain.doFilter(req, res);
    } finally {
      if (requireAuth
          && SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthentication) {
        SecurityContextHolder.getContext().setAuthentication(null);
      }
    }
  }

  /** Do the actual authentication for a pre-authenticated user. */
  private Authentication doAuthenticate(HttpServletRequest request, HttpServletResponse response) {
    Authentication authResult = null;
    PreauthAuthentication auth = getPreauthAuthentication(request, response);
    if (auth == null) {
      logger.debug("No pre-authenticated principal found in request");
      return null;
    } else {
      logger.debug("trying to authenticate preauth={}", auth);
    }
    try {
      auth.setDetails(authenticationDetailsSource.buildDetails(request));
      authResult = authenticationService.login(request, auth);
      successfulAuthentication(request, response, authResult);
      return authResult;
    } catch (AuthenticationException failed) {
      unsuccessfulAuthentication(request, response, failed);
      if (!continueOnFail) throw failed;
      else return null;
    }
  }

  /**
   * Puts the <code>Authentication</code> instance returned by the
   * authentication manager into the secure context.
   */
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      Authentication authResult) {
    logger.debug("PreAuthentication success: {}", authResult);
    SecurityContextHolder.getContext().setAuthentication(authResult);
  }

  /**
   * Ensures the authentication object in the secure context is set to null when authentication
   * fails.
   * If username not found or account status exception.just let other know by throw it.
   * It will be handled by ExceptionTranslationFilter
   */
  protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException failed) {
    SecurityContextHolder.clearContext();
    if (null != failed) {
      logger.debug("Cleared security context due to exception", failed);
      request.getSession().setAttribute(AbstractAuthenticationFilter.SECURITY_LAST_EXCEPTION_KEY, failed);
      if (failed instanceof UsernameNotFoundException || failed instanceof AccountStatusException) { throw failed; }
    }
  }

  /**
   * @param authenticationDetailsSource
   */
  public void setAuthenticationDetailsSource(
      AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
    Assert.notNull(authenticationDetailsSource, "AuthenticationDetailsSource required");
    this.authenticationDetailsSource = authenticationDetailsSource;
  }

  public void setAuthenticationService(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  public void setContinueOnFail(boolean continueOnFail) {
    this.continueOnFail = continueOnFail;
  }

  public void setAuthenticationAliveChecker(AuthenticationAliveChecker authenticationAliveChecker) {
    this.authenticationAliveChecker = authenticationAliveChecker;
  }

  public boolean isEnableAliveCheck() {
    return enableAliveCheck;
  }

  public void setEnableAliveCheck(boolean enableAliveCheck) {
    this.enableAliveCheck = enableAliveCheck;
  }

  /** Override to extract the principal information from the current request */
  protected abstract PreauthAuthentication getPreauthAuthentication(HttpServletRequest request,
      HttpServletResponse response);
}
