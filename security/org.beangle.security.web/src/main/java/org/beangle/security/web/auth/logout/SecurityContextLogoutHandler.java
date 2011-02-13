/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.auth.logout;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.Validate;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.context.SecurityContextHolder;

/**
 * Performs a logout by modifying the
 * {@link org.beangle.security.core.context.SecurityContextHolder}.
 * <p>
 * Will also invalidate the {@link HttpSession} if {@link #isInvalidateHttpSession()} is
 * <code>true</code> and the session is not <code>null</code>.
 */
public class SecurityContextLogoutHandler implements LogoutHandler {
	private boolean invalidateHttpSession = false;

	/**
	 * Requires the request to be passed in.
	 * 
	 * @param request
	 *            from which to obtain a HTTP session (cannot be null)
	 * @param response
	 *            not used (can be <code>null</code>)
	 * @param authentication
	 *            not used (can be <code>null</code>)
	 */
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		Validate.notNull(request, "HttpServletRequest required");
		if (invalidateHttpSession) {
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate();
			}
		}
		SecurityContextHolder.clearContext();
	}

	public boolean isInvalidateHttpSession() {
		return invalidateHttpSession;
	}

	/**
	 * Causes the {@link HttpSession} to be invalidated when this {@link LogoutHandler} is invoked.
	 * Defaults to true.
	 * 
	 * @param invalidateHttpSession
	 *            true if you wish the session to be invalidated (default) or
	 *            false if it should not be.
	 */
	public void setInvalidateHttpSession(boolean invalidateHttpSession) {
		this.invalidateHttpSession = invalidateHttpSession;
	}

}
