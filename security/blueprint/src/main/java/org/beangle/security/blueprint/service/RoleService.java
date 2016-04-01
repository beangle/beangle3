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
package org.beangle.security.blueprint.service;

import java.util.List;

import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.User;

/**
 * @author chaostone
 * @version $Id: RoleService.java Jul 29, 2011 1:59:38 AM chaostone $
 */
public interface RoleService {

  /**
   * Return role by id
   */
  Role get(Integer id);

  /**
   * 创建一个角色
   * 
   * @param creator
   * @param role
   */
  void createRole(User creator, Role role);

  /**
   * 删除管理者与角色的管理关系，如果该角色为其所创建则彻底删除. 1)超级管理员不能被删除.<br>
   * 2)如果删除人有超级管理员角色，则可以删除不是自己创建的角色
   * 
   * @param manager
   * @param roles
   */
  void removeRole(User manager, List<Role> roles);

  /**
   * 移动组
   * 
   * @param role
   * @param parent
   * @param indexno
   */
  void moveRole(Role role, Role parent, int indexno);

  /**
   * Returns true if user has administrative permission on role
   * 
   * @param user
   * @param role
   * @return
   */
  boolean isAdmin(User user, Role role);

  /**
   * 查询根用户
   * 
   * @return
   */
  List<Role> getRootRoles();
}
