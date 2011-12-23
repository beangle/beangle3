/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.auth.preauth;

import javax.servlet.http.HttpServletRequest;

/**
 * Source of the username supplied with pre-authenticated authentication
 * request. The username can be supplied in the request: in cookie, request
 * header, request parameter or as ServletRequest.getRemoteUser().
 */
public interface UsernameSource {
	/**
	 * Obtain username supplied in the request.
	 * 
	 * @param request
	 *            with username
	 * @return username or null if not supplied
	 */
	public String obtainUsername(HttpServletRequest request);
}
