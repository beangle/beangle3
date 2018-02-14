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
package org.beangle.security.core.userdetail;

import java.util.Collection;

import org.beangle.commons.lang.Objects;
import org.beangle.security.core.GrantedAuthority;

public class DefaultUserDetailBean implements UserDetail {

  private static final long serialVersionUID = 1L;
  private String password;
  private String username;
  private Collection<GrantedAuthority> authorities;
  private boolean accountExpired;
  private boolean accountLocked;
  private boolean credentialsExpired;
  private boolean enabled;

  public DefaultUserDetailBean(String username, String password,
      Collection<? extends GrantedAuthority> authorities) {
    this(username, password, true, false, false, false, authorities);
  }

  /**
   * Construct the <code>User</code> with the details required by
   * {@link org.beangle.security.auth.dao.DaoAuthenticationProvider} .
   *
   * @param username
   *          the username presented to the <code>DaoAuthenticationProvider</code>
   * @param password
   *          the password that should be presented to the <code>DaoAuthenticationProvider</code>
   * @param enabled
   *          set to <code>true</code> if the user is enabled
   * @param accountExpired
   *          set to <code>true</code> if the account has expired
   * @param credentialsExpired
   *          set to <code>true</code> if the credentials have expired
   * @param accountLocked
   *          set to <code>true</code> if the account is locked
   * @param authorities
   *          the authorities that should be granted to the caller if they
   *          presented the correct username and password and the user is
   *          enabled
   * @throws IllegalArgumentException
   *           if a <code>null</code> value was passed either as a parameter
   *           or as an element in the <code>GrantedAuthority[]</code> array
   */
  @SuppressWarnings("unchecked")
  public DefaultUserDetailBean(String username, String password, boolean enabled, boolean accountExpired,
      boolean credentialsExpired, boolean accountLocked, Collection<? extends GrantedAuthority> authorities)
      throws IllegalArgumentException {
    if (((username == null) || "".equals(username)) || (password == null)) { throw new IllegalArgumentException(
        "Cannot pass null or empty values to constructor"); }

    this.username = username;
    this.password = password;
    this.enabled = enabled;
    this.accountExpired = accountExpired;
    this.credentialsExpired = credentialsExpired;
    this.accountLocked = accountLocked;
    this.authorities = (Collection<GrantedAuthority>) authorities;
  }

  public boolean equals(Object rhs) {
    if (!(rhs instanceof DefaultUserDetailBean) || (rhs == null)) { return false; }
    DefaultUserDetailBean user = (DefaultUserDetailBean) rhs;
    return Objects.equalsBuilder().add(getUsername(), user.getUsername())
        .add(getPassword(), user.getPassword()).add(isAccountExpired(), user.isAccountLocked())
        .add(isAccountLocked(), user.isAccountLocked())
        .add(isCredentialsExpired(), user.isCredentialsExpired()).add(isEnabled(), user.isEnabled())
        .isEquals();
  }

  public Collection<GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public String getPassword() {
    return password;
  }

  public String getUsername() {
    return username;
  }

  public int hashCode() {
    return (null == getUsername()) ? 629 : getUsername().hashCode();
  }

  public boolean isAccountExpired() {
    return accountExpired;
  }

  public boolean isAccountLocked() {
    return this.accountLocked;
  }

  public boolean isCredentialsExpired() {
    return credentialsExpired;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append(super.toString()).append(": ");
    sb.append("Username: ").append(this.username).append("; ");
    sb.append("Password: [PROTECTED]; ");
    sb.append("Enabled: ").append(this.enabled).append("; ");
    sb.append("AccountExpired: ").append(this.accountExpired).append("; ");
    sb.append("credentialsExpired: ").append(this.credentialsExpired).append("; ");
    sb.append("AccountLocked: ").append(this.accountLocked).append("; ");

    if (!getAuthorities().isEmpty()) {
      sb.append("Granted Authorities: ");
      for (GrantedAuthority authority : getAuthorities()) {
        sb.append(authority.toString()).append(", ");
      }
      sb.deleteCharAt(sb.length() - 1);
    } else {
      sb.append("Not granted any authorities");
    }
    return sb.toString();
  }
}
