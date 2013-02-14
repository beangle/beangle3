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
package org.beangle.security;

import org.beangle.security.auth.AnonymousAuthentication;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.context.SecurityContext;
import org.beangle.security.core.context.SecurityContextHolder;

public final class Securities {

  public static boolean isValid(Authentication auth) {
    return (null != auth && !AnonymousAuthentication.class.isAssignableFrom(auth.getClass()));
  }

  public static boolean hasValidAuthentication() {
    return isValid(SecurityContextHolder.getContext().getAuthentication());
  }

  public static String getUsername() {
    SecurityContext context = SecurityContextHolder.getContext();
    if (null != context && null != context.getAuthentication()) {
      return context.getAuthentication().getName();
    } else return null;
  }

  public static Authentication getAuthentication() {
    SecurityContext context = SecurityContextHolder.getContext();
    return (null != context) ? context.getAuthentication() : null;
  }
}
