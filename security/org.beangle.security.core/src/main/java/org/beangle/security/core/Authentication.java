/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core;

import java.io.Serializable;
import java.security.Principal;


public interface Authentication extends Principal, Serializable {

	// public static String NAME = "name";
	//
	// public static String PASSWORD = "password";
	//
	// public static String USERID = "security.userId";
	//
	// public static String LOGINNAME = "security.loginName";
	//
	// public static String FULLNAME = "security.fullName";
	//
	// public static String USER_CATEGORYID = "security.categoryId";
	//
	// public static String ERROR_PASSWORD = "error.wrongPassword";
	//
	// public static String ERROR_NOTEXIST = "error.userNotExist";
	//
	// public static String ERROR_UNACTIVE = "error.userUnactive";
	//
	// public static String ERROR_OVERMAX = "error.overmax";

	public Object getPrincipal();

	public Object getCredentials();

	public GrantedAuthority[] getAuthorities();

	public Object getDetails();

	public boolean isAuthenticated();

	public void setAuthenticated(boolean authenticated);

}
