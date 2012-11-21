/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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

import org.beangle.security.blueprint.Member;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.model.UserBean;

/**
 * User service
 * 
 * @author chaostone
 * @since 2.0
 */
public interface UserService {
  /**
   * 根据用户名和密码查找用户
   * 
   * @param name
   * @param password
   */
  User get(String name, String password);

  /**
   * 根据登陆名查找用户
   * 
   * @param name
   */
  User get(String name);

  /**
   * 查询指定id的用户，不存在时返回null
   * 
   * @param id
   */
  User get(Long id);

  /**
   * 保存新用户，用户存在时，抛出异常
   * 
   * @param user
   */
  void saveOrUpdate(User user);

  /**
   * 返回userIds指定的用户
   * 
   * @param userIds
   */
  List<User> getUsers(Long userIds[]);

  /**
   * 查询用户关联的member角色
   * 
   * @param userId
   */
  List<Role> getRoles(Long userId);

  /**
   * 查找关联组关系
   * 
   * @param user
   * @param ship
   */
  List<Member> getMembers(User user, Member.Ship ship);

  /**
   * 设置用户状态
   * 不能禁用或激活自己和非管理范围内的用户
   * 
   * @param manager
   * @param userIds
   * @param enabled
   */
  int updateState(User manager, Long[] userIds, boolean enabled);

  /**
   * 创建帐户
   * 
   * @param creator
   * @param newUser
   */
  void createUser(User creator, UserBean newUser);

  /**
   * 删除creator与managed的管理关系，如该用户为creator所创建，则删除user
   * 
   * @param creator
   * @param user
   */
  void removeUser(User creator, User user);

  /**
   * 是否属于管理关系
   * 
   * @param manager
   * @param user
   */
  boolean isManagedBy(User manager, User user);

  /**
   * 是否是超级管理员
   * 
   * @param user
   */
  boolean isRoot(User user);

  /**
   * 是否是超级管理员
   * 
   * @param userId
   */
  boolean isRoot(Long userId);

}
