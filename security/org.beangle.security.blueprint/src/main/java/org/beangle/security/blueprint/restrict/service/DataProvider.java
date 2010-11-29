/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.restrict.service;

import java.util.List;

/**
 * @author chaostone
 * @version $Id: SourceProvider.java Nov 9, 2010 7:18:38 PM chaostone $
 */
public interface DataProvider {

	/**
	 * extract data from source
	 * @param <T>
	 * @param type
	 * @param source
	 * @return
	 */
	public <T> List<T> getData(Class<T> type, String source);

	/**
	 * transform object to String
	 * @param objects
	 * @return
	 */
	public String asString(List<?> objects);
	
	/**
	 * provider's unique name
	 * @return
	 */
	public String getName();
}
