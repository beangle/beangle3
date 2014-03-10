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
package org.beangle.security.blueprint;

import org.beangle.security.Securities;
import org.beangle.security.blueprint.service.UserToken;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.AuthenticationException;
import org.beangle.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

  private static ThreadLocal<String> resource = new ThreadLocal<String>();

  public static UserToken getPrincipal() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (!Securities.isValid(auth)) throw new AuthenticationException();
    UserToken user = (UserToken) auth.getPrincipal();
    if (null == user.getId()) throw new AuthenticationException();
    return user;
  }

  public static Long getUserId() {
    return getPrincipal().getId();
  }

  public static String getUsername() {
    return getPrincipal().getUsername();
  }

  public static String getFullname() {
    return getPrincipal().getFullname();
  }

  public static String getResource() {
    return resource.get();
  }

  public static void setResource(String name) {
    resource.set(name);
  }
}
