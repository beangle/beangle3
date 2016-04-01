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
package org.beangle.security.blueprint.model;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.NumberIdTimeObject;
import org.beangle.commons.entity.util.EntityUtils;
import org.beangle.commons.lang.Objects;
import org.beangle.security.blueprint.Profile;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.RoleMember;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.UserProfile;

/**
 * 系统用户
 * 记录账号、权限、状态信息.
 * 
 * @author dell,chaostone 2005-9-26
 */
@Entity(name = "org.beangle.security.blueprint.User")
public class UserBean extends NumberIdTimeObject<Long> implements User {
  private static final long serialVersionUID = -3625902334772342380L;

  /** 名称 */
  @Size(max = 40)
  @NotNull
  @Column(unique = true)
  protected String code;

  /** 用户姓名 */
  @NotNull
  @Size(max = 50)
  private String name;

  /** 用户密文 */
  @Size(max = 100)
  @NotNull
  private String password;

  /** 对应角色 */
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private Set<RoleMember> members = CollectUtils.newHashSet();

  /** 菜单列表 */
  @OneToMany(mappedBy = "user", targetEntity = UserProfile.class)
  private List<Profile> profiles = CollectUtils.newArrayList();

  /**
   * 账户生效日期
   */
  @NotNull
  protected java.sql.Date beginOn;

  /**
   * 账户失效日期
   */
  protected java.sql.Date endOn;

  /**
   * 密码失效日期
   */
  protected java.sql.Date passwordExpiredOn;

  /** 是否启用 */
  @NotNull
  protected boolean enabled;

  /** 备注 */
  @Size(max = 200)
  protected String remark;

  public UserBean() {
    super();
  }

  public UserBean(Long id) {
    setId(id);
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<RoleMember> getMembers() {
    return members;
  }

  public List<Role> getRoles(List<Profile> profiles) {
    List<Role> roles = CollectUtils.newArrayList();
    for (RoleMember member : members) {
      if (member.getRole().isEnabled() && member.isMember()) {
        boolean matched = false;
        if (member.getRole().getProperties().isEmpty()) matched = true;
        else {
          for (Profile profile : profiles) {
            if (profile.matches(member.getRole())) {
              matched = true;
              break;
            }
          }
        }
        if (matched) roles.add(member.getRole());
      }
    }

    Set<Role> allRoles = CollectUtils.newHashSet();
    for (Role g : roles) {
      while (null != g && !allRoles.contains(g)) {
        allRoles.add(g);
        g = g.getParent();
      }
    }
    roles.clear();
    roles.addAll(allRoles);
    Collections.sort(roles);
    return roles;
  }

  @Override
  public List<Role> getRoles() {
    return getRoles(this.getProfiles());
  }

  public List<Profile> getProfiles() {
    return profiles;
  }

  public void setProfiles(List<Profile> profiles) {
    this.profiles = profiles;
  }

  public void setMembers(Set<RoleMember> members) {
    this.members = members;
  }

  /**
   * 是否账户过期
   */
  public boolean isAccountExpired() {
    return EntityUtils.isExpired(this);
  }

  /**
   * 是否密码过期
   */
  public boolean isPasswordExpired() {
    return (null != passwordExpiredOn && new Date(System.currentTimeMillis()).after(passwordExpiredOn));
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public java.sql.Date getBeginOn() {
    return beginOn;
  }

  public void setBeginOn(java.sql.Date beginOn) {
    this.beginOn = beginOn;
  }

  public java.sql.Date getEndOn() {
    return endOn;
  }

  public void setEndOn(java.sql.Date endOn) {
    this.endOn = endOn;
  }

  public java.sql.Date getPasswordExpiredOn() {
    return passwordExpiredOn;
  }

  public void setPasswordExpiredOn(java.sql.Date passwordExpiredOn) {
    this.passwordExpiredOn = passwordExpiredOn;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String toString() {
    return Objects.toStringBuilder(this).add("id", this.id).add("password", this.password)
        .add("name", this.getCode()).toString();
  }

}
