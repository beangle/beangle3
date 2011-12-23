/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.auth.preauth;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.Validate;
import org.beangle.security.core.Authentication;

/**
 * @author chaostone
 * @version $Id: UsernameAliveChecker.java Nov 7, 2010 9:48:50 PM chaostone $
 */
public class UsernameAliveChecker implements AuthenticationAliveChecker {
	private UsernameSource usernameSource;

	/**
	 * @param usernameSource
	 *            the usernameSource to set
	 */
	public void setUsernameSource(UsernameSource usernameSource) {
		Validate.notNull(usernameSource, "usernameSource must be specified");
		this.usernameSource = usernameSource;
	}

	public boolean check(Authentication auth, HttpServletRequest request) {
		String newUsername = usernameSource.obtainUsername(request);
		return ObjectUtils.equals(newUsername, auth.getName());
	}

}
