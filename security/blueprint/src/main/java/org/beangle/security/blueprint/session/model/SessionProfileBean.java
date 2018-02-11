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
package org.beangle.security.blueprint.session.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.pojo.IntegerIdObject;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.model.RoleBean;
import org.beangle.security.core.session.category.CategoryProfile;

/**
 * 角色会话配置
 *
 * @author chaostone
 */
@Entity(name = "org.beangle.security.blueprint.session.model.SessionProfileBean")
public class SessionProfileBean extends IntegerIdObject implements CategoryProfile {

  private static final long serialVersionUID = 1999239598984221565L;

  /** 角色 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  protected Role role;

  /** 最大在线人数 */
  @NotNull
  protected int capacity;

  /** 单用户的同时最大会话数 */
  @NotNull
  protected short userMaxSessions = 1;

  /** 不操作过期时间(以分为单位) */
  @NotNull
  protected short inactiveInterval;

  public SessionProfileBean() {
    super();
  }

  /**
   * Quick construct profile
   */
  public SessionProfileBean(Integer id, Integer roleId,String roleCode,String roleName, int capacity, short userMaxSessions,
      short inactiveInterval) {
    super();
    this.id = id;
    this.role = new RoleBean(roleId,roleCode, roleName);
    this.capacity = capacity;
    this.userMaxSessions = userMaxSessions;
    this.inactiveInterval = inactiveInterval;
  }

  public String getCategory() {
    return role.getName();
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int max) {
    this.capacity = max;
  }

  public short getInactiveInterval() {
    return inactiveInterval;
  }

  public void setInactiveInterval(short inactiveInterval) {
    this.inactiveInterval = inactiveInterval;
  }

  public short getUserMaxSessions() {
    return userMaxSessions;
  }

  public void setUserMaxSessions(short maxSessions) {
    this.userMaxSessions = maxSessions;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(role.getName());
    sb.append(":{max=").append(capacity).append(',');
    sb.append("maxSessions=").append(userMaxSessions).append(',');
    sb.append("inactiveInterval=").append(inactiveInterval).append('}');
    return sb.toString();
  }

}
