/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.context;

import org.beangle.security.auth.AnonymousAuthentication;
import org.beangle.security.core.Authentication;

public final class AuthenticationUtils {

	public static boolean isValid(Authentication auth) {
		return (null != auth && !AnonymousAuthentication.class.isAssignableFrom(auth.getClass()));
	}

	public static boolean hasValidAuthentication() {
		return isValid(SecurityContextHolder.getContext().getAuthentication());
	}
}
