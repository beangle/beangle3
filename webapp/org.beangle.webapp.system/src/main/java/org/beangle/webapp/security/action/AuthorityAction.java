/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.security.action;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.StrUtils;
import org.beangle.model.entity.Model;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.security.blueprint.Authority;
import org.beangle.security.blueprint.Category;
import org.beangle.security.blueprint.Group;
import org.beangle.security.blueprint.GroupMember;
import org.beangle.security.blueprint.Menu;
import org.beangle.security.blueprint.MenuProfile;
import org.beangle.security.blueprint.Resource;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.service.CacheableAuthorityManager;
import org.beangle.security.core.authority.GrantedAuthorityBean;
import org.beangle.struts2.convention.route.Action;

/**
 * 权限分配与管理响应类
 * 
 * @author chaostone 2005-10-9
 */
public class AuthorityAction extends SecurityActionSupport {

	private CacheableAuthorityManager authorityManager;

	/**
	 * 主页面
	 */
	public String index() {
		User user = getUser();
		put("manager", user);
		if (isAdmin(user)) {
			put("allGroups", entityDao.getAll(Group.class));
		}
		return forward();
	}

	/**
	 * 根据菜单配置来分配权限
	 * 
	 * @author 鄂州蚊子
	 */
	public String edit() {
		Long groupId = getLong("group.id");
		if (null == groupId) {
			groupId = getLong("groupIds");
		}
		Group ao = entityDao.get(Group.class, groupId);
		User user = getUser();
		put("manager", user);
		List<Group> mngGroups=CollectUtils.newArrayList();
		if (isAdmin(user)) {
			mngGroups= entityDao.getAll(Group.class);
		}else{
			for(GroupMember m:user.getGroups()){
				if(m.isManager())mngGroups.add(m.getGroup());
			}
		}
		put("mngGroups",mngGroups);
		
		List<Category> categories = CollectUtils.newArrayList();
		categories.add(((Group) ao).getCategory());

		OqlBuilder<MenuProfile> query = OqlBuilder.from(MenuProfile.class, "menuProfile");
		query.where("menuProfile.category in(:categories)", categories);
		List<MenuProfile> menuProfiles = entityDao.search(query);
		put("menuProfiles", menuProfiles);

		Long menuProfileId = getLong("menuProfileId");
		MenuProfile menuProfile = null;
		if (null != menuProfileId) {
			menuProfile = entityDao.get(MenuProfile.class, menuProfileId);
			if (!menuProfile.getCategory().equals(ao.getCategory())) {
				menuProfile = (menuProfiles.get(0));
			}
		} else {
			menuProfile = (menuProfiles.get(0));
		}
		if (null != menuProfile) {
			List<Menu> menus = null;
			Collection<Resource> resources = null;
			if (isAdmin(user)) {
				menus = menuProfile.getMenus();
				resources = entityDao.getAll(Resource.class);
			} else {
				menus = authorityService.getMenus(menuProfile, user);
				resources = authorityService.getResources(user);
			}
			put("resources", CollectUtils.newHashSet(resources));
			boolean displayFreezen = getBool("displayFreezen");
			if (!displayFreezen) {
				List<Menu> freezed = CollectUtils.newArrayList();
				for (Menu menu : menus) {
					if (!menu.isEnabled()) {
						freezed.add(menu);
					}
				}
				menus.removeAll(freezed);
			}
			put("menus", menus);

			Set<Resource> aoResources = CollectUtils.newHashSet();
			Map<String, Long> aoResourceAuthorityMap = CollectUtils.newHashMap();
			List<Authority> authorities = authorityService.getAuthorities(ao);
			Collection<Menu> aoMenus = authorityService.getMenus(menuProfile, (Group) ao, null);
			for (final Authority authority : authorities) {
				aoResources.add(authority.getResource());
				aoResourceAuthorityMap.put(authority.getResource().getId().toString(), authority.getId());
			}
			put("aoMenus", CollectUtils.newHashSet(aoMenus));
			put("aoResources", aoResources);
			put("aoResourceAuthorityMap", aoResourceAuthorityMap);
		}
		put("menuProfile", menuProfile);
		put("ao", ao);
		return forward();
	}

	/**
	 * 显示权限操作提示界面
	 */
	public String prompt() {
		return forward();
	}

	/**
	 * 保存模块级权限
	 */
	public String save() {
		Group mao = entityDao.get(Group.class, getLong("group.id"));
		MenuProfile menuProfile = (MenuProfile) entityDao.get(MenuProfile.class, getLong("menuProfileId"));
		Set<Resource> newResources = CollectUtils.newHashSet(entityDao.get(Resource.class,
				StrUtils.splitToLong(get("resourceId"))));

		// 管理员拥有的菜单权限和系统资源
		User manager = getUser();
		Set<Menu> mngMenus = null;
		Set<Resource> mngResources = CollectUtils.newHashSet();
		if (isAdmin(manager)) {
			mngMenus = CollectUtils.newHashSet(menuProfile.getMenus());
		} else {
			mngMenus = CollectUtils.newHashSet(authorityService.getMenus(menuProfile, (User) manager));
		}
		for (final Menu m : mngMenus) {
			mngResources.addAll(m.getResources());
		}

		// 确定要删除的菜单和系统资源
		// Set<MenuAuthority> removedMenus = CollectionUtil.newHashSet();
		// for (MenuAuthority ma : mao.getMenuAuthorities()) {
		// if (mngMenus.contains(ma.getMenu()) &&
		// ma.getMenu().getProfile().equals(menuProfile)) {
		// if (!newMenus.contains(ma.getMenu())) {
		// removedMenus.add(ma);
		// } else {
		// newMenus.remove(ma.getMenu());
		// }
		// }
		// }

		Set<Authority> removedResources = CollectUtils.newHashSet();
		for (final Authority au : mao.getAuthorities()) {
			if (mngResources.contains(au.getResource())) {
				if (!newResources.contains(au.getResource())) {
					removedResources.add(au);
				} else {
					newResources.remove(au.getResource());
				}
			}
		}

		// 删除菜单和系统资源
		// mao.getMenuAuthorities().removeAll(removedMenus);
		mao.getAuthorities().removeAll(removedResources);

		// 添加新的菜单和系统资源
		// for (Menu menu : newMenus) {
		// MenuAuthority authority = Model.newInstance(MenuAuthority.class);
		// authority.setGroup(mao);
		// authority.setMenu(menu);
		// mao.getMenuAuthorities().add(authority);
		// }

		for (Resource resource : newResources) {
			Authority authority = Model.newInstance(Authority.class);
			authority.setGroup(mao);
			authority.setResource(resource);
			mao.getAuthorities().add(authority);
		}

		entityDao.saveOrUpdate(mao);
		authorityManager.refreshGroupAuthorities(new GrantedAuthorityBean(mao.getName()));

		Action redirect = Action.to(this).method("edit");
		redirect.param("group.id", mao.getId()).param("menuProfileId", menuProfile.getId());
		String displayFreezen = get("displayFreezen");
		if (null != displayFreezen) {
			redirect.param("displayFreezen", displayFreezen);
		}
		return redirect(redirect, "info.save.success");
	}

	public void setAuthorityManager(CacheableAuthorityManager authorityManager) {
		this.authorityManager = authorityManager;
	}

}
