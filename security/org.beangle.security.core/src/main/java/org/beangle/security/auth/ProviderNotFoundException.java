/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.auth;

import org.beangle.security.core.AuthenticationException;

public class ProviderNotFoundException extends AuthenticationException {
	private static final long serialVersionUID = -400870612941880077L;

	public ProviderNotFoundException(String message) {
		super(message);
	}

}
