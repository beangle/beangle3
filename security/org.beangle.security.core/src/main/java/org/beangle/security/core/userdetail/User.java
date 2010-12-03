/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.userdetail;

import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang.Validate;
import org.beangle.security.core.GrantedAuthority;

public class User implements UserDetail {

	private static final long serialVersionUID = 1L;
	private String password;
	private String username;
	private GrantedAuthority[] authorities;
	private boolean accountExpired;
	private boolean accountLocked;
	private boolean credentialsExpired;
	private boolean enabled;

	/**
	 * Construct the <code>User</code> with the details required by
	 * {@link org.beangle.security.auth.dao.providers.dao.DaoAuthenticationProvider}
	 * .
	 * 
	 * @param username
	 *            the username presented to the
	 *            <code>DaoAuthenticationProvider</code>
	 * @param password
	 *            the password that should be presented to the
	 *            <code>DaoAuthenticationProvider</code>
	 * @param enabled
	 *            set to <code>true</code> if the user is enabled
	 * @param accountExpired
	 *            set to <code>true</code> if the account has not expired
	 * @param credentialsExpired
	 *            set to <code>true</code> if the credentials have not expired
	 * @param accountLocked
	 *            set to <code>true</code> if the account is not locked
	 * @param authorities
	 *            the authorities that should be granted to the caller if they
	 *            presented the correct username and password and the user is
	 *            enabled
	 * @throws IllegalArgumentException
	 *             if a <code>null</code> value was passed either as a parameter
	 *             or as an element in the <code>GrantedAuthority[]</code> array
	 */
	public User(String username, String password, boolean enabled, boolean accountExpired,
			boolean credentialsExpired, boolean accountLocked, GrantedAuthority[] authorities)
			throws IllegalArgumentException {
		if (((username == null) || "".equals(username)) || (password == null)) { throw new IllegalArgumentException(
				"Cannot pass null or empty values to constructor"); }

		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.accountExpired = accountExpired;
		this.credentialsExpired = credentialsExpired;
		this.accountLocked = accountLocked;
		setAuthorities(authorities);
	}

	public boolean equals(Object rhs) {
		if (!(rhs instanceof User) || (rhs == null)) { return false; }
		User user = (User) rhs;
		// We rely on constructor to guarantee any User has non-null and >0
		// authorities
		if (user.getAuthorities().length != this.getAuthorities().length) { return false; }

		for (int i = 0; i < this.getAuthorities().length; i++) {
			if (!this.getAuthorities()[i].equals(user.getAuthorities()[i])) { return false; }
		}

		// We rely on constructor to guarantee non-null username and password
		return (this.getPassword().equals(user.getPassword())
				&& this.getUsername().equals(user.getUsername())
				&& (this.isAccountExpired() == user.isAccountExpired())
				&& (this.isAccountLocked() == user.isAccountLocked())
				&& (this.isCredentialsExpired() == user.isCredentialsExpired()) && (this
				.isEnabled() == user.isEnabled()));
	}

	public GrantedAuthority[] getAuthorities() {
		return authorities;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public int hashCode() {
		int code = 9792;

		if (this.getAuthorities() != null) {
			for (int i = 0; i < this.getAuthorities().length; i++) {
				code = code * (this.getAuthorities()[i].hashCode() % 7);
			}
		}
		if (this.getPassword() != null) {
			code = code * (this.getPassword().hashCode() % 7);
		}

		if (this.getUsername() != null) {
			code = code * (this.getUsername().hashCode() % 7);
		}

		if (this.isAccountExpired()) {
			code = code * -2;
		}

		if (this.isAccountLocked()) {
			code = code * -3;
		}

		if (this.isCredentialsExpired()) {
			code = code * -5;
		}

		if (this.isEnabled()) {
			code = code * -7;
		}

		return code;
	}

	public boolean isAccountExpired() {
		return accountExpired;
	}

	public boolean isAccountLocked() {
		return this.accountLocked;
	}

	public boolean isCredentialsExpired() {
		return credentialsExpired;
	}

	public boolean isEnabled() {
		return enabled;
	}

	protected void setAuthorities(GrantedAuthority[] authorities) {
		Validate.notNull(authorities, "Cannot pass a null GrantedAuthority array");
		SortedSet<GrantedAuthority> sorter = new TreeSet<GrantedAuthority>();
		for (int i = 0; i < authorities.length; i++) {
			Validate.notNull(authorities[i], "Granted authority element " + i
					+ " is null - GrantedAuthority[] cannot contain any null elements");
			sorter.add(authorities[i]);
		}
		this.authorities = (GrantedAuthority[]) sorter.toArray(new GrantedAuthority[sorter.size()]);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString()).append(": ");
		sb.append("Username: ").append(this.username).append("; ");
		sb.append("Password: [PROTECTED]; ");
		sb.append("Enabled: ").append(this.enabled).append("; ");
		sb.append("AccountExpired: ").append(this.accountExpired).append("; ");
		sb.append("credentialsExpired: ").append(this.credentialsExpired).append("; ");
		sb.append("AccountLocked: ").append(this.accountLocked).append("; ");

		if (this.getAuthorities() != null) {
			sb.append("Granted Authorities: ");

			for (int i = 0; i < this.getAuthorities().length; i++) {
				if (i > 0) {
					sb.append(", ");
				}

				sb.append(this.getAuthorities()[i].toString());
			}
		} else {
			sb.append("Not granted any authorities");
		}

		return sb.toString();
	}
}
