/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.io;

import java.net.URL;

import com.opensymphony.xwork2.util.ClassLoaderUtil;

public class ClasspathDocLoader implements StaticFileLoader {

	public URL getFile(String filename) {
		return ClassLoaderUtil.getResource(filename, getClass());
	}

}
