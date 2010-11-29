/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.security.core.Authentication;
import org.beangle.security.core.session.SessionException;

/**
 * @author chaostone
 * @version $Id: SessionStrategy.java Nov 21, 2010 2:52:50 PM chaostone $
 */
public interface SessionStrategy {
	/**
	 * Performs Http session-related functionality when a new authentication
	 * occurs.
	 * 
	 * @throws SessionAuthenticationException
	 *             if it is decided that the authentication is not allowed for
	 *             the session. This will typically be because the user has too
	 *             many sessions open at once.
	 */
	void onAuthentication(Authentication authentication, HttpServletRequest request,
			HttpServletResponse response) throws SessionException;
}
