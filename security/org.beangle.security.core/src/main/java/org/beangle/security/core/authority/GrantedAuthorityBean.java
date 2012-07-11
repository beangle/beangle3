/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
