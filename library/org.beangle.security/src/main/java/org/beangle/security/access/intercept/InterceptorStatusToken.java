/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.access.intercept;

import org.beangle.security.core.Authentication;

/**
 * A return object received by {@link AbstractSecurityInterceptor} subclasses.
 * <p>
 * This class reflects the status of the security interception, so that the final call to
 * {@link org.beangle.security.access.intercept.AbstractSecurityInterceptor#afterInvocation(InterceptorStatusToken, Object)}
 * can tidy up correctly.
 */
public class InterceptorStatusToken {
	private Authentication authentication;
	private Object secureObject;
	private boolean contextHolderRefreshRequired;

	public InterceptorStatusToken(Authentication authentication, boolean contextHolderRefreshRequired,
			Object secureObject) {
		this.authentication = authentication;
		this.contextHolderRefreshRequired = contextHolderRefreshRequired;
		this.secureObject = secureObject;
	}

	public Authentication getAuthentication() {
		return authentication;
	}

	public Object getSecureObject() {
		return secureObject;
	}

	public boolean isContextHolderRefreshRequired() {
		return contextHolderRefreshRequired;
	}
}
