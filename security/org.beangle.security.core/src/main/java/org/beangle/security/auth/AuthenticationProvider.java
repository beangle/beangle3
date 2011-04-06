/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.auth;

import org.beangle.security.core.Authentication;
import org.beangle.security.core.AuthenticationException;

/**
 * 被监视者服务
 * 
 * @author chaostone
 */
public interface AuthenticationProvider {

	public Authentication authenticate(Authentication auth) throws AuthenticationException;

	public boolean supports(Class<? extends Authentication> auth);
}
