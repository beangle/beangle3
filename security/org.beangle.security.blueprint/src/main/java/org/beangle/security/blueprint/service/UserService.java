/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.service;

import java.util.List;

import org.beangle.security.blueprint.Member;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.User;

public interface UserService {
  /**
   * 根据用户名和密码查找用户
   * 
   * @param name
   * @param password
   */
  public User get(String name, String password);

  /**
   * 根据登陆名查找用户
   * 
   * @param name
   */
  public User get(String name);

  /**
   * 查询指定id的用户，不存在时返回null
   * 
   * @param id
   */
  public User get(Long id);

  /**
   * 保存新用户，用户存在时，抛出异常
   * 
   * @param user
   */
  public void saveOrUpdate(User user);

  /**
   * 返回userIds指定的用户
   * 
   * @param userIds
   */
  public List<User> getUsers(Long userIds[]);

  /**
   * 查询用户关联的member角色
   * 
   * @param userId
   */
  public List<Role> getRoles(Long userId);

  /**
   * 查找关联组关系
   * 
   * @param user
   * @param ship
   */
  public List<Member> getMembers(User user, Member.Ship ship);

  /**
   * 设置用户状态
   * 不能禁用或激活自己和非管理范围内的用户
   * 
   * @param manager
   * @param userIds
   * @param enabled
   */
  public int updateState(User manager, Long[] userIds, boolean enabled);

  /**
   * 创建帐户
   * 
   * @param creator
   * @param newUser
   */
  public void createUser(User creator, User newUser);

  /**
   * 删除creator与managed的管理关系，如该用户为creator所创建，则删除user
   * 
   * @param creator
   * @param managed
   */
  public void removeUser(User creator, User user);

  /**
   * 是否属于管理关系
   * 
   * @param manager
   * @param managed
   */
  public boolean isManagedBy(User manager, User user);

  /**
   * 是否是超级管理员
   * 
   * @param user
   */
  public boolean isRoot(User user);

  /**
   * 是否是超级管理员
   * 
   * @param userId
   */
  public boolean isRoot(Long userId);

}
