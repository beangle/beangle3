/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.auth;

import java.util.Collection;

import org.beangle.security.core.GrantedAuthority;

public class UsernamePasswordAuthentication extends AbstractAuthentication {

	private static final long serialVersionUID = 1L;

	private final Object principal;
	private final Object credentials;

	public UsernamePasswordAuthentication(Object principal, Object credentials) {
		super(null);
		this.principal = principal;
		this.credentials = credentials;
	}

	public UsernamePasswordAuthentication(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		super.setAuthenticated(true);
	}

	public Object getCredentials() {
		return credentials;
	}

	public Object getPrincipal() {
		return principal;
	}

	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		if (isAuthenticated) { throw new IllegalArgumentException(
				"Cannot set this token to trusted - use constructor containing GrantedAuthority[]s instead"); }
		super.setAuthenticated(false);
	}
}
