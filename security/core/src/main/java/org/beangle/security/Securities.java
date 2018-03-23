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
package org.beangle.security;

import java.security.Principal;

import org.beangle.security.core.context.SecurityContext;
import org.beangle.security.core.session.Session;

public final class Securities {

  public static String getUsername() {
    Session session = SecurityContext.getSession();
    if (null != session && null != session.getPrincipal()) {
      return session.getPrincipal().getName();
    } else return null;
  }

  public static Principal getPrincipal() {
    Session session = SecurityContext.getSession();
    return (null != session) ? session.getPrincipal() : null;
  }
}
