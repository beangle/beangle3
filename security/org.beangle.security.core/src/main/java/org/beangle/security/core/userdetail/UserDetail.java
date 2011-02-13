/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.userdetail;

import java.io.Serializable;
import java.util.Collection;

import org.beangle.security.core.GrantedAuthority;

/**
 * Provides core user information.
 */
public interface UserDetail extends Serializable {
	/**
	 * Returns the username used to authenticate the user. Cannot return <code>null</code>.
	 * 
	 * @return the username (never <code>null</code>)
	 */
	String getUsername();

	/**
	 * Returns the password used to authenticate the user. Cannot return <code>null</code>.
	 * 
	 * @return the password (never <code>null</code>)
	 */
	String getPassword();

	/**
	 * Returns the authorities granted to the user. Cannot return <code>null</code>.
	 * 
	 * @return the authorities, sorted by natural key (never <code>null</code>)
	 */
	Collection<? extends GrantedAuthority> getAuthorities();

	/**
	 * Indicates whether the user's account has expired. An expired account
	 * cannot be authenticated.
	 * 
	 * @return <code>true</code> if the user's account is valid (ie
	 *         non-expired), <code>false</code> if no longer valid (ie expired)
	 */
	boolean isAccountExpired();

	/**
	 * Indicates whether the user is locked or unlocked. A locked user cannot be
	 * authenticated.
	 * 
	 * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
	 */
	boolean isAccountLocked();

	/**
	 * Indicates whether the user's credentials (password) has expired. Expired
	 * credentials prevent authentication.
	 * 
	 * @return <code>true</code> if the user's credentials are valid (ie
	 *         non-expired), <code>false</code> if no longer valid (ie expired)
	 */
	boolean isCredentialsExpired();

	/**
	 * Indicates whether the user is enabled or disabled. A disabled user cannot
	 * be authenticated.
	 * 
	 * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
	 */
	boolean isEnabled();
}
