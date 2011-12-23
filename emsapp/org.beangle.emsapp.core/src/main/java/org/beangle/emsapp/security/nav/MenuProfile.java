/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.nav;

import java.util.List;

import org.beangle.emsapp.security.Group;
import org.beangle.model.pojo.LongIdEntity;

public interface MenuProfile extends LongIdEntity {

	public String getName();

	public void setName(String name);

	public List<Menu> getMenus();

	public void setMenus(List<Menu> menus);

	public Group getGroup();

	public void setGroup(Group group);

	/**
	 * 资源状态
	 * 
	 * @return
	 */
	public boolean isEnabled();

	/**
	 * 设置资源状态
	 * 
	 * @param IsActive
	 * @return
	 */
	public void setEnabled(boolean isEnabled);
}
