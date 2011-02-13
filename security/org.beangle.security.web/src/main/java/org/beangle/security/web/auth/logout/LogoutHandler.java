/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.auth.logout;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.security.core.Authentication;

/**
 * Indicates a class that is able to participate in logout handling.
 * <p>
 * Called by {@link LogoutFilter}.
 */
public interface LogoutHandler {

	/**
	 * Causes a logout to be completed. The method must complete successfully.
	 * 
	 * @param request
	 *            the HTTP request
	 * @param response
	 *            the HTTP resonse
	 * @param authentication
	 *            the current principal details
	 */
	void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication);
}
