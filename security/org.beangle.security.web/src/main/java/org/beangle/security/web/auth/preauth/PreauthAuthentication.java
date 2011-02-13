/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.auth.preauth;

import java.util.Collection;

import org.beangle.security.auth.UsernamePasswordAuthentication;
import org.beangle.security.core.GrantedAuthority;

public class PreauthAuthentication extends UsernamePasswordAuthentication {

	private static final long serialVersionUID = 7456187250318208972L;

	public PreauthAuthentication(Object aPrincipal, Object aCredentials) {
		super(aPrincipal, aCredentials);
	}

	public PreauthAuthentication(Object aPrincipal, Object aCredentials,
			Collection<? extends GrantedAuthority> anAuthorities) {
		super(aPrincipal, aCredentials, anAuthorities);
	}

}
