/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security;

import org.beangle.security.core.Authentication;

public class MockAuthorityManager implements AuthorityManager {

	public boolean isAuthorized(Authentication auth, Object resource) {
		return false;
	}

}
