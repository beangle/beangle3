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
package org.beangle.security.blueprint.service.internal;

import java.sql.Date;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.bean.Initializing;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Objects;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.lang.functor.Predicate;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.RoleMember;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.UserProfile;
import org.beangle.security.blueprint.event.UserAlterationEvent;
import org.beangle.security.blueprint.event.UserCreationEvent;
import org.beangle.security.blueprint.event.UserRemoveEvent;
import org.beangle.security.blueprint.event.UserStatusEvent;
import org.beangle.security.blueprint.model.RoleMemberBean;
import org.beangle.security.blueprint.model.UserBean;
import org.beangle.security.blueprint.service.UserService;

/**
 * 用户信息服务的实现类
 *
 * @author dell,chaostone 2005-9-26
 */
public class UserServiceImpl extends BaseServiceImpl implements UserService, Initializing {

  protected final Set<String> roots = new HashSet<String>();

  @Override
  public void init() throws Exception {
    initRoots();
  }

  protected void initRoots() {

  }

  public boolean isRoot(User user) {
    return roots.contains(user.getCode());
  }

  public boolean isRoot(String userCode) {
    return roots.contains(userCode);
  }

  public User get(String name, String password) {
    Map<String, Object> params = CollectUtils.newHashMap();
    params.put("name", name);
    params.put("password", password);
    List<?> userList = entityDao
        .search("from User user where  user.name = :name and user.password = :password", params);
    if (userList.size() > 0) return (User) userList.get(0);
    else return null;
  }

  public User get(Long id) {
    return entityDao.get(User.class, id);
  }

  public User get(String loginName) {
    if (Strings.isEmpty(loginName)) return null;
    OqlBuilder<User> entityQuery = OqlBuilder.from(User.class, "user");
    entityQuery.where("user.name=:name", loginName);
    List<User> users = entityDao.search(entityQuery);
    return (users.isEmpty()) ? null : users.get(0);
  }

  public List<User> getUsers(Long[] userIds) {
    return entityDao.get(User.class, userIds);
  }

  public int updateState(final User manager, Long[] ids, final boolean enabled) {
    assert (null == ids || ids.length < 1);
    List<User> users = CollectUtils.select(getUsers(ids), new Predicate<User>() {
      public Boolean apply(User one) {
        return isManagedBy(manager, one) && !manager.equals(one) && (one.isEnabled() != enabled);
      }
    });

    for (int i = 0; i < users.size(); i++) {
      User cur = users.get(i);
      cur.setEnabled(enabled);
    }
    if (!users.isEmpty()) {
      entityDao.saveOrUpdate(users);
      publish(new UserStatusEvent(users, enabled));
    }
    return users.size();
  }

  public void saveOrUpdate(User user) {
    user.setUpdatedAt(new Date(System.currentTimeMillis()));
    entityDao.saveOrUpdate(user);
    publish(new UserAlterationEvent(Collections.singletonList(user)));
  }

  public List<RoleMember> getMembers(User user, RoleMember.Ship ship) {
    if (isRoot(user) && !Objects.equals(ship, RoleMember.Ship.MEMBER)) {
      List<RoleMember> members = CollectUtils.newArrayList();
      List<Role> roles = entityDao.getAll(Role.class);
      for (Role role : roles) {
        RoleMemberBean gmb = new RoleMemberBean(role, user, RoleMember.Ship.MEMBER);
        gmb.setGranter(true);
        gmb.setManager(true);
        members.add(gmb);
      }
      return members;
    }
    OqlBuilder<RoleMember> builder = OqlBuilder.from(RoleMember.class, "gm");
    builder.where("gm.user=:user", user);
    if (null != ship) {
      if (ship.equals(RoleMember.Ship.MEMBER)) builder.where("gm.member=true");
      if (ship.equals(RoleMember.Ship.MANAGER)) builder.where("gm.manager=true");
      if (ship.equals(RoleMember.Ship.GRANTER)) builder.where("gm.granter=true");
    }
    return entityDao.search(builder);
  }

  public void createUser(User creator, UserBean newUser) {
    newUser.setUpdatedAt(new Date(System.currentTimeMillis()));
    entityDao.saveOrUpdate(newUser);
    publish(new UserCreationEvent(Collections.singletonList(newUser)));
  }

  public void removeUser(User manager, User user) {
    List<User> removed = CollectUtils.newArrayList();
    if (isManagedBy(manager, user)) {
      entityDao.remove(entityDao.get(UserProfile.class, "user", user), user);
      removed.add(user);
    }
    publish(new UserRemoveEvent(removed));
  }

  public boolean isManagedBy(User manager, User user) {
    if (isRoot(manager)) return true;
    for (RoleMember m1 : manager.getMembers()) {
      if (m1.isManager()) {
        for (RoleMember m2 : user.getMembers())
          if (m2.isMember() && m2.getRole().equals(m1.getRole())) return true;
      }
    }
    return false;
  }

}
