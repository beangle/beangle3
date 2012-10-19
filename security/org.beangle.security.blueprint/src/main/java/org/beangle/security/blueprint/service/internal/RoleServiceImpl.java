/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.service.internal;

import java.util.Date;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.Operation;
import org.beangle.commons.dao.impl.AbstractHierarchyService;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.security.blueprint.Member;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.event.RoleCreationEvent;
import org.beangle.security.blueprint.model.MemberBean;
import org.beangle.security.blueprint.model.RoleBean;
import org.beangle.security.blueprint.service.RoleService;
import org.beangle.security.blueprint.service.UserService;

/**
 * @author chaostone
 * @version $Id: RoleServiceImpl.java Jul 29, 2011 1:58:51 AM chaostone $
 */
public class RoleServiceImpl extends AbstractHierarchyService<RoleBean, Role> implements RoleService {
  private UserService userService;

  public void createRole(User owner, Role role) {
    role.setUpdatedAt(new Date(System.currentTimeMillis()));
    role.setCreatedAt(new Date(System.currentTimeMillis()));
    role.getMembers().add(new MemberBean(role, owner, Member.Ship.MANAGER));
    entityDao.saveOrUpdate(role);
    publish(new RoleCreationEvent(role));
  }

  /**
   * 超级管理员不能删除
   */
  public void removeRole(User manager, List<Role> roles) {
    List<Object> saved = CollectUtils.newArrayList();
    List<Object> removed = CollectUtils.newArrayList();

    for (final Role role : roles) {
      if (role.getOwner().equals(manager) || userService.isRoot(manager)) {
        if (null != role.getParent()) {
          role.getParent().getChildren().remove(role);
          saved.add(role.getParent());
        }
        removed.add(role);
      }
    }
    entityDao.execute(Operation.saveOrUpdate(saved).remove(removed));
  }

  public void moveRole(Role role, Role parent, int indexno) {
    move((RoleBean) role, (RoleBean) parent, indexno);
  }

  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  protected List<RoleBean> getTopNodes(RoleBean m) {
    OqlBuilder builder = OqlBuilder.from(Role.class, "r").where("r.parent is null");
    return entityDao.search(builder);
  }

  /**
   * Returns true if user has administrative permission on role
   * 
   * @param user
   * @param role
   */
  public boolean isAdmin(User user, Role role) {
    if (userService.isRoot(user)) return true;
    else {
      Role parent = role.getParent();
      if (null != parent) return false;
      for (Member m : user.getMembers()) {
        if (m.is(Member.Ship.MANAGER) && m.getRole().equals(parent)) return true;
      }
      return false;
    }
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

}
