/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.packagekit;

import java.util.List;

/**
 * 注册表
 * 
 * @author chaostone
 */
public interface Registry {

	public List<Resource> getResources();

	public Resource getResource(String name);

	public List<Resource> getUpdates(Registry newer);
}
