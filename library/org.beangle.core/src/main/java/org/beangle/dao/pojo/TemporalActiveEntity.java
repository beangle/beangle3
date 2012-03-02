/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.dao.pojo;

import java.util.Date;

/**
 * 有时效性的实体
 * </p>
 * 指有具体生效时间和失效时间的实体。一般生效时间不能为空，失效时间可以为空。
 * 具体时间采用日期时间格式便于比对。
 * 
 * @author chaostone
 */

public interface TemporalActiveEntity {

	/**
	 * 获得生效日期
	 * 
	 * @return 生效日期
	 */
	public Date getEffectAt();
	/**
	 * 获得失效日期
	 * 
	 * @return 失效日期
	 */
	public Date getInvalidAt();

}
