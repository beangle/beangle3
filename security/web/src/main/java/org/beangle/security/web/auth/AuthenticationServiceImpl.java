/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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
package org.beangle.security.web.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.commons.lang.Option;
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

  public AuthenticationServiceImpl() {
    super();
  }

  public AuthenticationServiceImpl(AuthenticationManager authenticationManager) {
    super();
    this.authenticationManager = authenticationManager;
  }

  public Authentication login(HttpServletRequest request, Authentication auth) throws AuthenticationException {
    if (auth instanceof AbstractAuthentication) {
      AbstractAuthentication abauth = (AbstractAuthentication) auth;
      if (null == abauth.getDetails()) abauth.setDetails(authenticationDetailsSource.buildDetails(request));
    }
    Authentication authRequest = auth;
    authRequest = authenticationManager.authenticate(authRequest);
    sessionRegistry.register(authRequest, request.getSession().getId());
    Option<Short> interval = sessionRegistry.getController().getInactiveInterval(authRequest);
    // inactiveInterval in minutes
    if (interval.isDefined()) request.getSession(true).setMaxInactiveInterval(interval.get() * 60);
    SecurityContextHolder.getContext().setAuthentication(authRequest);
    return authRequest;

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
