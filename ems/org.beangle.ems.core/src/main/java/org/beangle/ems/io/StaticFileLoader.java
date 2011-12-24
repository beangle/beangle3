/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.io;

import java.net.URL;

/**
 * 静态文件加载器
 * 
 * @author chaostone
 */
public interface StaticFileLoader {

	public URL getFile(String filename);
}
