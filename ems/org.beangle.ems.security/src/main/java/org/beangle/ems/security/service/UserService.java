/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.service;

import java.util.List;

import org.beangle.ems.security.Group;
import org.beangle.ems.security.GroupMember;
import org.beangle.ems.security.User;

public interface UserService {
	/**
	 * 根据用户名和密码查找用户
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	public User get(String name, String password);

	/**
	 * 根据登陆名查找用户
	 * 
	 * @param name
	 * @return
	 */
	public User get(String name);

	/**
	 * 查询指定id的用户，不存在时返回null
	 * 
	 * @param id
	 * @return
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
	 * @return
	 */
	public List<User> getUsers(Long userIds[]);

	/**
	 * 查询用户关联的用户组
	 * 
	 * @param user
	 * @return
	 */
	public List<Group> getGroups(User user, GroupMember.Ship ship);

	/**
	 * 查找关联组关系
	 * 
	 * @param user
	 * @param ship
	 * @return
	 */
	public List<GroupMember> getGroupMembers(User user, GroupMember.Ship ship);

	/**
	 * 设置用户状态
	 * 不能禁用或激活自己和非管理范围内的用户
	 * 
	 * @param manager
	 * @param userIds
	 * @param enabled
	 */
	public void updateState(User manager, Long[] userIds, boolean enabled);

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
	 * @return
	 */
	public boolean isManagedBy(User manager, User user);

	/**
	 * 创建一个用户组
	 * 
	 * @param creator
	 * @param group
	 */
	public void createGroup(User creator, Group group);

	/**
	 * 删除管理者与用户组的管理关系，如果该用户组为其所创建则彻底删除. 1)超级管理员不能被删除.<br>
	 * 2)如果删除人有超级管理员用户组，则可以删除不是自己创建的用户组
	 * 
	 * @param manager
	 * @param group
	 */
	public void removeGroup(User manager, List<Group> groups);

	/**
	 * 是否是超级管理员
	 * 
	 * @param user
	 * @return
	 */
	public boolean isAdmin(User user);

	/**
	 * 是否是超级管理员
	 * 
	 * @param userId
	 * @return
	 */
	public boolean isAdmin(Long userId);
}
