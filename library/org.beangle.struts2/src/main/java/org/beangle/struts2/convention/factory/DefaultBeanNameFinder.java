/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.convention.factory;

public class DefaultBeanNameFinder implements BeanNameFinder {

	public String[] getBeanNames(Class<?> type) {
		return new String[] { type.getName() };
	}

}
