/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.pojo;

import java.util.Date;

/**
 * 有时效性的实体
 * </p>
 * 指有具体生效时间和失效时间的实体。一般生效时间不能为空，失效时间可以为空。
 * 具体时间采用日期、时间格式便于比对。
 * 
 * @author chaostone
 */

public interface TemporalActiveEntity {

	/**
	 * 获得生效时间
	 * 
	 * @return 生效时间
	 */
	public Date getEffectiveAt();

	/**
	 * 设置生效时间
	 * 
	 * @param effectiveAt 生效时间
	 */
	public void setEffectiveAt(Date effectiveAt);

	/**
	 * 获得失效时间
	 * 
	 * @return 失效时间
	 */
	public Date getInvalidAt();

	/**
	 * 设置失效时间
	 * 
	 * @param invalidAt 失效时间
	 */
	public void setInvalidAt(Date invalidAt);
}
