/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.auth;

import org.beangle.security.core.AuthenticationException;

public class BadCredentialsException extends AuthenticationException {

	private static final long serialVersionUID = 3165517475635935263L;

	public BadCredentialsException() {
	}

	public BadCredentialsException(String message) {
		super(message);
	}

	public BadCredentialsException(String msg, Object extraInfo) {
		super(msg, extraInfo);
	}

}
