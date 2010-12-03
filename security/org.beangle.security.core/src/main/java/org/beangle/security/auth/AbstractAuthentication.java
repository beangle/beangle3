/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.auth;

import java.security.Principal;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.GrantedAuthority;
import org.beangle.security.core.userdetail.UserDetail;

public abstract class AbstractAuthentication implements Authentication {
	private Object details;
	private GrantedAuthority[] authorities;
	private boolean authenticated = false;

	public AbstractAuthentication(GrantedAuthority[] authorities) {
		if (authorities != null) {
			for (int i = 0; i < authorities.length; i++) {
				Validate.notNull(authorities[i], "Granted authority element " + i
						+ " is null - GrantedAuthority[] cannot contain any null elements");
			}
		}
		this.authorities = authorities;
	}

	public Object getDetails() {
		return details;
	}

	public void setDetails(Object details) {
		this.details = details;
	}

	public GrantedAuthority[] getAuthorities() {
		if (authorities == null) { return null; }
		GrantedAuthority[] copy = new GrantedAuthority[authorities.length];
		System.arraycopy(authorities, 0, copy, 0, authorities.length);
		return copy;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	public boolean equals(Object obj) {
		if (obj instanceof AbstractAuthentication) {
			AbstractAuthentication test = (AbstractAuthentication) obj;
			return new EqualsBuilder().append(getPrincipal(), test.getPrincipal())
					.append(getCredentials(), test.getCredentials())
					.append(getDetails(), test.getDetails())
					.append(isAuthenticated(), test.isAuthenticated())
					.append(getAuthorities(), test.getAuthorities()).isEquals();
		}

		return false;
	}

	public String getName() {
		if (this.getPrincipal() instanceof UserDetail) { return ((UserDetail) getPrincipal())
				.getUsername(); }

		if (getPrincipal() instanceof Principal) { return ((Principal) getPrincipal()).getName(); }

		return (this.getPrincipal() == null) ? "" : this.getPrincipal().toString();
	}
}
