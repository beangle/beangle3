/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.service;

import java.util.List;

import org.beangle.ems.security.Group;
import org.beangle.ems.security.Menu;
import org.beangle.ems.security.MenuProfile;
import org.beangle.ems.security.User;

/**
 * @author chaostone
 * @version $Id: MenuService.java Jun 5, 2011 9:24:23 PM chaostone $
 */
public interface MenuService {

	/**
	 * 获取用户的直接权限范围内的资源和所具有用户组的资源.
	 * 
	 * @param userId
	 * @return
	 */
	public List<Menu> getMenus(MenuProfile profile, User user);

	/**
	 * 用户组内对应的资源
	 * 
	 * @param groupId
	 * @return
	 */
	public List<Menu> getMenus(MenuProfile profile, Group group, Boolean enabled);

	/**
	 * 移动菜单到指定的位置
	 * 
	 * @param menu
	 * @param location
	 *            新的位置
	 * @param indexno
	 *            新位置的顺序号
	 */
	public void move(Menu menu, Menu location, int indexno);

}
