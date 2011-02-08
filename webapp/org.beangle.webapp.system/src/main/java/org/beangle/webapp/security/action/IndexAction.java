/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.security.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.security.blueprint.AdminUser;
import org.beangle.security.blueprint.Group;
import org.beangle.security.blueprint.Menu;
import org.beangle.security.blueprint.MenuProfile;
import org.beangle.security.blueprint.Resource;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.UserCategory;
import org.beangle.security.blueprint.model.AdminUserBean;
import org.beangle.security.blueprint.model.UserCategoryBean;
import org.beangle.security.blueprint.restrict.RestrictField;
import org.beangle.security.blueprint.restrict.RestrictPattern;

public class IndexAction extends SecurityActionSupport {

	public String stat() {
		OqlBuilder<UserCategory> cquery = OqlBuilder.from(UserCategory.class, "category");
		List<UserCategory> categories = entityDao.search(cquery);
		Map<Long, UserCategory> categoryMap = CollectUtils.newHashMap();
		for (UserCategory category : categories) {
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
				entityDao.search(OqlBuilder.from(RestrictPattern.class, "pattern").select(
						"count(*)")));
		put("paramStat",
				entityDao.search(OqlBuilder.from(RestrictField.class, "param").select("count(*)")));
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
		String newCategory = get("newCategory");
		if (StringUtils.isNotBlank(newCategory)) {
			if (entityDao.get(UserCategory.class, "name", newCategory).isEmpty()) {
				UserCategoryBean category = new UserCategoryBean();
				category.setName(newCategory);
				entityDao.saveOrUpdate(category);
				return redirect("admin", "info.save.success");
			} else {
				return redirect("admin", "info.save.failure");
			}
		}
		Long categoryId = getLong("removeCategoryId");
		if (null != categoryId) {
			UserCategory category = entityDao.get(UserCategory.class, categoryId);
			if (null != category) {
				try {
					entityDao.remove(category);
				} catch (Exception e) {
					return redirect("admin", "info.remove.failure");
				}
			}
			return redirect("admin", "info.remove.success");
		}
		String newAdmin = get("newAdmin");
		if (StringUtils.isNotBlank(newAdmin)) {
			User user = authorityService.getUserService().get(newAdmin);
			if (null == user) {
				return redirect("admin", "info.save.failure");
			} else {
				if (authorityService.getUserService().isAdmin(user)) { return redirect("admin",
						"info.save.failure"); }
				AdminUser adminUser = new AdminUserBean();
				adminUser.setUser(user);
				adminUser.setCreatedAt(new Date());
				adminUser.setUpdatedAt(new Date());
				entityDao.saveOrUpdate(adminUser);
				return redirect("admin", "info.save.success");
			}
		}
		Long adminId = getLong("removeAdminId");
		if (null != adminId) {
			AdminUser adminUser = entityDao.get(AdminUser.class, adminId);
			if (null != adminUser) {
				entityDao.remove(adminUser);
			}
			return redirect("admin", "info.remove.success");
		}
		List<AdminUser> admins = entityDao.getAll(AdminUser.class);
		put("adminUsers", admins);
		List<UserCategory> categories = entityDao.getAll(UserCategory.class);
		put("categories", categories);
		return forward();
	}
}
