/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.security.Securities;
import org.beangle.security.auth.AbstractAuthentication;
import org.beangle.security.auth.AuthenticationDetailsSource;
import org.beangle.security.auth.AuthenticationManager;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.AuthenticationException;
import org.beangle.security.core.context.SecurityContextHolder;
import org.beangle.security.core.session.SessionRegistry;
import org.beangle.security.web.auth.logout.LogoutHandlerStack;

public class AuthenticationServiceImpl implements AuthenticationService {

  private AuthenticationDetailsSource<HttpServletRequest, Object> authenticationDetailsSource;

  private AuthenticationManager authenticationManager;

  private SessionRegistry sessionRegistry;

  private LogoutHandlerStack handlerStack;

  public void login(HttpServletRequest request, Authentication auth) throws AuthenticationException {
    if (auth instanceof AbstractAuthentication) {
      AbstractAuthentication abauth = (AbstractAuthentication) auth;
      if (null == abauth.getDetails()) abauth.setDetails(authenticationDetailsSource.buildDetails(request));
    }
    Authentication authRequest = auth;
    authRequest = authenticationManager.authenticate(authRequest);
    sessionRegistry.register(authRequest, request.getSession().getId());
    int inactiveInterval = sessionRegistry.getController().getInactiveInterval(authRequest);
    // inactiveInterval in minutes
    if (inactiveInterval > 0) request.getSession(true).setMaxInactiveInterval(inactiveInterval * 60);
    SecurityContextHolder.getContext().setAuthentication(authRequest);
  }

  public boolean logout(HttpServletRequest request, HttpServletResponse response) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (Securities.isValid(auth)) {
      if (null != handlerStack) handlerStack.logout(request, response, auth);
      return true;
    } else {
      return false;
    }
  }

  public void setAuthenticationDetailsSource(
      AuthenticationDetailsSource<HttpServletRequest, Object> authenticationDetailsSource) {
    this.authenticationDetailsSource = authenticationDetailsSource;
  }

  public void setAuthenticationManager(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  public void setSessionRegistry(SessionRegistry sessionRegistry) {
    this.sessionRegistry = sessionRegistry;
  }

  public void setHandlerStack(LogoutHandlerStack handlerStack) {
    this.handlerStack = handlerStack;
  }

}
