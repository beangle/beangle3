/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.rememberme;

import javax.servlet.http.HttpServletRequest;

import org.beangle.security.auth.RememberMeAuthentication;

public interface RememberMeService {

	RememberMeAuthentication autoLogin(HttpServletRequest httpRequest);

}
