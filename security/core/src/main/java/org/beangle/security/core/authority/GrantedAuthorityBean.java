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
package org.beangle.security.core.authority;

import java.io.Serializable;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Assert;
import org.beangle.security.core.GrantedAuthority;

/** Basic concrete implementation of a {@link GrantedAuthority}. */

public class GrantedAuthorityBean implements GrantedAuthority, Serializable {

  private static final long serialVersionUID = 1L;
  private Object role;

  public GrantedAuthorityBean(Object role) {
    Assert.notNull(role, "A granted authority textual representation is required");
    this.role = role;
  }

  public static List<GrantedAuthority> build(Object... roles) {
    List<GrantedAuthority> authorities = CollectUtils.newArrayList(roles.length);
    for (Object role : roles) {
      authorities.add(new GrantedAuthorityBean(role));
    }
    return authorities;
  }

  public boolean equals(Object obj) {
    if (obj instanceof GrantedAuthority) {
      GrantedAuthority attr = (GrantedAuthority) obj;
      return this.role.equals(attr.getAuthority());
    }
    return false;
  }

  public Object getAuthority() {
    return this.role;
  }

  public int hashCode() {
    return this.role.hashCode();
  }

  public String toString() {
    return role.toString();
  }

  @SuppressWarnings("unchecked")
  public int compareTo(GrantedAuthority o) {
    if (o != null) {
      Object rhsRole = o.getAuthority();
      if (rhsRole == null) { return -1; }
      if (role instanceof Comparable) {
        return ((Comparable<Object>) role).compareTo(rhsRole);
      } else {
        throw new RuntimeException("Cannot compare GrantedAuthorityBean using role:" + role);
      }
    }
    return -1;
  }
}
