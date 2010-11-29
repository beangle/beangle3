/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.cas.auth;


/**
 * Implementation of @link {@link StatelessTicketCache} that has no backing
 * cache. Useful in instances where storing of tickets for stateless session
 * management is not required.
 * <p>
 * This is the default StatelessTicketCache of the @link
 * {@link CasAuthenticationProvider} to eliminate the unnecessary dependency on
 * EhCache that applications have even if they are not using the stateless
 * session management.
 * 
 * @author chaostone
 * @see CasAuthenticationProvider
 */
public final class NullTicketCache implements StatelessTicketCache {

	/**
	 * @return null since we are not storing any tickets.
	 */
	public CasAuthentication get(final String serviceTicket) {
		return null;
	}

	/**
	 * This is a no-op since we are not storing tickets.
	 */
	public void put(final CasAuthentication token) {
		// nothing to do
	}

	/**
	 * This is a no-op since we are not storing tickets.
	 */
	public void remove(final CasAuthentication token) {
		// nothing to do
	}

	/**
	 * This is a no-op since we are not storing tickets.
	 */
	public void remove(final String serviceTicket) {
		// nothing to do
	}
}
