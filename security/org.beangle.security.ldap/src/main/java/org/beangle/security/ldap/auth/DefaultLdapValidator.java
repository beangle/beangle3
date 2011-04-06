/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.ldap.auth;

import java.security.NoSuchAlgorithmException;

import org.beangle.security.ldap.connect.LdapUserStore;

public class DefaultLdapValidator implements LdapValidator {

	private LdapUserStore userStore;

	public DefaultLdapValidator() {
		super();
	}

	public DefaultLdapValidator(LdapUserStore userStore) {
		super();
		this.userStore = userStore;
	}

	public boolean verifyPassword(String name, String password) {
		String ldapPwd = userStore.getPassword(name);
		try {
			return (null != ldapPwd) && (LdapPasswordHandler.getInstance().verify(ldapPwd, password));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public void setUserStore(LdapUserStore userStore) {
		this.userStore = userStore;
	}
}
