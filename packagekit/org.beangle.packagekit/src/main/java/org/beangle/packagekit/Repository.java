/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.packagekit;

/**
 * 仓库
 * 
 * @author chaostone
 */
public interface Repository {

	public String getBaseUrl();

	public String getLocation(Resource resource);

	public Registry getRegistry();

	public String getProtocal();
}
