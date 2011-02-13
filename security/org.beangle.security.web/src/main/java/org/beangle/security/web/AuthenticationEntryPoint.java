/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.beangle.security.core.AuthenticationException;

public interface AuthenticationEntryPoint {

	/**
	 * Commences an authentication scheme.
	 * <p>
	 * <code>ExceptionTranslationFilter</code> will populate the <code>HttpSession</code> attribute
	 * named <code>AbstractProcessingFilter.SECURITY_SAVED_REQUEST_KEY</code> with the requested
	 * target URL before calling this method.
	 * </p>
	 * <p>
	 * Implementations should modify the headers on the <code>ServletResponse</code> as necessary to
	 * commence the authentication process.
	 * </p>
	 * 
	 * @param request
	 *            that resulted in an <code>AuthenticationException</code>
	 * @param response
	 *            so that the user agent can begin authentication
	 * @param authException
	 *            that caused the invocation
	 */
	void commence(ServletRequest request, ServletResponse response, AuthenticationException authException)
			throws IOException, ServletException;
}
