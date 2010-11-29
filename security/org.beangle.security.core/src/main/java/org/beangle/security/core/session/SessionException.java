/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session;

import org.beangle.security.core.AuthenticationException;

public class SessionException extends AuthenticationException {
	private static final long serialVersionUID = -2827989849698493720L;

	public SessionException() {
		super();
	}

	public SessionException(String msg) {
		super(msg);
	}

}
