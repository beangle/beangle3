/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.auth.preauth;

import javax.servlet.http.HttpServletRequest;

import org.beangle.security.core.Authentication;

/**
 * @author chaostone
 * @version $Id: AuthenticationAliveChecker.java Nov 7, 2010 9:44:28 PM
 *          chaostone $
 */
public interface AuthenticationAliveChecker {

	public boolean check(Authentication auth, HttpServletRequest request);
}
