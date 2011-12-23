/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.entity;

public interface Type {
	/**
	 * Is this type a collection type.
	 */
	public boolean isCollectionType();

	/**
	 * Is this type a component type. If so, the implementation must be castable
	 * to <tt>AbstractComponentType</tt>. A component type may own collections
	 * or associations and hence must provide certain extra functionality.
	 * 
	 * @return boolean
	 */
	public boolean isComponentType();

	/**
	 * Is this type an entity type?
	 * 
	 * @return boolean
	 */
	public boolean isEntityType();

	public Type getPropertyType(String property);

	public String getName();

	public Class<?> getReturnedClass();

	public Object newInstance();
}
