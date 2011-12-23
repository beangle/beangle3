/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.convention.factory;

public interface BeanNameFinder {

	/**
	 * the names of beans matching the given object type (excluding subclasses),
	 * or an empty array if none
	 * 
	 * @param type
	 * @return
	 */
	public String[] getBeanNames(Class<?> type);

}
