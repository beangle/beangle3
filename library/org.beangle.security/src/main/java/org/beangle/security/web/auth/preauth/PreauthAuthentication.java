/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.auth.preauth;

import java.util.Collection;

import org.beangle.security.auth.AbstractAuthentication;
import org.beangle.security.core.GrantedAuthority;
/**
 * Preauth authentication token
 * Different from UsernamePasswordAuthentication,it cannot be authenticate by dao provider.
 * @author chaostone
 *
 */
public class PreauthAuthentication extends AbstractAuthentication {

	private static final long serialVersionUID = 7456187250318208972L;

	private final Object principal;
	private final Object credentials;

	public PreauthAuthentication(Object principal) {
		super(null);
		this.principal = principal;
		this.credentials = "NONE";
	}

	public PreauthAuthentication(Object aPrincipal, Object credentials) {
		super(null);
		this.principal = aPrincipal;
		this.credentials = credentials;
	}

	public PreauthAuthentication(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		super.setAuthenticated(true);
	}

	public Object getPrincipal() {
		return principal;
	}

	public Object getCredentials() {
		return credentials;
	}

	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		if (isAuthenticated) { throw new IllegalArgumentException(
				"Cannot set this token to trusted - use constructor containing GrantedAuthority[]s instead"); }
		super.setAuthenticated(false);
	}

}
