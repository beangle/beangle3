/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.security.core.Authentication;

/**
 * Login/Logout Service
 * 
 * @author chaostone
 * @since 3.0.0
 */
public interface AuthenticationService {

  Authentication login(HttpServletRequest request, Authentication auth);

  boolean logout(HttpServletRequest request, HttpServletResponse response);

}
