/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.auth;

import org.beangle.security.core.Authentication;
import org.beangle.security.core.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractAuthenticationManager implements AuthenticationManager {

	private boolean clearExtraInfo = false;
	protected Logger logger = LoggerFactory.getLogger(AbstractAuthenticationManager.class);

	public final Authentication authenticate(Authentication authRequest) throws AuthenticationException {
		try {
			Authentication auth = doAuthentication(authRequest);
			logger.debug("Successfully Authenticated: {}", auth);
			return auth;
		} catch (AuthenticationException e) {
			e.setAuthentication(authRequest);
			if (clearExtraInfo) {
				e.clearExtraInfo();
			}
			throw e;
		}
	}

	protected abstract Authentication doAuthentication(Authentication authentication)
			throws AuthenticationException;

	public void setClearExtraInfo(boolean clearExtraInfo) {
		this.clearExtraInfo = clearExtraInfo;
	}
}
