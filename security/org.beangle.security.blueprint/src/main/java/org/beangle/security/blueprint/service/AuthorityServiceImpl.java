/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.entity.Model;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.security.blueprint.Authority;
import org.beangle.security.blueprint.Group;
import org.beangle.security.blueprint.GroupMember;
import org.beangle.security.blueprint.Menu;
import org.beangle.security.blueprint.MenuProfile;
import org.beangle.security.blueprint.Resource;
import org.beangle.security.blueprint.User;

/**
 * 授权信息的服务实现类
 * 
 * @author dell,chaostone 2005-9-26
 */
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
		List<Group> groups = userService.getGroups(user, GroupMember.Ship.MEMBER);
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
		List<Group> groups = userService.getGroups(user, GroupMember.Ship.MEMBER);
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

	public List<Menu> getMenus(MenuProfile profile, User user) {
		Set<Menu> menus = CollectUtils.newHashSet();
		List<Group> groups = userService.getGroups(user, GroupMember.Ship.MEMBER);
		for (final Group group : groups) {
			if (group.isEnabled()) menus.addAll(getMenus(profile, group, Boolean.TRUE));
		}
		return addParentMenus(menus);
	}

	/**
	 * 添加父菜单并且排序
	 * 
	 * @param menus
	 * @return
	 */
	private List<Menu> addParentMenus(Set<Menu> menus) {
		Set<Menu> parentMenus = CollectUtils.newHashSet();
		for (Menu menu : menus) {
			while (null != menu.getParent() && !menu.getParent().equals(menu)) {
				parentMenus.add(menu.getParent());
				menu = menu.getParent();
			}
		}
		menus.addAll(parentMenus);
		List<Menu> menuList = CollectUtils.newArrayList(menus);
		Collections.sort(menuList);
		return menuList;
	}

	/**
	 * 查询用户组对应的模块
	 */
	public List<Menu> getMenus(MenuProfile profile, Group group, Boolean enabled) {
		OqlBuilder<Menu> query = buildMenuQuery(profile, group).cacheable();
		if (null != enabled) {
			query.where("menu.enabled=:enabled", enabled);
		}
		List<Menu> menus = entityDao.search(query);
		return addParentMenus(CollectUtils.newHashSet(menus));
	}

	private OqlBuilder<Menu> buildMenuQuery(MenuProfile profile, Group group) {
		OqlBuilder<Menu> builder = OqlBuilder.from(Menu.class);
		builder.join("menu.resources", "mr");
		builder.where("exists(from " + Authority.class.getName()
				+ " a where a.group=:group and a.resource=mr)", group);
		if (null != profile) {
			builder.where("menu.profile=:profile", profile);
		}
		return builder;
	}

	public List<Resource> getResources(User user) {
		Set<Resource> resources = CollectUtils.newHashSet();
		Map<String, Object> params = CollectUtils.newHashMap();
		List<Group> groups = userService.getGroups(user, GroupMember.Ship.MEMBER);
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Set<String> getResourceNames(int scope) {
		OqlBuilder query = OqlBuilder.from(Resource.class, "resource");
		query.where("resource.scope=:scope and resource.enabled=true", Integer.valueOf(scope));
		query.select("resource.name");
		return CollectUtils.newHashSet(entityDao.search(query));
	}

	/**
	 * 找到该组内激活的资源id
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Set<String> getResourceNamesByGroup(String group) {
		String hql = "select m.name from " + Group.class.getName() + " as r join r.authorities as a"
				+ " join a.resource as m where  r.name = :groupName and m.enabled = true";
		OqlBuilder query = OqlBuilder.hql(hql).param("groupName", group).cacheable();
		return (Set<String>) new HashSet(entityDao.search(query));
	}

	public void authorize(Group group, Set<Resource> resources) {
		// 查找保留的权限
		Set<Authority> reserved = CollectUtils.newHashSet();
		for (final Authority authority : group.getAuthorities()) {
			if (resources.contains(authority.getResource())) {
				reserved.add(authority);
				resources.remove(authority.getResource());
			}
		}
		group.getAuthorities().clear();
		group.getAuthorities().addAll(reserved);
		// 新权限
		Authority model = null;
		try {
			model = (Authority) Model.newInstance(Authority.class);
		} catch (Exception e) {
			throw new RuntimeException("cannot init authroity by class:" + Authority.class);
		}
		model.setGroup(group);
		for (final Resource element : resources) {
			Authority authority = (Authority) model.clone();
			authority.setResource(element);
			group.getAuthorities().add(authority);
		}
		entityDao.saveOrUpdate(group);
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Collection<Group> filter(Collection<Group> groups, Resource resource) {
		OqlBuilder builder = OqlBuilder.from(Authority.class, "au");
		builder.where("au.group in (:groups) and au.resource = :resource", groups, resource);
		builder.select("au.group");
		return entityDao.search(builder);
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

	/**
	 * 查询用户组对应的模块
	 */
	public List<Resource> getResources(Group group) {
		String hql = "select distinct m from " + Group.class.getName() + " as r join r.authorities as a"
				+ " join a.resource as m where  r.id = :groupId and m.enabled = true";
		OqlBuilder<Resource> query = OqlBuilder.hql(hql);
		query.param("groupId", group.getId()).cacheable();
		return entityDao.search(query);
	}

	public void remove(Authority authority) {
		if (null != authority) entityDao.remove(authority);
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

	public void saveOrUpdate(Authority authority) {
		entityDao.saveOrUpdate(authority);
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserService getUserService() {
		return userService;
	}
}
