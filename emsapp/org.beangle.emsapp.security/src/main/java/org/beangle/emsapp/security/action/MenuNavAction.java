/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.action;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.StrUtils;
import org.beangle.ems.security.Resource;
import org.beangle.ems.security.User;
import org.beangle.ems.security.nav.Menu;
import org.beangle.ems.security.nav.MenuProfile;
import org.beangle.ems.security.nav.service.MenuService;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.util.HierarchyEntityUtil;

/**
 * 菜单浏览导航器
 * 
 * @author chaostone
 * @version $Id: MenuNavAction.java Jun 24, 2011 8:25:47 PM chaostone $
 */
public class MenuNavAction extends SecurityActionSupport {

	private MenuService menuService;

	@Override
	public String index() throws Exception {
		final String name = get("name");
		final Long menuId = getLong("menu.id");
		final Set<Menu> family;
		final Menu givenMenu;
		if (null != menuId) {
			givenMenu = entityDao.get(Menu.class, menuId);
			family = HierarchyEntityUtil.getFamily(givenMenu);
		} else {
			family = null;
			givenMenu = null;
		}

		User user = entityDao.get(User.class, getUserId());
		Long categoryId = getUserCategoryId();
		MenuProfile profile = getMenuProfile(categoryId);
		List<Menu> menus = Collections.emptyList();
		if (null != profile) {
			menus = menuService.getMenus(profile, user);
		}
		CollectionUtils.filter(menus, new Predicate() {
			public boolean evaluate(Object object) {
				Menu amenu = (Menu) object;
				if (null != family && !family.contains(amenu)) return false;
				if (StringUtils.isNotEmpty(name)) {
					if (!amenu.getChildren().isEmpty()) return false;
					StringBuilder searchTarget = new StringBuilder(StrUtils.concat(amenu.getName(),
							amenu.getTitle(), amenu.getRemark()));
					for (Resource res : amenu.getResources()) {
						searchTarget.append(StrUtils.concat(res.getName(), res.getTitle(), res.getRemark()));
					}
					return searchTarget.toString().contains(name);
				} else return true;
			}
		});
		if (StringUtils.isNotEmpty(name)) {
			HierarchyEntityUtil.addParent(menus, givenMenu);
			Collections.sort(menus);
		}

		List<Menu> menuPath = CollectUtils.newArrayList();
		if (null != givenMenu) {
			// menus.retainAll(family);
			menus.remove(givenMenu);
			menuPath = HierarchyEntityUtil.getPath(givenMenu);
		}
		put("menuPath", menuPath);
		put("menus", menus);
		put("tops", HierarchyEntityUtil.getRoots(menus));
		return forward();
	}

	public String search() {
		User user = entityDao.get(User.class, getUserId());
		Long categoryId = getLong("security.categoryId");
		if (null == categoryId) {
			categoryId = getUserCategoryId();
		}
		MenuProfile profile = getMenuProfile(categoryId);
		List<Menu> menus = menuService.getMenus(profile, user);
		List<Menu> menuPath = CollectUtils.newArrayList();
		Long menuId = getLong("menu.id");
		if (null != menuId) {
			Menu menu = entityDao.get(Menu.class, menuId);
			menus.retainAll(HierarchyEntityUtil.getFamily(menu));
			menus.remove(menu);
			menuPath = HierarchyEntityUtil.getPath(menu);
		}
		put("menuPath", menuPath);
		if (null != profile) {
			put("menus", menus);
		} else {
			put("menus", Collections.EMPTY_LIST);
		}
		put("tops", HierarchyEntityUtil.getRoots(menus));
		put("user", user);
		return forward();
	}

	public String access() {
		Long menuId = getLong("menu.id");
		Menu menu = entityDao.get(Menu.class, menuId);
		List<Menu> paths = HierarchyEntityUtil.getPath(menu);
		put("menu", menu);
		put("paths", paths);
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
