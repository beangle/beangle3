/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.entity.meta;

import org.beangle.model.pojo.LongIdEntity;

/**
 * 属性元信息
 * </p>
 * 记录实体的属性元信息描述。
 */
public interface PropertyMeta extends LongIdEntity {

	/**
	 * 获得实体元信息
	 * 
	 * @return 实体元信息
	 */
	public EntityMeta getMeta();

	/**
	 * 设置实体元信息
	 * 
	 * @param meta 实体元信息
	 */
	public void setMeta(EntityMeta meta);

	/**
	 * 获得属性名
	 * 
	 * @return 属性名
	 */
	public String getName();

	/**
	 * 设置属性名
	 * 
	 * @param name 属性名
	 */
	public void setName(String name);

	/**
	 * 获得属性类型
	 * 
	 * @return 属性类型
	 */
	public String getType();

	/**
	 * 设置属性类型
	 * 
	 * @param type 属性类型
	 */
	public void setType(String type);

	/**
	 * 获得备注
	 * 
	 * @return 备注
	 */
	public String getComments();

	/**
	 * 设置备注
	 * 
	 * @param comment 备注
	 */
	public void setComments(String comments);
}
