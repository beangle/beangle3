/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.packagekit;

import java.util.List;

public interface Resolver {

	/**
	 * 资源依赖的其他资源
	 * 
	 * @param resource
	 * @return
	 */
	List<Resource> getRequires(Resource resource);

	/**
	 * 依赖该资源的其他资源
	 * 
	 * @param resource
	 * @return
	 */
	List<Resource> getDependencies(Resource resource);
}
