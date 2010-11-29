/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.rememberme;

import javax.servlet.http.HttpServletRequest;

import org.beangle.security.auth.RememberMeAuthentication;

public class TokenBasedRememberMeService implements RememberMeService {

	public RememberMeAuthentication autoLogin(HttpServletRequest httpRequest) {
		// String username = WebUtils.getCookieValue(httpRequest,
		// Authentication.NAME);
		// if (StringUtils.isNotEmpty(username)) {
		// String password = WebUtils.getCookieValue(httpRequest,
		// Authentication.PASSWORD);
		// if (StringUtils.isNotEmpty(password)) {
		// return new RememberMeAuthentication(username, password);
		// }
		// }
		return null;
	}
}
