/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.security.action;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.security.blueprint.Category;
import org.beangle.security.blueprint.Group;
import org.beangle.security.blueprint.Menu;
import org.beangle.security.blueprint.MenuProfile;
import org.beangle.security.blueprint.Resource;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.model.CategoryBean;
import org.beangle.security.blueprint.restrict.RestrictField;
import org.beangle.security.blueprint.restrict.RestrictPattern;

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
		userQuery.select("user.defaultCategory.id,user.status,count(*)").groupBy(
				"user.defaultCategory.id,user.status");
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
		if (!isAdmin(getUser())) { return "admin-info"; }
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
