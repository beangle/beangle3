/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.access;

import javax.servlet.http.HttpServletRequest;

public class SimpleResourceExtractor implements ResourceExtractor {

	public String extract(HttpServletRequest request) {
		return extract(request.getServletPath());
	}

	public String extract(String uri) {
		int firstSlot = uri.indexOf('/');
		firstSlot++;
		int lastDot = uri.lastIndexOf('?');
		if (lastDot < 0) {
			lastDot = uri.length();
		}
		return uri.substring(firstSlot, lastDot);
	}

}
