/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.restrict;

import org.beangle.dao.pojo.LongIdEntity;

/**
 * 资源访问限制
 * 
 * @author chaostone
 */
public interface Restriction extends LongIdEntity, Cloneable {
	public static final String ALL = "*";
	/**
	 * 限制内容
	 * 
	 * @return
	 */
	public String getContent();

	/**
	 * 针对实体
	 * 
	 * @return
	 */
	public RestrictEntity getEntity();

	/**
	 * 备注
	 * 
	 * @return
	 */
	public String getRemark();

	/**
	 * 是否有效
	 * 
	 * @return
	 */
	public boolean isEnabled();
}
