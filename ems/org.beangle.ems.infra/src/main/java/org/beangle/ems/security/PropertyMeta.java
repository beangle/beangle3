/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security;

import org.beangle.model.pojo.LongIdEntity;

/**
 * 用户自定义属性
 * 
 * @author chaostone
 * @since 2011-09-22
 */
public interface PropertyMeta extends LongIdEntity {

	/**
	 * 名称
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 值类型
	 * 
	 * @return
	 */
	public String getValueType();

	/**
	 * 主键属性名
	 * 
	 * @return
	 */
	public String getKeyName();

	/**
	 * 其它属性列表
	 * 
	 * @return
	 */
	public String getPropertyNames();

	/**
	 * 数据源提供者
	 * 
	 * @return
	 */
	public String getSource();

	/**
	 * 是否为集合类型
	 * 
	 * @return
	 */
	public boolean isMultiple();

	/**
	 * 备注
	 * 
	 * @return
	 */
	public String getRemark();
}
