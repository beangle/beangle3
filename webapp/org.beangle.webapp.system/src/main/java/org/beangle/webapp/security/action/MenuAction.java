/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.security.action;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.StrUtils;
import org.beangle.model.Entity;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.util.HierarchyEntityUtil;
import org.beangle.security.blueprint.Authority;
import org.beangle.security.blueprint.Menu;
import org.beangle.security.blueprint.MenuProfile;
import org.beangle.security.blueprint.Resource;
import org.beangle.security.blueprint.model.MenuBean;
import org.beangle.security.blueprint.service.MenuService;

/**
 * 系统模块(菜单)管理响应类
 * 
 * @author 鄂州蚊子 2008-8-4
 */
public class MenuAction extends SecurityActionSupport {

	private MenuService menuService;
	
	protected void indexSetting() {
		put("profiles", entityDao.getAll(MenuProfile.class));
	}

	protected void editSetting(Entity<?> entity) {
		put("profiles", entityDao.getAll(MenuProfile.class));
		Menu menu = (Menu) entity;
		List<Menu> folders = CollectUtils.newArrayList();
		OqlBuilder<Resource> builder = OqlBuilder.from(Resource.class, "r");
		if (null != menu.getProfile() && null != menu.getProfile().getId()) {
			MenuProfile profile = entityDao.get(MenuProfile.class, menu.getProfile().getId());
			builder.where("exists(from r.categories as rc where rc=:category)", profile.getCategory());
			// 查找可以作为父节点的菜单
			OqlBuilder<Menu> folderBuilder = OqlBuilder.from(Menu.class, "m");
			folderBuilder.where("m.entry is null and m.profile=:profile", profile);
			folderBuilder.orderBy("m.code");
			folders = entityDao.search(folderBuilder);
			if (null != menu.getParent()&& !folders.contains(menu.getParent())) folders.add(menu.getParent());
			folders.removeAll(HierarchyEntityUtil.getFamily(menu));
		}
		List<Resource> resurces = entityDao.search(builder);
		Set<Resource> existResources = menu.getResources();
		if (null != resurces) {
			resurces.removeAll(existResources);
		}
		put("parents", folders);
		put("resources", resurces);
	}

	@Override
	protected String removeAndForward(Collection<?> entities) {
		@SuppressWarnings("unchecked")
		List<Menu> menus = (List<Menu>) entities;
		List<Menu> parents = CollectUtils.newArrayList();
		for (Menu menu : menus) {
			if (null != menu.getParent()) {
				menu.getParent().getChildren().remove(menu);
				parents.add(menu.getParent());
			}
		}
		entityDao.saveOrUpdate(parents);
		return super.removeAndForward(entities);
	}

	protected String saveAndForward(Entity<?> entity) {
		Menu menu = (Menu) entity;
		try {
			List<Resource> resources = CollectUtils.newArrayList();
			String resourceIdSeq = get("resourceIds");
			if (null != resourceIdSeq && resourceIdSeq.length() > 0) {
				resources = entityDao.get(Resource.class, StrUtils.splitToLong(resourceIdSeq));
			}
			menu.getResources().clear();
			menu.getResources().addAll(resources);
			if(menu.isTransient()){
				((MenuBean)menu).generateCode();
			}
			entityDao.saveOrUpdate(menu);
			Long newParentId = getLong("parent.id");
			int indexno = getInteger("indexno");
			Menu parent = null;
			if (null != newParentId) {
				parent = entityDao.get(Menu.class, newParentId);
			}
			menuService.move(menu, parent, indexno);
		} catch (Exception e) {
			e.printStackTrace();
			return forward(ERROR);
		}
		return redirect("search", "info.save.success");
	}


	

	/**
	 * 禁用或激活一个或多个模块
	 * 
	 * @return
	 */
	public String activate() {
		Long[] menuIds = getEntityIds(getShortName());
		Boolean enabled = getBoolean("isActivate");
		if (null == enabled) {
			enabled = Boolean.TRUE;
		}
		List<Menu> menus = entityDao.get(Menu.class, menuIds);
		for (Menu menu : menus) {
			menu.setEnabled(enabled);
		}
		entityDao.saveOrUpdate(menus);
		return redirect("search", "info.save.success");
	}

	/**
	 * 打印预览功能列表
	 * 
	 * @return
	 */
	public String preview() {
		OqlBuilder<Menu> query = OqlBuilder.from(Menu.class, "menu");
		populateConditions(query);
		query.orderBy("menu.code asc");
		put("menus", entityDao.search(query));

		query.cleanOrders();
		query.select("max(length(menu.code)/2)");
		List<?> rs = entityDao.search(query);
		put("depth", rs.get(0));
		return forward();
	}

	@Override
	public String info() {
		Long entityId = getEntityId(getShortName());
		if (null == entityId) {
			logger.warn("cannot get paremeter {}Id or {}.id", getShortName(), getShortName());
		}
		Menu menu = (Menu) getModel(getEntityName(), entityId);
		put(getShortName(), menu);
		if (!menu.getResources().isEmpty()) {
			OqlBuilder<Authority> groupQuery = OqlBuilder.from(Authority.class, "auth");
			groupQuery.where("auth.resource in(:resources)", menu.getResources()).select(
					"distinct auth.group");
			put("groups", entityDao.search(groupQuery));
		}
		return forward();
	}

	@Override
	protected String getEntityName() {
		return Menu.class.getName();
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

}
