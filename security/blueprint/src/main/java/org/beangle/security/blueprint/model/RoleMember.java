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
package org.beangle.security.blueprint.model;

import java.util.Date;

import org.beangle.commons.entity.pojo.NumberIdTimeObject;

/**
 * 角色成员关系
 *
 * @author chaostone
 */
public class RoleMember extends NumberIdTimeObject<Integer> {

  private static final long serialVersionUID = -3882917413656652492L;

  /** 角色 */
  private Role role;

  /** 用户 */
  private User user;

  /** 用户是否是该组的成员 */
  private boolean member;

  /** 用户是否能将该组授权给他人 */
  private boolean granter;

  /** 用户是否是该组的管理者 */
  private boolean manager;

  public RoleMember() {
    super();
  }

  public RoleMember(Role role, User user, MemberShip ship) {
    super();
    this.role = role;
    this.user = user;
    this.updatedAt = new Date();
    if (null != ship) {
      if (ship.equals(MemberShip.MEMBER)) {
        member = true;
      } else if (ship.equals(MemberShip.MANAGER)) {
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

  public boolean is(MemberShip ship) {
    if (ship.equals(MemberShip.MEMBER)) { return member; }
    if (ship.equals(MemberShip.MANAGER)) { return manager; }
    if (ship.equals(MemberShip.GRANTER)) { return granter; }
    return false;
  }

  public int getDepth() {
    return role.getDepth();
  }
}
