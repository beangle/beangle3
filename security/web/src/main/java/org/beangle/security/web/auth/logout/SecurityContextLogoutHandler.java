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
package org.beangle.security.web.auth.logout;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.beangle.commons.lang.Assert;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.context.SecurityContextHolder;

/**
 * Performs a logout by modifying the
 * {@link org.beangle.security.core.context.SecurityContextHolder}.
 * <p>
 * Will also invalidate the {@link HttpSession} if {@link #isInvalidateHttpSession()} is
 * <code>true</code> and the session is not <code>null</code>.
 */
public class SecurityContextLogoutHandler implements LogoutHandler {

  private boolean invalidateHttpSession = true;

  /**
   * Requires the request to be passed in.
   * 
   * @param request from which to obtain a HTTP session (cannot be null)
   * @param response not used (can be <code>null</code>)
   * @param authentication not used (can be <code>null</code>)
   */
  public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    Assert.notNull(request, "HttpServletRequest required");
    if (invalidateHttpSession) {
      HttpSession session = request.getSession(false);
      if (session != null) session.invalidate();
    }
    SecurityContextHolder.clearContext();
  }

  public boolean isInvalidateHttpSession() {
    return invalidateHttpSession;
  }

  /**
   * Causes the {@link HttpSession} to be invalidated when this {@link LogoutHandler} is invoked.
   * Defaults to true.
   * 
   * @param invalidateHttpSession
   *          true if you wish the session to be invalidated (default) or
   *          false if it should not be.
   */
  public void setInvalidateHttpSession(boolean invalidateHttpSession) {
    this.invalidateHttpSession = invalidateHttpSession;
  }

}
