/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.packagekit.maven;

import org.apache.commons.lang.StringUtils;
import org.beangle.packagekit.internal.SimpleResource;

public class MavenUtils {

	public static SimpleResource build(String id) {
		SimpleResource resource = new SimpleResource();
		String name = StringUtils.substringBeforeLast(id, "-");
		resource.setId(id);
		resource.setName(name);
		return resource;
	}
}
