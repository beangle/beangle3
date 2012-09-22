/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.nav.service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.impl.AbstractHierarchyService;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.entity.util.HierarchyEntityUtils;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.function.FuncPermission;
import org.beangle.security.blueprint.nav.Menu;
import org.beangle.security.blueprint.nav.MenuProfile;
import org.beangle.security.blueprint.nav.model.MenuBean;

/**
 * @author chaostone
 * @version $Id: MenuServiceImpl.java Jun 5, 2011 9:25:49 PM chaostone $
 */
public class MenuServiceImpl extends AbstractHierarchyService<MenuBean, Menu> implements MenuService {

  public List<MenuProfile> getProfiles(User user) {
    List<Role> roles = user.getRoles();
    return getProfilesInternal(roles.toArray(new Role[roles.size()]));
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

  public MenuProfile getProfile(Role role, Long profileId) {
    List<Role> path = HierarchyEntityUtils.getPath(role);
    List<MenuProfile> profiles = getProfilesInternal(path.toArray(new Role[path.size()]));
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

  private List<MenuProfile> getProfilesInternal(Role... roles) {
    if (roles.length == 0) return Collections.emptyList();
    OqlBuilder<MenuProfile> query = OqlBuilder.from(MenuProfile.class, "menuProfile");
    query.where("menuProfile.role in(:roles)", roles).cacheable();
    return entityDao.search(query);
  }

  public List<MenuProfile> getProfiles(Role... roles) {
    if (roles.length == 0) return Collections.emptyList();
    Set<Role> allRoles = CollectUtils.newHashSet();
    for (Role role : roles) {
      allRoles.addAll(HierarchyEntityUtils.getPath(role));
    }
    OqlBuilder<MenuProfile> query = OqlBuilder.from(MenuProfile.class, "menuProfile");
    query.where("menuProfile.role in(:roles)", allRoles).cacheable();
    return entityDao.search(query);
  }

  public List<Menu> getMenus(MenuProfile profile, User user) {
    Set<Menu> menus = CollectUtils.newHashSet();
    for (final Role role : user.getRoles()) {
      if (role.isEnabled()) menus.addAll(getMenus(profile, role, Boolean.TRUE));
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
   * 查询角色对应的菜单
   */
  public List<Menu> getMenus(MenuProfile profile, Role role, Boolean enabled) {
    OqlBuilder<Menu> query = buildMenuQuery(profile, role).cacheable();
    if (null != enabled) query.where("menu.enabled=:enabled", enabled);
    List<Menu> menus = entityDao.search(query);
    return addParentMenus(CollectUtils.newHashSet(menus));
  }

  private OqlBuilder<Menu> buildMenuQuery(MenuProfile profile, Role role) {
    OqlBuilder<Menu> builder = OqlBuilder.from(Menu.class);
    builder.join("menu.resources", "mr");
    builder.where("exists(from " + FuncPermission.class.getName()
        + " a where a.role=:role and a.resource=mr)", role);
    if (null != profile) builder.where("menu.profile=:profile", profile);
    return builder;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  protected List<MenuBean> getTopNodes(MenuBean menu) {
    List sibling = CollectUtils.newArrayList();
    for (Menu m : menu.getProfile().getMenus())
      if (null == m.getParent()) sibling.add(m);
    return sibling;
  }

  public void move(Menu menu, Menu location, int indexno) {
    this.move((MenuBean) menu, (MenuBean) location, indexno);
  }
}
