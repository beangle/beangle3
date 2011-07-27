/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.nav.service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.util.HierarchyEntityUtil;
import org.beangle.ems.security.Authority;
import org.beangle.ems.security.Category;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.GroupMember;
import org.beangle.ems.security.User;
import org.beangle.ems.security.nav.Menu;
import org.beangle.ems.security.nav.MenuProfile;
import org.beangle.ems.security.nav.model.MenuBean;
import org.beangle.ems.security.service.UserService;

/**
 * @author chaostone
 * @version $Id: MenuServiceImpl.java Jun 5, 2011 9:25:49 PM chaostone $
 */
public class MenuServiceImpl extends BaseServiceImpl implements MenuService {

	private UserService userService;

	public List<MenuProfile> getProfiles(User user) {
		Set<Category> categories = CollectUtils.newHashSet();
		categories.add(user.getDefaultCategory());
		categories.addAll(user.getCategories());
		OqlBuilder<MenuProfile> query = OqlBuilder.from(MenuProfile.class, "menuProfile");
		query.where("menuProfile.category in(:categories)", categories).cacheable();
		return entityDao.search(query);
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
		HierarchyEntityUtil.addParent(menus);
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

	public void move(Menu menu, Menu location, int indexno) {
		if (ObjectUtils.equals(menu.getParent(), location)) {
			if (NumberUtils.toInt(((MenuBean) menu).getIndexno()) != indexno) {
				shiftCode(menu, location, indexno);
			}
		} else {
			if (null != menu.getParent()) {
				menu.getParent().getChildren().remove(menu);
			}
			menu.setParent(location);
			shiftCode(menu, location, indexno);
		}
	}

	private void shiftCode(Menu menu, Menu newParent, int indexno) {
		List<Menu> sibling = null;
		if (null != newParent) sibling = newParent.getChildren();
		else {
			sibling = CollectUtils.newArrayList();
			for (Menu m : menu.getProfile().getMenus()) {
				if (null == m.getParent()) sibling.add(m);
			}
		}
		Collections.sort(sibling);
		sibling.remove(menu);
		indexno--;
		if (indexno > sibling.size()) {
			indexno = sibling.size();
		}
		sibling.add(indexno, menu);
		int nolength = String.valueOf(sibling.size()).length();
		Set<Menu> menus = CollectUtils.newHashSet();
		for (int seqno = 1; seqno <= sibling.size(); seqno++) {
			Menu one = sibling.get(seqno - 1);
			generateCode(one, StringUtils.leftPad(String.valueOf(seqno), nolength, '0'), menus);
		}
		entityDao.saveOrUpdate(menus);
	}

	private void generateCode(Menu menu, String indexno, Set<Menu> menus) {
		menus.add(menu);
		if (null != indexno) {
			((MenuBean) menu).generateCode(indexno);
		} else {
			((MenuBean) menu).generateCode();
		}
		if (null != menu.getChildren()) {
			for (Menu m : menu.getChildren()) {
				generateCode(m, null, menus);
			}
		}
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
