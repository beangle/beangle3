/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model;

import java.io.Serializable;

/**
 * 实体类接口
 * 
 * @author chaostone
 */
public interface Entity<ID extends Serializable> extends Serializable {

	/**
	 * 返回实体的id
	 * 
	 * @return
	 */
	public ID getEntityId();

	/**
	 * 是否是持久化对象
	 * 
	 * @return
	 */
	public boolean isPersisted();

	/**
	 * 是否为未持久化对象
	 */
	public boolean isTransient();
}
