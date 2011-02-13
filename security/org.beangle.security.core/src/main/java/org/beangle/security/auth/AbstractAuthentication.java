/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.auth;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.GrantedAuthority;
import org.beangle.security.core.userdetail.UserDetail;

public abstract class AbstractAuthentication implements Authentication {

	private static final long serialVersionUID = 3966615358056184985L;

	private Object details;
	private final Collection<GrantedAuthority> authorities;
	private boolean authenticated = false;

	public AbstractAuthentication(Collection<? extends GrantedAuthority> authorities) {
		if (authorities == null) {
			this.authorities = Collections.emptyList();
		} else {
			Validate.noNullElements(authorities, "authorities cannot contain any null element");
			this.authorities = Collections.unmodifiableCollection(CollectUtils.newArrayList(authorities));
		}
	}

	public Object getDetails() {
		return details;
	}

	public void setDetails(Object details) {
		this.details = details;
	}

	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
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
					.append(getCredentials(), test.getCredentials()).append(getDetails(), test.getDetails())
					.append(isAuthenticated(), test.isAuthenticated())
					.append(getAuthorities(), test.getAuthorities()).isEquals();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getPrincipal()).append(getCredentials()).append(getDetails())
				.append(isAuthenticated()).append(getAuthorities()).toHashCode();
	}

	public String getName() {
		if (this.getPrincipal() instanceof UserDetail) { return ((UserDetail) getPrincipal()).getUsername(); }

		if (getPrincipal() instanceof Principal) { return ((Principal) getPrincipal()).getName(); }

		return (this.getPrincipal() == null) ? "" : this.getPrincipal().toString();
	}
}
