/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.auth.preauth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;

/**
 * Flexible pre-authenticated filter which obtains username and other values
 * supplied in the request (in headers, or in cookies, or in
 * HttpServletRequest.getRemoteUser()), for use with SSO systems.
 * <p>
 * Has additional <tt>usernameSource</tt> property.
 * <p>
 * Will create Authentication object (and attach it to the SecurityContextHolder), if such object
 * does not exist yet.
 * <p>
 * As with most pre-authenticated scenarios, it is essential that the external authentication system
 * is set up correctly as this filter does no authentication whatsoever. All the protection is
 * assumed to be provided externally and if this filter is included inappropriately in a
 * configuration, it would be possible to assume the identity of a user merely by setting the
 * correct header name. This also means it should not be used in combination with other Spring
 * Security authentication mechanisms such as form login, as this would imply there was a means of
 * bypassing the external system which would be risky.
 * <p>
 */
public class UsernamePreauthFilter extends AbstractPreauthFilter {
	private UsernameSource usernameSource;

	protected void initFilterBean() {
		super.initFilterBean();
		Validate.notNull(usernameSource, "usernameSource must be set");
	}

	/**
	 * @param usernameSource
	 *            the usernameSource to set
	 */
	public void setUsernameSource(UsernameSource usernameSource) {
		Validate.notNull(usernameSource, "usernameSource must be specified");
		this.usernameSource = usernameSource;
	}

	@Override
	protected PreauthAuthentication getPreauthAuthentication(HttpServletRequest request,
			HttpServletResponse response) {
		String username = usernameSource.obtainUsername(request);
		if (null == username) return null;
		else {
			return new PreauthAuthentication(username);
		}
	}
}
