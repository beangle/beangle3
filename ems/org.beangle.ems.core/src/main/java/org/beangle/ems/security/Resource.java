/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security;

import org.beangle.dao.pojo.LongIdEntity;

/** 系统资源.<br>
 * 
 * @author chaostone 2008-7-28 */
public interface Resource extends LongIdEntity {

	/** 资源名称
	 * 
	 * @return */
	public String getName();

	/** 资源名称
	 * 
	 * @param name */
	public void setName(String name);

	/** 资源标题
	 * 
	 * @return */
	public String getTitle();

	/** 资源标题
	 * 
	 * @param title */
	public void setTitle(String title);

	/** 返回资源描述
	 * 
	 * @return */
	public String getRemark();

	/** 资源状态
	 * 
	 * @return */
	public boolean isEnabled();

	/** 设置资源状态
	 * 
	 * @param IsActive
	 * @return */
	public void setEnabled(boolean isEnabled);

	public Scope getScope();

	public static enum Scope {
		/** 不受保护的公共资源 */
		PUBLIC(0),
		/** 受保护的公有资源 */
		PROTECTED(1),
		/** 受保护的私有资源 */
		PRIVATE(2);
		int value;

		private Scope(int value) {
			this.value = value;
		}
	}

}
