/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.action;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.ems.security.Category;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.Resource;
import org.beangle.ems.security.User;
import org.beangle.ems.security.model.CategoryBean;
import org.beangle.ems.security.nav.Menu;
import org.beangle.ems.security.nav.MenuProfile;
import org.beangle.ems.security.restrict.RestrictField;
import org.beangle.ems.security.restrict.RestrictPattern;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.query.builder.OqlBuilder;

public class IndexAction extends SecurityActionSupport {

	public String stat() {
		OqlBuilder<Category> cquery = OqlBuilder.from(Category.class, "category");
		List<Category> categories = entityDao.search(cquery);
		Map<Long, Category> categoryMap = CollectUtils.newHashMap();
		for (Category category : categories) {
			categoryMap.put(category.getId(), category);
		}
		put("categories", categoryMap);

		populateUserStat();
		// stat group
		OqlBuilder<Group> groupQuery = OqlBuilder.from(Group.class, "group");
		groupQuery.select("group.category.id,group.enabled,count(*)").groupBy(
				"group.category.id,group.enabled");
		put("groupStat", entityDao.search(groupQuery));

		// state menus
		List<MenuProfile> menuProfiles = entityDao.getAll(MenuProfile.class);
		Map<Long, List<?>> menuStats = CollectUtils.newHashMap();
		for (MenuProfile profile : menuProfiles) {
			OqlBuilder<Menu> menuQuery = OqlBuilder.from(Menu.class, "menu");
			menuQuery.where("menu.profile=:profile", profile).select("menu.enabled,count(*)")
					.groupBy("enabled");
			menuStats.put(profile.getId(), entityDao.search(menuQuery));
		}
		put("menuProfiles", menuProfiles);
		put("menuStats", menuStats);

		// stat resource
		OqlBuilder<Resource> resourceQuery = OqlBuilder.from(Resource.class, "resource");
		resourceQuery.select("resource.enabled,count(*)").groupBy("enabled");
		put("resourceStat", entityDao.search(resourceQuery));

		// stat pattern and restriction
		put("patternStat",
				entityDao.search(OqlBuilder.from(RestrictPattern.class, "pattern").select("count(*)")));
		put("paramStat", entityDao.search(OqlBuilder.from(RestrictField.class, "param").select("count(*)")));
		return forward();
	}

	private void populateUserStat() {
		OqlBuilder<User> userQuery = OqlBuilder.from(User.class, "user");
		userQuery.select("user.defaultCategory.id,user.enabled,count(*)").groupBy(
				"user.defaultCategory.id,user.enabled");
		put("userStat", entityDao.search(userQuery));
	}

	/**
	 * 管理用户类别和超级管理员
	 * 
	 * @return
	 */
	public String admin() {
		List<Category> categories = entityDao.getAll(Category.class);
		put("categories", categories);
		if (!isAdmin()) { return "admin-info"; }
		boolean updated=false;
		for (Category category : categories) {
			String name = get(category.getId() + "_category.name");
			String title = get(category.getId() + "_category.title");
			if (null != name && null != title) {
				category.setName(name);
				category.setTitle(title);
				updated=true;
				entityDao.saveOrUpdate(category);
			}
		}
		if(updated){
			return redirect("admin", "info.save.success");
		}
		String newCategory = get("newCategory");
		if (StringUtils.isNotBlank(newCategory)) {
			if (entityDao.get(Category.class, "name", newCategory).isEmpty()) {
				CategoryBean category = new CategoryBean();
				category.setName(newCategory);
				entityDao.saveOrUpdate(category);
				return redirect("admin", "info.save.success");
			} else {
				return redirect("admin", "info.save.failure");
			}
		}
		Long categoryId = getLong("removeCategoryId");
		if (null != categoryId) {
			Category category = entityDao.get(Category.class, categoryId);
			if (null != category) {
				try {
					entityDao.remove(category);
				} catch (Exception e) {
					return redirect("admin", "info.remove.failure");
				}
			}
			return redirect("admin", "info.remove.success");
		}
		return forward();
	}
}
