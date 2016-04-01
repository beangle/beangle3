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
package org.beangle.security.web.auth.logout;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.security.core.Authentication;

/**
 * Indicates a class that is able to participate in logout handling.
 * <p>
 * Called by {@link LogoutFilter}.
 */
public interface LogoutHandler {

  /**
   * Causes a logout to be completed. The method must complete successfully.
   * 
   * @param request
   *          the HTTP request
   * @param response
   *          the HTTP resonse
   * @param authentication
   *          the current principal details
   */
  void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication);
}
