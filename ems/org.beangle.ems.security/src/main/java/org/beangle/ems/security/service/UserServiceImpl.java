/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityExistsException;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.GroupMember;
import org.beangle.ems.security.User;
import org.beangle.ems.security.model.GroupMemberBean;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * 用户信息服务的实现类
 * 
 * @author dell,chaostone 2005-9-26
 */
public class UserServiceImpl extends BaseServiceImpl implements UserService {

	public boolean isAdmin(User user) {
		return User.ROOT.equals(user.getId());
	}

	public boolean isAdmin(Long userId) {
		return User.ROOT.equals(userId);
	}

	public User get(String name, String password) {
		Map<String, Object> params = CollectUtils.newHashMap();
		params.put("name", name);
		params.put("password", password);
		List<?> userList = entityDao.searchHQLQuery(
				"from User user where  user.name = :name and user.password = :password", params);
		if (userList.size() > 0) return (User) userList.get(0);
		else return null;
	}

	public User get(Long id) {
		return entityDao.get(User.class, id);
	}

	public User get(String loginName) {
		if (StringUtils.isEmpty(loginName)) return null;
		OqlBuilder<User> entityQuery = OqlBuilder.from(User.class, "user");
		entityQuery.where("user.name=:name", loginName);
		List<User> users = entityDao.search(entityQuery);
		return (users.isEmpty()) ? null : users.get(0);
	}

	public List<User> getUsers(Long[] userIds) {
		return entityDao.get(User.class, userIds);
	}

	public void updateState(Long[] ids, int state) {
		assert (null == ids || ids.length < 1);
		List<User> users = getUsers(ids);
		for (int i = 0; i < users.size(); i++) {
			User cur = users.get(i);
			cur.setStatus(state);
		}
		entityDao.saveOrUpdate(users);
	}

	public void saveOrUpdate(User user) {
		try {
			user.setUpdatedAt(new Date(System.currentTimeMillis()));
			if (!user.isPersisted()) {
				user.setCreatedAt(new Date(System.currentTimeMillis()));
			}
			entityDao.saveOrUpdate(user);
		} catch (DataIntegrityViolationException e) {
			throw new EntityExistsException("User already exits:" + user);
		} catch (Exception e) {
			throw new EntityExistsException("User already exits:" + user);
		}
	}

	// workgroup for no session
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Group> getGroups(User user, GroupMember.Ship ship) {
		if (isAdmin(user) && !ObjectUtils.equals(ship, GroupMember.Ship.MEMBER)) return entityDao
				.getAll(Group.class);
		OqlBuilder builder = OqlBuilder.from(GroupMember.class, "gm");
		builder.where("gm.user=:user", user).select("gm.group");
		if (null != ship) {
			if (ship.equals(GroupMember.Ship.MEMBER)) builder.where("gm.member=true");
			if (ship.equals(GroupMember.Ship.MANAGER)) builder.where("gm.manager=true");
			if (ship.equals(GroupMember.Ship.GRANTER)) builder.where("gm.granter=true");
		}
		return entityDao.search(builder);
	}

	public List<GroupMember> getGroupMembers(User user, GroupMember.Ship ship) {
		if (isAdmin(user) && !ObjectUtils.equals(ship, GroupMember.Ship.MEMBER)) {
			List<GroupMember> members = CollectUtils.newArrayList();
			List<Group> groups = entityDao.getAll(Group.class);
			for (Group group : groups) {
				GroupMemberBean gmb = new GroupMemberBean(group, user, GroupMember.Ship.MEMBER);
				gmb.setGranter(true);
				gmb.setManager(true);
				members.add(gmb);
			}
			return members;
		}
		OqlBuilder<GroupMember> builder = OqlBuilder.from(GroupMember.class, "gm");
		builder.where("gm.user=:user", user);
		if (null != ship) {
			if (ship.equals(GroupMember.Ship.MEMBER)) builder.where("gm.member=true");
			if (ship.equals(GroupMember.Ship.MANAGER)) builder.where("gm.manager=true");
			if (ship.equals(GroupMember.Ship.GRANTER)) builder.where("gm.granter=true");
		}
		return entityDao.search(builder);
	}

	public void createUser(User creator, User newUser) {
		newUser.setCreator(creator);
		newUser.setUpdatedAt(new Date(System.currentTimeMillis()));
		newUser.setCreatedAt(new Date(System.currentTimeMillis()));
		entityDao.saveOrUpdate(newUser);
	}

	public void removeUser(User manager, User user) {
		if (isManagedBy(manager, user)) {
			entityDao.remove(user);
		}
	}

	public boolean isManagedBy(User manager, User user) {
		return (isAdmin(manager) || manager.equals(user.getCreator()));
	}

	public void createGroup(User owner, Group group) {
		group.setUpdatedAt(new Date(System.currentTimeMillis()));
		group.setCreatedAt(new Date(System.currentTimeMillis()));
		group.setOwner(owner);
		group.getMembers().add(new GroupMemberBean(group, owner, GroupMember.Ship.MANAGER));
		entityDao.saveOrUpdate(group);
	}

	/**
	 * 超级管理员不能删除
	 */
	public void removeGroup(User manager, List<Group> groups) {
		List<Object> removed = CollectUtils.newArrayList();
		for (final Group group : groups) {
			if (group.getOwner().equals(manager) || isAdmin(manager)) entityDao.remove(group);
		}
		entityDao.remove(removed);
	}

}
