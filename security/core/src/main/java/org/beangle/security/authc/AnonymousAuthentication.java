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
package org.beangle.security.authc;

import java.util.Collection;

import org.beangle.commons.lang.Objects;

public class AnonymousAuthentication extends AbstractAuthentication {

  private static final long serialVersionUID = 3236987468644441586L;

  private Object principal;

  /**
   * Default anonymous instance
   */
  public static AnonymousAuthentication Instance = new AnonymousAuthentication("anonymous", null);

  public AnonymousAuthentication(Object principal, Collection<?> authorities) {
    super(authorities);
    this.principal = principal;
    setAuthenticated(true);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof AnonymousAuthentication) {
      AbstractAuthentication test = (AbstractAuthentication) obj;
      return Objects.equals(getPrincipal(), test.getPrincipal());
    }
    return false;
  }

  public Object getCredentials() {
    return "";
  }

  public Object getPrincipal() {
    return principal;
  }

}
