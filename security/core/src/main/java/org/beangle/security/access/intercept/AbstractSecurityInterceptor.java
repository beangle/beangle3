/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.access.intercept;

import org.beangle.commons.bean.Initializing;
import org.beangle.commons.lang.Assert;
import org.beangle.security.access.AccessDeniedException;
import org.beangle.security.access.AuthorityManager;
import org.beangle.security.auth.AuthenticationManager;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.AuthenticationException;
import org.beangle.security.core.context.SecurityContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract class that implements security interception for secure objects.
 * <p>
 * The <code>AbstractSecurityInterceptor</code> will ensure the proper startup configuration of the
 * security interceptor. It will also implement the proper handling of secure object invocations,
 * namely.
 */
public abstract class AbstractSecurityInterceptor implements Initializing {

  protected static final Logger logger = LoggerFactory.getLogger(AbstractSecurityInterceptor.class);

  private AuthorityManager authorityManager;
  private AuthenticationManager authenticationManager;

  private boolean alwaysReauthenticate = false;
  private boolean rejectPublicInvocations = false;
  private boolean validateConfigAttributes = true;

  /**
   * Completes the work of the <tt>AbstractSecurityInterceptor</tt> after the
   * secure object invocation has been completed.
   *
   * @param token as returned by the {@link #beforeInvocation(Object)} method
   * @param returnedObject any object returned from the secure object invocation (may be
   *          <tt>null</tt>)
   * @return the object the secure object invocation should ultimately return
   *         to its caller (may be <tt>null</tt>)
   */
  protected Object afterInvocation(InterceptorStatusToken token, Object returnedObject) {
    if (token == null) return returnedObject;
    if (token.isContextHolderRefreshRequired()) {
      logger.debug("Reverting to original Authentication: {}", token.getAuthentication());
      SecurityContextHolder.getContext().setAuthentication(token.getAuthentication());
    }
    return returnedObject;
  }

  public void init() throws Exception {
    Assert.notNull(getSecureObjectClass(),
        "Subclass must provide a non-null response to getSecureObjectClass()");
    Assert.notNull(this.authenticationManager, "An AuthenticationManager is required");
    Assert.notNull(this.authorityManager, "An AuthorityManager is required");
  }

  protected InterceptorStatusToken beforeInvocation(Object object) {
    Assert.notNull(object, "Object was null");
    if (!getSecureObjectClass().isAssignableFrom(object.getClass())) { throw new IllegalArgumentException(
        "Security invocation attempted for object " + object.getClass().getName()
            + " but AbstractSecurityInterceptor only configured to support secure objects of type: "
            + getSecureObjectClass()); }
    Authentication authenticated = authenticateIfRequired();

    // Attempt authorization
    if (!authorityManager.isAuthorized(authenticated, object)) { throw new AccessDeniedException(object,
        "access denied"); }
    logger.debug("Authorization successful");

    return new InterceptorStatusToken(authenticated, false, object);

  }

  /**
   * Checks the current authentication token and passes it to the
   * AuthenticationManager if {@link org.beangle.security.core.Authentication#isAuthenticated()}
   * returns false or the property <tt>alwaysReauthenticate</tt> has been set
   * to true.
   *
   * @return an authenticated <tt>Authentication</tt> object.
   */
  private Authentication authenticateIfRequired() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (null == authentication) { throw new AuthenticationException(); }
    if (authentication.isAuthenticated() && !alwaysReauthenticate) {
      logger.debug("Previously Authenticated: {}", authentication);
      return authentication;
    }
    authentication = authenticationManager.authenticate(authentication);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return authentication;
  }

  public AuthorityManager getAuthorityManager() {
    return authorityManager;
  }

  public AuthenticationManager getAuthenticationManager() {
    return this.authenticationManager;
  }

  /**
   * Indicates the type of secure objects the subclass will be presenting to
   * the abstract parent for processing. This is used to ensure collaborators
   * wired to the <code>AbstractSecurityInterceptor</code> all support the
   * indicated secure object class.
   *
   * @return the type of secure object the subclass provides services for
   */
  public abstract Class<?> getSecureObjectClass();

  public boolean isAlwaysReauthenticate() {
    return alwaysReauthenticate;
  }

  public boolean isRejectPublicInvocations() {
    return rejectPublicInvocations;
  }

  public boolean isValidateConfigAttributes() {
    return validateConfigAttributes;
  }

  public void setAuthorityManager(AuthorityManager authorityManager) {
    this.authorityManager = authorityManager;
  }

  public void setAlwaysReauthenticate(boolean alwaysReauthenticate) {
    this.alwaysReauthenticate = alwaysReauthenticate;
  }

  public void setAuthenticationManager(AuthenticationManager newManager) {
    this.authenticationManager = newManager;
  }

  public void setRejectPublicInvocations(boolean rejectPublicInvocations) {
    this.rejectPublicInvocations = rejectPublicInvocations;
  }

  public void setValidateConfigAttributes(boolean validateConfigAttributes) {
    this.validateConfigAttributes = validateConfigAttributes;
  }
}
