/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.event;

import java.util.List;

import org.beangle.dao.Entity;

/**
 * 实体操作相关事件
 * 
 * @author chaostone
 * @version $Id: EntityOperationEvent.java Mar 3, 2012 9:05:40 PM chaostone $
 */
@SuppressWarnings("serial")
public abstract class BusinessEntityEvent<T extends Entity<?>> extends BusinessEvent {

	private final Class<T> clazz;

	public BusinessEntityEvent(Class<T> clazz, List<T> source) {
		super(source);
		this.clazz = clazz;
	}
	public Class<T> getClazz() {
		return clazz;
	}

	@SuppressWarnings("unchecked")
	public List<T> getEntities() {
		return (List<T>) source;
	}
}
