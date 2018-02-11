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
package org.beangle.security.core.context;

import org.beangle.security.core.Authentication;

/**
 * Base implementation of {@link SecurityContext}.
 * <p>
 * Used by default by {@link SecurityContextHolder}.
 * </p>
 *
 * @author chaostone
 * @version $Id: SecurityContextImpl.java 2217 2007-10-27 00:45:30Z $
 */
public class SecurityContextBean implements SecurityContext {

  private static final long serialVersionUID = 3146265469090172129L;

  private Authentication authentication;

  public boolean equals(Object obj) {
    if (obj instanceof SecurityContextBean) {
      SecurityContextBean test = (SecurityContextBean) obj;

      if ((this.getAuthentication() == null) && (test.getAuthentication() == null)) { return true; }

      if ((this.getAuthentication() != null) && (test.getAuthentication() != null)
          && this.getAuthentication().equals(test.getAuthentication())) { return true; }
    }

    return false;
  }

  public Authentication getAuthentication() {
    return authentication;
  }

  public int hashCode() {
    if (this.authentication == null) {
      return -1;
    } else {
      return this.authentication.hashCode();
    }
  }

  public void setAuthentication(Authentication authentication) {
    this.authentication = authentication;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append(super.toString());

    if (this.authentication == null) {
      sb.append(": Null authentication");
    } else {
      sb.append(": Authentication: ").append(this.authentication);
    }

    return sb.toString();
  }
}
