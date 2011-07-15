/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.portal.action;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

import org.beangle.ems.security.SecurityUtils;
import org.beangle.ems.security.User;
import org.beangle.ems.security.nav.MenuProfile;
import org.beangle.ems.security.nav.service.MenuService;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.util.HierarchyEntityUtil;

/**
 * 加载用户登陆信息
 * 
 * @author chaostone
 */
public class HomeAction extends SecurityActionSupport {

	private MenuService menuService;
	
	public String index() {
		User user = entityDao.get(User.class, getUserId());
		Long categoryId = getUserCategoryId();
		put("categoryId", categoryId);
		MenuProfile profile = getMenuProfile(categoryId);
		if (null != profile) {
			put("menus", HierarchyEntityUtil.getRoots(menuService.getMenus(profile, user)));
		} else {
			put("menus", Collections.EMPTY_LIST);
		}
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
