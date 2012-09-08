/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.session.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.model.RoleBean;
import org.beangle.security.core.session.category.CategoryProfile;

/**
 * 角色会话配置
 * 
 * @author chaostone
 */
@Entity(name = "org.beangle.security.blueprint.session.model.SessionProfileBean")
public class SessionProfileBean extends LongIdObject implements CategoryProfile {

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
  protected int userMaxSessions = 1;

  /** 不操作过期时间(以分为单位) */
  @NotNull
  protected int inactiveInterval;

  public SessionProfileBean() {
    super();
  }

  /**
   * Quick construct profile
   * 
   * @param id
   * @param category
   * @param capacity
   * @param userMaxSessions
   * @param inactiveInterval
   */
  public SessionProfileBean(Long id, String category, int capacity, int userMaxSessions, int inactiveInterval) {
    super();
    this.id = id;
    this.role = new RoleBean(null, category);
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

  public int getInactiveInterval() {
    return inactiveInterval;
  }

  public void setInactiveInterval(int inactiveInterval) {
    this.inactiveInterval = inactiveInterval;
  }

  public int getUserMaxSessions() {
    return userMaxSessions;
  }

  public void setUserMaxSessions(int maxSessions) {
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
