/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.service;

import java.util.Collection;
import java.util.List;

import org.beangle.ems.security.UserProperty;

public interface UserDataResolver {

	/**
	 * Marshal list of objects to text format
	 * 
	 * @param field
	 * @param items
	 * @return
	 */
	public String marshal(UserProperty field, Collection<?> items);

	/**
	 * Convert text to list of objects
	 * 
	 * @param <T>
	 * @param field
	 * @param text
	 * @return
	 */
	public <T> List<T> unmarshal(UserProperty field, String text);
}
