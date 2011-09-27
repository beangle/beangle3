/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.service;

import java.util.List;

import org.beangle.ems.security.UserProperty;

/**
 * @author chaostone
 * @version $Id: SourceProvider.java Nov 9, 2010 7:18:38 PM chaostone $
 */
public interface UserDataProvider {

	/**
	 * extract data from source
	 * 
	 * @param <T>
	 * @param type
	 * @param source
	 * @return
	 */
	public <T> List<T> getData(UserProperty field, String source);

	/**
	 * provider's unique name
	 * 
	 * @return
	 */
	public String getName();
}
