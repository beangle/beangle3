/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.beangle.commons.orm.pojo.LongIdTimeObject;
import org.beangle.security.blueprint.Member;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.User;

/**
 * 角色成员关系
 * 
 * @author chaostone
 * @version $Id: MemberBean.java Nov 2, 2010 6:45:48 PM chaostone $
 */
@Entity(name = "org.beangle.security.blueprint.Member")
public class MemberBean extends LongIdTimeObject implements Member {

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

  public MemberBean() {
    super();
  }

  public MemberBean(Role role, User user, Member.Ship ship) {
    super();
    this.role = role;
    this.user = user;
    this.createdAt = new Date();
    this.updatedAt = new Date();
    if (null != ship) {
      if (ship.equals(Member.Ship.MEMBER)) {
        member = true;
      } else if (ship.equals(Member.Ship.MANAGER)) {
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

  public boolean is(Member.Ship ship) {
    if (ship.equals(Member.Ship.MEMBER)) { return member; }
    if (ship.equals(Member.Ship.MANAGER)) { return manager; }
    if (ship.equals(Member.Ship.GRANTER)) { return granter; }
    return false;
  }

  public int getDepth() {
    return role.getDepth();
  }
}
