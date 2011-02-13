/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.access;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.beangle.security.access.AccessDeniedException;

public interface AccessDeniedHandler {

	/**
	 * Handles an access denied failure.
	 * 
	 * @param request
	 *            that resulted in an <code>AccessDeniedException</code>
	 * @param response
	 *            so that the user agent can be advised of the failure
	 * @param accessDeniedException
	 *            that caused the invocation
	 * @throws IOException
	 *             in the event of an IOException
	 * @throws ServletException
	 *             in the event of a ServletException
	 */
	void handle(ServletRequest request, ServletResponse response, AccessDeniedException accessDeniedException)
			throws IOException, ServletException;
}
