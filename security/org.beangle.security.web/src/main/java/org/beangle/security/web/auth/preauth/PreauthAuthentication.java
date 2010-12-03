/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.auth.preauth;

import org.beangle.security.auth.UsernamePasswordAuthentication;
import org.beangle.security.core.GrantedAuthority;

public class PreauthAuthentication extends UsernamePasswordAuthentication {

	public PreauthAuthentication(Object aPrincipal, Object aCredentials) {
		super(aPrincipal, aCredentials);
	}

	public PreauthAuthentication(Object aPrincipal, Object aCredentials,
			GrantedAuthority[] anAuthorities) {
		super(aPrincipal, aCredentials, anAuthorities);
	}

}
