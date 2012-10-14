/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.nav.service;

import java.util.List;

import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.nav.Menu;
import org.beangle.security.blueprint.nav.MenuProfile;

/**
 * @author chaostone
 * @version $Id: MenuService.java Jun 5, 2011 9:24:23 PM chaostone $
 */
public interface MenuService {

  /**
   * 查询用户能够适用的菜单配置
   * 
   * @param user
   */
  List<MenuProfile> getProfiles(User user);

  /**
   * 查询角色能够适用的菜单配置
   * 
   * @param roles
   */
  List<MenuProfile> getProfiles(Role... roles);

  /**
   * 查询用户能够适用的单个菜单配置
   * 
   * @param user
   * @return profiles的第一个如果profileId is null
   */
  MenuProfile getProfile(User user, Long profileId);

  /**
   * 查询角色能够适用的单个菜单配置
   * 
   * @param role
   * @param profileId
   */
  MenuProfile getProfile(Role role, Long profileId);

  /**
   * 获取用户的直接权限范围内的资源和所具有角色的资源.
   * 
   * @param profile
   * @param user
   */
  List<Menu> getMenus(MenuProfile profile, User user);

  /**
   * 查询角色对应的菜单
   * 
   * @param profile
   * @param role
   * @param enabled
   */
  List<Menu> getMenus(MenuProfile profile, Role role, Boolean enabled);

  /**
   * 移动菜单到指定的位置
   * 
   * @param menu
   * @param location
   *          新的位置
   * @param indexno
   *          新位置的顺序号
   */
  void move(Menu menu, Menu location, int indexno);

}
