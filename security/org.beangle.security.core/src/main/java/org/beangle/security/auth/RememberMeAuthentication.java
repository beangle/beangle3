/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.auth;

import java.util.Collection;

import org.beangle.security.core.GrantedAuthority;

public class RememberMeAuthentication extends AbstractAuthentication {
	private static final long serialVersionUID = 1L;
	private Object principal;
	private int keyHash;

	public RememberMeAuthentication(String key, Object principal,
			Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		if ((key == null) || ("".equals(key)) || (principal == null) || "".equals(principal)) { throw new IllegalArgumentException(
				"Cannot pass null or empty values to constructor"); }

		this.keyHash = key.hashCode();
		this.principal = principal;
		setAuthenticated(true);
	}

	public boolean equals(Object obj) {
		if (!super.equals(obj)) { return false; }

		if (obj instanceof RememberMeAuthentication) {
			RememberMeAuthentication test = (RememberMeAuthentication) obj;

			if (this.getKeyHash() != test.getKeyHash()) { return false; }

			return true;
		}

		return false;
	}

	public Object getCredentials() {
		return "";
	}

	public int getKeyHash() {
		return this.keyHash;
	}

	public Object getPrincipal() {
		return this.principal;
	}
}
