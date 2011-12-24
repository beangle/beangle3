/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.io;

import java.net.URL;

public class ClasspathDocLoader implements StaticFileLoader {

	public URL getFile(String filename) {
		URL url = Thread.currentThread().getContextClassLoader().getResource(filename);

		if (url == null) {
			url = ClasspathDocLoader.class.getClassLoader().getResource(filename);
		}
		if ((url == null) && (filename != null) && ((filename.length() == 0) || (filename.charAt(0) != '/'))) { return getFile('/' + filename); }
		return url;
	}

}
