/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.nav.service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.ems.security.Authority;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.User;
import org.beangle.ems.security.nav.Menu;
import org.beangle.ems.security.nav.MenuProfile;
import org.beangle.ems.security.nav.model.MenuBean;
import org.beangle.model.persist.impl.AbstractHierarchyService;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.util.HierarchyEntityUtils;

/**
 * @author chaostone
 * @version $Id: MenuServiceImpl.java Jun 5, 2011 9:25:49 PM chaostone $
 */
public class MenuServiceImpl extends AbstractHierarchyService<MenuBean> implements MenuService {

	public List<MenuProfile> getProfiles(User user) {
		List<Group> groups = user.getGroups();
		return getProfilesInternal(groups.toArray(new Group[groups.size()]));
	}

	public MenuProfile getProfile(User user, Long profileId) {
		List<MenuProfile> profiles = getProfiles(user);
		if (profiles.isEmpty()) return null;
		MenuProfile profile = profiles.get(0);
		if (null != profileId) {
			for (MenuProfile mp : profiles) {
				if (mp.getId().equals(profileId)) {
					profile = mp;
					break;
				}
			}
		}
		return profile;
	}

	public MenuProfile getProfile(Group group, Long profileId) {
		List<Group> path = HierarchyEntityUtils.getPath(group);
		List<MenuProfile> profiles = getProfilesInternal(path.toArray(new Group[path.size()]));
		if (profiles.isEmpty()) return null;
		MenuProfile profile = profiles.get(0);
		if (null != profileId) {
			for (MenuProfile mp : profiles) {
				if (mp.getId().equals(profileId)) {
					profile = mp;
					break;
				}
			}
		}
		return profile;
	}

	private List<MenuProfile> getProfilesInternal(Group... groups) {
		if (groups.length == 0) return Collections.emptyList();
		OqlBuilder<MenuProfile> query = OqlBuilder.from(MenuProfile.class, "menuProfile");
		query.where("menuProfile.group in(:group)", groups).cacheable();
		return entityDao.search(query);
	}

	public List<MenuProfile> getProfiles(Group... groups) {
		if (groups.length == 0) return Collections.emptyList();
		Set<Group> allGroups = CollectUtils.newHashSet();
		for (Group group : groups) {
			allGroups.addAll(HierarchyEntityUtils.getPath(group));
		}
		OqlBuilder<MenuProfile> query = OqlBuilder.from(MenuProfile.class, "menuProfile");
		query.where("menuProfile.group in(:group)", allGroups).cacheable();
		return entityDao.search(query);
	}

	public List<Menu> getMenus(MenuProfile profile, User user) {
		Set<Menu> menus = CollectUtils.newHashSet();
		for (final Group group : user.getGroups()) {
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
		HierarchyEntityUtils.addParent(menus);
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

	// public void move(Menu menu, Menu location, int indexno) {
	// if (ObjectUtils.equals(menu.getParent(), location)) {
	// if (NumberUtils.toInt(((MenuBean) menu).getIndexno()) != indexno) {
	// shiftCode(menu, location, indexno);
	// }
	// } else {
	// if (null != menu.getParent()) {
	// menu.getParent().getChildren().remove(menu);
	// }
	// menu.setParent(location);
	// shiftCode(menu, location, indexno);
	// }
	// }
	//
	// private void shiftCode(Menu menu, Menu newParent, int indexno) {
	// List<Menu> sibling = null;
	// if (null != newParent) sibling = newParent.getChildren();
	// else {
	// sibling = CollectUtils.newArrayList();
	// for (Menu m : menu.getProfile().getMenus()) {
	// if (null == m.getParent()) sibling.add(m);
	// }
	// }
	// Collections.sort(sibling);
	// sibling.remove(menu);
	// indexno--;
	// if (indexno > sibling.size()) {
	// indexno = sibling.size();
	// }
	// sibling.add(indexno, menu);
	// int nolength = String.valueOf(sibling.size()).length();
	// Set<Menu> menus = CollectUtils.newHashSet();
	// for (int seqno = 1; seqno <= sibling.size(); seqno++) {
	// Menu one = sibling.get(seqno - 1);
	// generateCode(one, StringUtils.leftPad(String.valueOf(seqno), nolength, '0'), menus);
	// }
	// entityDao.saveOrUpdate(menus);
	// }
	//
	// private void generateCode(Menu menu, String indexno, Set<Menu> menus) {
	// menus.add(menu);
	// if (null != indexno) {
	// ((MenuBean) menu).generateCode(indexno);
	// } else {
	// ((MenuBean) menu).generateCode();
	// }
	// if (null != menu.getChildren()) {
	// for (Menu m : menu.getChildren()) {
	// generateCode(m, null, menus);
	// }
	// }
	// }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected List<MenuBean> getTopNodes(MenuBean menu) {
		List sibling = CollectUtils.newArrayList();
		for (Menu m : menu.getProfile().getMenus()) {
			if (null == m.getParent()) sibling.add(m);
		}
		return sibling;
	}

	public void move(Menu menu, Menu location, int indexno) {
		this.move((MenuBean) menu, (MenuBean) location, indexno);
	}

}
