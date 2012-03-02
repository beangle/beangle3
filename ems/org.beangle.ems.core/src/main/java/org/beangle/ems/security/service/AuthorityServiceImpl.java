/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beangle.collection.CollectUtils;
import org.beangle.dao.impl.BaseServiceImpl;
import org.beangle.dao.metadata.Model;
import org.beangle.dao.query.builder.OqlBuilder;
import org.beangle.ems.security.Authority;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.Resource;
import org.beangle.ems.security.User;
import org.beangle.ems.security.event.GroupAuthorityEvent;

/** 授权信息的服务实现类
 * 
 * @author dell,chaostone 2005-9-26 */
public class AuthorityServiceImpl extends BaseServiceImpl implements AuthorityService {

	protected UserService userService;

	public Resource getResource(String name) {
		OqlBuilder<Resource> query = OqlBuilder.from(Resource.class, "r");
		query.where("r.name=:name", name).cacheable();
		return entityDao.uniqueResult(query);
	}

	public List<Authority> getAuthorities(User user) {
		if (null == user) return Collections.emptyList();
		Map<Resource, Authority> authorities = CollectUtils.newHashMap();
		List<Group> groups = user.getGroups();
		for (final Group group : groups) {
			List<Authority> groupAuths = getAuthorities(group);
			for (final Authority groupAuth : groupAuths) {
				if (authorities.containsKey(groupAuth.getResource())) {
					Authority existed = authorities.get(groupAuth.getResource());
					existed.merge(groupAuth);
				} else {
					authorities.put(groupAuth.getResource(), groupAuth);
				}
			}
		}
		return CollectUtils.newArrayList(authorities.values());
	}

	public Authority getAuthority(User user, Resource resource) {
		if ((null == user) || null == resource) return null;
		Authority au = null;
		List<Group> groups = user.getGroups();
		for (final Group one : groups) {
			Authority ar = getAuthority(one, resource);
			if (null == au) {
				au = ar;
			} else {
				au.merge(ar);
			}
		}
		return au;
	}

	public List<Resource> getResources(User user) {
		Set<Resource> resources = CollectUtils.newHashSet();
		Map<String, Object> params = CollectUtils.newHashMap();
		List<Group> groups = user.getGroups();
		String hql = "select distinct m from " + Group.class.getName() + " as r join r.authorities as a"
				+ " join a.resource as m where  r.id = :groupId";
		params.clear();
		for (final Group group : groups) {
			params.put("groupId", group.getId());
			List<Resource> groupResources = entityDao.searchHQLQuery(hql, params);
			resources.addAll(groupResources);
		}
		return CollectUtils.newArrayList(resources);
	}

	/** 找到该组内激活的资源id */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Set<String> getResourceNamesByGroup(Long groupId) {
		String hql = "select a.resource.name from " + Authority.class.getName()
				+ " as a where a.group.id= :groupId and a.resource.enabled = true";
		OqlBuilder query = OqlBuilder.hql(hql).param("groupId", groupId).cacheable();
		return (Set<String>) new HashSet(entityDao.search(query));
	}

	/** 找到该组内激活的资源id */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Set<String> getResourceNamesByScope(Resource.Scope scope) {
		OqlBuilder builder = OqlBuilder.from(Resource.class, "r").where("r.scope=:scope", scope)
				.select("r.name").cacheable();
		return (Set<String>) new HashSet(entityDao.search(builder));
	}

	public void authorize(Group group, Set<Resource> resources) {
		Set<Authority> removed = CollectUtils.newHashSet();
		for (final Authority au : group.getAuthorities()) {
			if (!resources.contains(au.getResource())) {
				removed.add(au);
			} else {
				resources.remove(au.getResource());
			}
		}
		group.getAuthorities().removeAll(removed);

		for (Resource resource : resources) {
			Authority authority = Model.newInstance(Authority.class);
			authority.setGroup(group);
			authority.setResource(resource);
			group.getAuthorities().add(authority);
		}
		entityDao.remove(removed);
		entityDao.saveOrUpdate(group);
		publish(new GroupAuthorityEvent(group));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updateState(Long[] resourceIds, boolean isEnabled) {
		OqlBuilder query = OqlBuilder.from(Resource.class, "resource");
		query.where("resource.id in (:ids)", resourceIds);
		List<Resource> resources = entityDao.search(query);
		for (Resource resource : resources) {
			resource.setEnabled(isEnabled);
		}
		entityDao.saveOrUpdate(resources);
	}

	public Authority getAuthority(Group group, Resource resource) {
		if (null == group || null == resource) return null;
		Map<String, Object> params = CollectUtils.newHashMap();
		params.put("group", group);
		params.put("resource", resource);
		List<?> authorityList = entityDao.searchNamedQuery("getGroupAuthorityByResource", params);
		if (authorityList.isEmpty()) return null;
		else return (Authority) authorityList.get(0);
	}

	public List<Authority> getAuthorities(Group group) {
		OqlBuilder<Authority> builder = OqlBuilder.hql("select distinct a from  " + Group.class.getName()
				+ " as r join r.authorities as a join a.resource as m  where r = :group");
		builder.param("group", group);
		return entityDao.search(builder);
	}

	/** 查询用户组对应的模块 */
	public List<Resource> getResources(Group group) {
		String hql = "select distinct m from " + Group.class.getName() + " as r join r.authorities as a"
				+ " join a.resource as m where  r.id = :groupId and m.enabled = true";
		OqlBuilder<Resource> query = OqlBuilder.hql(hql);
		query.param("groupId", group.getId()).cacheable();
		return entityDao.search(query);
	}

	public String extractResource(String uri) {
		int lastDot = -1;
		for (int i = 0; i < uri.length(); i++) {
			char a = uri.charAt(i);
			if (a == '.' || a == '!') {
				lastDot = i;
				break;
			}
		}
		if (lastDot < 0) {
			lastDot = uri.length();
		}
		return uri.substring(0, lastDot);
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserService getUserService() {
		return userService;
	}
}
