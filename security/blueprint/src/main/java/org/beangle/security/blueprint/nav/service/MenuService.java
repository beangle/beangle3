/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.blueprint.nav.service;

import java.util.List;

import org.beangle.security.blueprint.Profile;
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
  MenuProfile getProfile(User user, Integer profileId);

  /**
   * 查询角色能够适用的单个菜单配置
   * 
   * @param role
   * @param profileId
   */
  MenuProfile getProfile(Role role, Integer profileId);

  /**
   * 获取用户的直接权限范围内的资源和所具有角色的资源.
   * 
   * @param profile
   * @param user
   */
  List<Menu> getMenus(MenuProfile profile, User user, List<Profile> userProfiles);

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
