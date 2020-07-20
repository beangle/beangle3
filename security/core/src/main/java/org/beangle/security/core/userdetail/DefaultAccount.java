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

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Objects;

public class DefaultAccount implements Account {

  private static int Locked = 1;
  private static int Disabled = 2;
  private static int AccountExpired = 4;
  private static int CredentialExpired = 8;

  private static final long serialVersionUID = 1L;
  private String name;
  private String description;
  private int categoryId;
  private String remoteToken;
  private String[] authorities;
  private String[] permissions;

  private Map<String, Object> details = CollectUtils.newHashMap();
  private Profile[] profiles;
  private int status;

  private void change(boolean value, int mask) {
    if (value) {
      status = status | mask;
    } else {
      if ((status & mask) > 0) status = status ^ mask;
    }
  }

  private boolean get(int mask) {
    return (status & mask) > 0;
  }

  public DefaultAccount(String username, String description, Map<String, Object> details) {
    this(username, description);
    this.details = details;
  }

  public DefaultAccount(String name, String description) throws IllegalArgumentException {
    if (((name == null) || "".equals(name))) { throw new IllegalArgumentException(
        "Cannot pass null or empty values to constructor"); }
    this.name = name;
    this.description = description;
    this.setAccountExpired(false);
    this.setAccountLocked(false);
    this.setEnabled(true);
    this.setCredentialExpired(false);
  }

  public boolean equals(Object rhs) {
    if (!(rhs instanceof DefaultAccount) || (rhs == null)) { return false; }
    DefaultAccount user = (DefaultAccount) rhs;
    return Objects.equalsBuilder().add(getName(), user.getName()).add(getDescription(), user.getDescription())
        .add(isAccountExpired(), user.isAccountLocked()).add(isAccountLocked(), user.isAccountLocked())
        .add(isCredentialExpired(), user.isCredentialExpired()).add(isEnabled(), user.isEnabled()).isEquals();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }

  public String getRemoteToken() {
    return remoteToken;
  }

  public void setRemoteToken(String remoteToken) {
    this.remoteToken = remoteToken;
  }

  public Map<String, Object> getDetails() {
    return details;
  }

  public void setDetails(Map<String, Object> details) {
    this.details = details;
  }

  public boolean isCredentialExpired() {
    return get(CredentialExpired);
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public void setCredentialExpired(boolean credentialExpired) {
    change(credentialExpired, CredentialExpired);
  }

  public void setAccountExpired(boolean accountExpired) {
    change(accountExpired, AccountExpired);
  }

  public void setAccountLocked(boolean accountLocked) {
    change(accountLocked, Locked);
  }

  public void setEnabled(boolean enabled) {
    change(enabled, Disabled);
  }

  public int hashCode() {
    return (null == getName()) ? 629 : getName().hashCode();
  }

  public boolean isAccountExpired() {
    return get(AccountExpired);
  }

  public boolean isAccountLocked() {
    return get(Locked);
  }

  public boolean isEnabled() {
    return get(Disabled);
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append(super.toString()).append(": ");
    sb.append("name: ").append(this.name).append("; ");
    sb.append("status: ").append(this.status).append("; ");

    if (!getDetails().isEmpty()) {
      sb.append("Details: ");
      for (Map.Entry<String, Object> d : getDetails().entrySet()) {
        sb.append(d.getKey()).append('=').append(d.getValue());
      }
      sb.deleteCharAt(sb.length() - 1);
    } else {
      sb.append("Not granted any authorities");
    }
    return sb.toString();
  }

  public String[] getAuthorities() {
    return authorities;
  }

  public void setAuthorities(String[] authorities) {
    this.authorities = authorities;
  }

  public String[] getPermissions() {
    return permissions;
  }

  public void setPermissions(String[] permissions) {
    this.permissions = permissions;
  }

  public Profile[] getProfiles() {
    return profiles;
  }

  public void setProfiles(Profile[] profiles) {
    this.profiles = profiles;
  }
}
