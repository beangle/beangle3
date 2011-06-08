/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint;

import java.util.Set;

import org.beangle.model.pojo.HierarchyEntity;
import org.beangle.model.pojo.LongIdEntity;

/**
 * 系统菜单
 * 
 * @author chaostone
 */
public interface Menu extends LongIdEntity, Comparable<Menu>,HierarchyEntity<Menu> {

	/**
	 * 同级菜单索引号
	 * 
	 * @return
	 */
	public String getCode();

	public void setCode(String code);

	/**
	 * 同级菜单序列号
	 * 
	 * @return
	 */
	public int getIndexno();

	public void setIndexno(int indexno);

	/**
	 * 菜单标题
	 * 
	 * @return
	 */
	public String getTitle();

	public void setTitle(String title);

	public String getEngTitle();

	public void setEngTitle(String engTitle);

	public String getEntry();

	public void setEntry(String entry);

	public String getRemark();

	public void setRemark(String remark);

	public Set<Resource> getResources();

	public void setResources(Set<Resource> resources);

	public boolean isEnabled();

	public void setEnabled(boolean enabled);

	public MenuProfile getProfile();

	public void setProfile(MenuProfile profile);
}
