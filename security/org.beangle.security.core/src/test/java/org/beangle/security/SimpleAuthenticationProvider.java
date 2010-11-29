/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security;

import org.beangle.security.auth.AuthenticationProvider;
import org.beangle.security.auth.UsernamePasswordAuthentication;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.AuthenticationException;

public class SimpleAuthenticationProvider implements AuthenticationProvider {

	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		return auth;
	}

	public boolean supports(Class<? extends Authentication> authTokenType) {
		return (UsernamePasswordAuthentication.class.isAssignableFrom(authTokenType));
	}

}
