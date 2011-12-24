/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.restrict;

import org.beangle.model.pojo.LongIdEntity;

/**
 * 数据限制对象
 * 
 * @author chaostone
 */
public interface RestrictEntity extends LongIdEntity {

	/**
	 * 名称
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 实体类名
	 * 
	 * @return
	 */
	public String getType();

	/**
	 * 备注
	 * 
	 * @return
	 */
	public String getRemark();

}
