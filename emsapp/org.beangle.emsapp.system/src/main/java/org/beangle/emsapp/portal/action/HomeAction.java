/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.portal.action;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

import org.beangle.emsapp.security.SecurityUtils;
import org.beangle.emsapp.security.User;
import org.beangle.emsapp.security.nav.MenuProfile;
import org.beangle.emsapp.security.nav.service.MenuService;
import org.beangle.emsapp.web.action.SecurityActionSupport;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.util.HierarchyEntityUtils;

/**
 * 加载用户登陆信息
 * 
 * @author chaostone
 */
public class HomeAction extends SecurityActionSupport {

	private MenuService menuService;

	public String index() {
		User user = entityDao.get(User.class, getUserId());
		List<MenuProfile> profiles = menuService.getProfiles(user);
		if (profiles.isEmpty()) {
			put("menus", Collections.EMPTY_LIST);
		} else {
			MenuProfile profile = menuService.getProfile(user, getLong("security.menuProfileId"));
			put("menus", HierarchyEntityUtils.getRoots(menuService.getMenus(profile, user)));
		}
		put("menuProfiles",profiles);
		put("user", user);
		return forward();
	}

	public String welcome() {
		put("date", new Date(System.currentTimeMillis()));
		put("user", SecurityUtils.getFullname());
		return forward();
	}

	protected MenuProfile getMenuProfile(Long categoryId) {
		OqlBuilder<MenuProfile> query = OqlBuilder.from(MenuProfile.class, "mp");
		query.where("category.id=:categoryId", categoryId).cacheable();
		List<MenuProfile> mps = entityDao.search(query);
		if (mps.isEmpty()) {
			return null;
		} else {
			return mps.get(0);
		}
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

}
