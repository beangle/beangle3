/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.userdetail;

import org.beangle.security.auth.BadCredentialsException;

public class UsernameNotFoundException extends BadCredentialsException {
	private static final long serialVersionUID = 1L;

	public UsernameNotFoundException() {

	}

	public UsernameNotFoundException(String msg) {
		super(msg);
	}

	public UsernameNotFoundException(String msg, Object extraInfo) {
		super(msg, extraInfo);
	}

}
