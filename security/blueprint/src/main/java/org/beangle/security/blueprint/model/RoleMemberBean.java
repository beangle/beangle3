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
package org.beangle.security.blueprint.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.pojo.NumberIdTimeObject;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.RoleMember;
import org.beangle.security.blueprint.User;

/**
 * 角色成员关系
 * 
 * @author chaostone
 * @version $Id: MemberBean.java Nov 2, 2010 6:45:48 PM chaostone $
 */
@Entity(name = "org.beangle.security.blueprint.RoleMember")
public class RoleMemberBean extends NumberIdTimeObject<Integer> implements RoleMember {

  private static final long serialVersionUID = -3882917413656652492L;

  /** 角色 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Role role;

  /** 用户 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  /** 用户是否是该组的成员 */
  @NotNull
  private boolean member;

  /** 用户是否能将该组授权给他人 */
  @NotNull
  private boolean granter;

  /** 用户是否是该组的管理者 */
  @NotNull
  private boolean manager;

  public RoleMemberBean() {
    super();
  }

  public RoleMemberBean(Role role, User user, RoleMember.Ship ship) {
    super();
    this.role = role;
    this.user = user;
    this.updatedAt = new Date();
    if (null != ship) {
      if (ship.equals(RoleMember.Ship.MEMBER)) {
        member = true;
      } else if (ship.equals(RoleMember.Ship.MANAGER)) {
        manager = true;
      } else {
        granter = true;
      }
    }
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public boolean isMember() {
    return member;
  }

  public void setMember(boolean member) {
    this.member = member;
  }

  public boolean isManager() {
    return manager;
  }

  public void setManager(boolean manager) {
    this.manager = manager;
  }

  public boolean isGranter() {
    return granter;
  }

  public void setGranter(boolean granter) {
    this.granter = granter;
  }

  public boolean is(RoleMember.Ship ship) {
    if (ship.equals(RoleMember.Ship.MEMBER)) { return member; }
    if (ship.equals(RoleMember.Ship.MANAGER)) { return manager; }
    if (ship.equals(RoleMember.Ship.GRANTER)) { return granter; }
    return false;
  }

  public int getDepth() {
    return role.getDepth();
  }
}
