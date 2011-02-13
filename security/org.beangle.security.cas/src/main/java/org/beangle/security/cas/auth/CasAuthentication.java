/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.cas.auth;

import java.util.Collection;

import org.beangle.security.core.GrantedAuthority;
import org.beangle.security.core.userdetail.UserDetail;
import org.beangle.security.web.auth.preauth.PreauthAuthentication;
import org.jasig.cas.client.validation.Assertion;

/**
 * Represents a successful CAS <code>Authentication</code>.
 * 
 * @author chaostone
 */
public class CasAuthentication extends PreauthAuthentication {

	private static final long serialVersionUID = 1L;

	/**
	 * Used to identify a CAS request for a stateful user agent, such as a web
	 * browser.
	 */
	public static final String STATEFUL_ID = "_cas_stateful_";

	/**
	 * Used to identify a CAS request for a stateless user agent, such as a
	 * remoting protocol client (eg Hessian, Burlap, SOAP etc). Results in a
	 * more aggressive caching strategy being used, as the absence of a <code>HttpSession</code>
	 * will result in a new authentication attempt on
	 * every request.
	 */
	public static final String STATELESS_ID = "_cas_stateless_";

	private final int keyHash;
	private final Assertion assertion;
	private final String loginUrl;

	/**
	 * Constructor.
	 * 
	 * @param key
	 *            to identify if this object made by a given {@link CasAuthenticationProvider}
	 * @param principal
	 *            typically the UserDetails object (cannot be <code>null</code>)
	 * @param credentials
	 *            the service/proxy ticket ID from CAS (cannot be <code>null</code>)
	 * @param authorities
	 *            the authorities granted to the user (from the
	 *            {@link org.springframework.security.userdetails.UserDetailsService} ) (cannot be
	 *            <code>null</code>)
	 * @param userDetails
	 *            the user details (from the
	 *            {@link org.springframework.security.userdetails.UserDetailsService} ) (cannot be
	 *            <code>null</code>)
	 * @param assertion
	 *            the assertion returned from the CAS servers. It contains the
	 *            principal and how to obtain a proxy ticket for the user.
	 * @throws IllegalArgumentException
	 *             if a <code>null</code> was passed
	 */
	public CasAuthentication(final String key, final Object principal, final Object credentials,
			final Collection<? extends GrantedAuthority> authorities, final UserDetail userDetail,
			final Assertion assertion) {
		super(principal, credentials, authorities);
		if ((key == null) || ("".equals(key)) || (principal == null) || "".equals(principal)
				|| (credentials == null) || "".equals(credentials) || (authorities == null)
				|| (userDetail == null) || (assertion == null)) { throw new IllegalArgumentException(
				"Cannot pass null or empty values to constructor"); }
		this.keyHash = key.hashCode();
		this.assertion = assertion;
		this.loginUrl = null;
	}

	public CasAuthentication(final Object principal, final Object credentials, String loginUrl) {
		super(principal, credentials);
		this.loginUrl = loginUrl;
		this.assertion = null;
		this.keyHash = 0;
	}

	public boolean equals(final Object obj) {
		if (!super.equals(obj)) { return false; }
		if (obj instanceof CasAuthentication) {
			CasAuthentication test = (CasAuthentication) obj;
			if (!this.assertion.equals(test.getAssertion())) { return false; }
			if (this.getKeyHash() != test.getKeyHash()) { return false; }
			return true;
		}
		return false;
	}

	public int getKeyHash() {
		return this.keyHash;
	}

	public Assertion getAssertion() {
		return this.assertion;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString());
		sb.append(" Assertion: ").append(this.assertion);
		sb.append(" Credentials (Service/Proxy Ticket): ").append(getCredentials());
		return (sb.toString());
	}
}
