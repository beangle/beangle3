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

import org.beangle.security.core.GrantedAuthority;

public class RememberMeAuthentication extends AbstractAuthentication {
  private static final long serialVersionUID = 1L;
  private Object principal;
  private int keyHash;

  public RememberMeAuthentication(String key, Object principal,
      Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    if ((key == null) || ("".equals(key)) || (principal == null) || "".equals(principal)) { throw new IllegalArgumentException(
        "Cannot pass null or empty values to constructor"); }

    this.keyHash = key.hashCode();
    this.principal = principal;
    setAuthenticated(true);
  }

  public boolean equals(Object obj) {
    if (!super.equals(obj)) { return false; }

    if (obj instanceof RememberMeAuthentication) {
      RememberMeAuthentication test = (RememberMeAuthentication) obj;

      if (this.getKeyHash() != test.getKeyHash()) { return false; }

      return true;
    }

    return false;
  }

  public Object getCredentials() {
    return "";
  }

  public int getKeyHash() {
    return this.keyHash;
  }

  public Object getPrincipal() {
    return this.principal;
  }
}
