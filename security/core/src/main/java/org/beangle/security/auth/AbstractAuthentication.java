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
package org.beangle.security.auth;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Assert;
import org.beangle.commons.lang.Objects;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.GrantedAuthority;
import org.beangle.security.core.userdetail.UserDetail;

public abstract class AbstractAuthentication implements Authentication {

  private static final long serialVersionUID = 3966615358056184985L;

  private Object details;
  private final Collection<GrantedAuthority> authorities;
  private boolean authenticated = false;

  public AbstractAuthentication(Collection<? extends GrantedAuthority> authorities) {
    if (authorities == null) {
      this.authorities = Collections.emptyList();
    } else {
      Assert.noNullElements(authorities, "authorities cannot contain any null element");
      this.authorities = Collections.unmodifiableCollection(CollectUtils.newArrayList(authorities));
    }
  }

  public Object getDetails() {
    return details;
  }

  public void setDetails(Object details) {
    this.details = details;
  }

  public Collection<GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public boolean isAuthenticated() {
    return authenticated;
  }

  public void setAuthenticated(boolean authenticated) {
    this.authenticated = authenticated;
  }

  public boolean equals(Object obj) {
    if (obj instanceof AbstractAuthentication) {
      AbstractAuthentication test = (AbstractAuthentication) obj;
      return Objects.equalsBuilder().add(getPrincipal(), test.getPrincipal())
          .add(getCredentials(), test.getCredentials()).add(getDetails(), test.getDetails())
          .add(isAuthenticated(), test.isAuthenticated()).add(getAuthorities(), test.getAuthorities())
          .isEquals();
    }
    return false;
  }

  @Override
  public int hashCode() {
    return (null == getPrincipal()) ? 629 : getPrincipal().hashCode();
  }

  public String getName() {
    if (this.getPrincipal() instanceof UserDetail) return ((UserDetail) getPrincipal()).getUsername();
    if (getPrincipal() instanceof Principal) return ((Principal) getPrincipal()).getName();
    return (this.getPrincipal() == null) ? "" : this.getPrincipal().toString();
  }
}
