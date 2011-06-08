/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.util.HierarchyEntityUtil;
import org.beangle.security.blueprint.Authority;
import org.beangle.security.blueprint.Group;
import org.beangle.security.blueprint.GroupMember;
import org.beangle.security.blueprint.Menu;
import org.beangle.security.blueprint.MenuProfile;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.model.MenuBean;

/**
 * @author chaostone
 * @version $Id: MenuServiceImpl.java Jun 5, 2011 9:25:49 PM chaostone $
 */
public class MenuServiceImpl extends BaseServiceImpl implements MenuService {

	private UserService userService;

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
		HierarchyEntityUtil.sort(menuList, "indexno");
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

	public void move(Menu menu, Menu location, int indexno) {
		if (ObjectUtils.equals(menu.getParent(), location)) {
			if (menu.getIndexno() != indexno) {
				menu.setIndexno(indexno);
				generateCode(menu);
				shiftCode(menu, location, indexno);
			}
		} else {
			if (null != menu.getParent()) {
				menu.getParent().getChildren().remove(menu);
			}
			menu.setParent(location);
			menu.setIndexno(indexno);
			generateCode(menu);
			shiftCode(menu, location, indexno);
		}
	}

	private void shiftCode(Menu menu, Menu newParent, int indexno) {
		List<Menu> sibling = null;
		if (null != newParent) sibling = newParent.getChildren();
		else sibling = menu.getProfile().getMenus();

		Collections.sort(sibling);
		int restart = 0;
		for (int i = 0; i < sibling.size(); i++) {
			Menu one = sibling.get(i);
			if (ObjectUtils.equals(one.getParent(), menu.getParent())) {
				if (!ObjectUtils.equals(one.getId(), menu.getId())) {
					restart++;
					if (restart == indexno) restart++;
					one.setIndexno(restart);
					generateCode(one);
				}
			}
		}
	}

	private void generateCode(Menu menu) {
		((MenuBean) menu).generateCode();
		for (Menu m : menu.getChildren()) {
			generateCode(m);
		}
		entityDao.saveOrUpdate(menu);
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
