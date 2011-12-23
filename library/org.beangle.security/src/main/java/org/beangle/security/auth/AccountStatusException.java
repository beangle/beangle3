/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.auth;

import org.beangle.security.core.AuthenticationException;

public class AccountStatusException extends AuthenticationException {
	private static final long serialVersionUID = 5750259152188732891L;

	public AccountStatusException() {
		super();
	}

	public AccountStatusException(String message) {
		super(message);
	}

	public AccountStatusException(String msg, Object extraInformation) {
		super(msg, extraInformation);
	}
}
