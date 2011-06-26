/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.nav;

import java.util.Set;

import org.beangle.ems.security.Resource;
import org.beangle.model.pojo.HierarchyEntity;
import org.beangle.model.pojo.LongIdEntity;

/**
 * 系统菜单
 * 
 * @author chaostone
 */
public interface Menu extends LongIdEntity, Comparable<Menu>, HierarchyEntity<Menu> {

	/**
	 * 同级菜单索引号
	 * 
	 * @return
	 */
	public String getCode();

	/**
	 * 设置代码
	 * 
	 * @param code
	 */
	public void setCode(String code);

	/**
	 * 菜单的层级，从1开始
	 * 
	 * @return
	 */
	public int getDepth();

	/**
	 * 菜单名称
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 设置菜单名称
	 * 
	 * @param name
	 */
	public void setName(String name);

	/**
	 * 菜单标题
	 * 
	 * @return
	 */
	public String getTitle();

	/**
	 * 设置菜单标题
	 * 
	 * @param title
	 */
	public void setTitle(String title);

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
