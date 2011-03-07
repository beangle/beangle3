/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.spring.bind;

import org.beangle.spring.testbean.UserLdapProvider;
import org.beangle.spring.testbean.UserProvider;

public class SomeAction {

	UserProvider userDaoProvider;

	private UserLdapProvider ldapProvider;

	public boolean hasDaoProvider() {
		return null != userDaoProvider;
	}

	public boolean hasLdapProvider() {
		return null != ldapProvider;
	}

	public void setLdapProvider(UserLdapProvider ldapProvider) {
		this.ldapProvider = ldapProvider;
	}

	public void setUserDaoProvider(UserProvider userDaoProvider) {
		this.userDaoProvider = userDaoProvider;
	}

	public UserProvider getUserDaoProvider() {
		return userDaoProvider;
	}

	public UserLdapProvider getLdapProvider() {
		return ldapProvider;
	}
	
}
