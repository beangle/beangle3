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
package org.beangle.security.auth;

import java.util.Collection;

import org.beangle.security.core.GrantedAuthority;

public class UsernamePasswordAuthentication extends AbstractAuthentication {

  private static final long serialVersionUID = 1L;

  private final Object principal;
  private final Object credentials;

  public UsernamePasswordAuthentication(Object principal, Object credentials) {
    super(null);
    this.principal = principal;
    this.credentials = credentials;
  }

  public UsernamePasswordAuthentication(Object principal, Object credentials,
      Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.principal = principal;
    this.credentials = credentials;
    super.setAuthenticated(true);
  }

  public Object getCredentials() {
    return credentials;
  }

  public Object getPrincipal() {
    return principal;
  }

  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    if (isAuthenticated) { throw new IllegalArgumentException(
        "Cannot set this token to trusted - use constructor containing GrantedAuthority[]s instead"); }
    super.setAuthenticated(false);
  }
}
