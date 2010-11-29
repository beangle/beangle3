/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.auth;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.beangle.security.core.AuthenticationException;
import org.beangle.web.util.RedirectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * <p>
 * Used by the <code>SecurityEnforcementFilter</code> to commence authentication
 * via the {@link UsernamePasswordAuthenticationFilter}. This object holds the
 * location of the login form, relative to the web app context path, and is used
 * to commence a redirect to that form.
 * <p>
 * By setting the <em>forceHttps</em> property to true, you may configure the
 * class to force the protocol used for the login form to be <code>HTTPS</code>,
 * even if the original intercepted request for a resource used the
 * <code>HTTP</code> protocol. When this happens, after a successful login (via
 * HTTPS), the original resource will still be accessed as HTTP, via the
 * original request URL. For the forced HTTPS feature to work, the
 * {@link PortMapper} is consulted to determine the HTTP:HTTPS pairs.
 * 
 * @author chaostone
 */
public class LoginUrlEntryPoint implements UrlEntryPoint, InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(LoginUrlEntryPoint.class);

	private String loginUrl;

	private boolean serverSideRedirect = false;

	public void afterPropertiesSet() throws Exception {
		Validate.notEmpty(loginUrl, "loginFormUrl must be specified");
	}

	/**
	 * Allows subclasses to modify the login form URL that should be applicable
	 * for a given request.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param exception
	 *            the exception
	 * @return the URL (cannot be null or empty; defaults to
	 *         {@link #getLoginFormUrl()})
	 */
	protected String determineUrlToUseForThisRequest(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception) {
		return getLoginUrl();
	}

	/**
	 * Performs the redirect (or forward) to the login form URL.
	 */
	public void commence(ServletRequest request, ServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String redirectUrl = null;
		if (serverSideRedirect) {
			String loginForm = determineUrlToUseForThisRequest(httpRequest, httpResponse,
					authException);
			logger.debug("Server side forward to: {}", loginForm);
			RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(loginForm);
			dispatcher.forward(request, response);
			return;
		} else {
			// redirect to login page. Use https if forceHttps true
			redirectUrl = determineUrlToUseForThisRequest(httpRequest, httpResponse, authException);
		}
		RedirectUtils.sendRedirect(httpRequest, httpResponse, redirectUrl);
	}

	/**
	 * The URL where the <code>AuthenticationProcessingFilter</code> login page
	 * can be found. Should be relative to the web-app context path, and include
	 * a leading <code>/</code>
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	/**
	 * Tells if we are to do a server side include of the
	 * <code>loginFormUrl</code> instead of a 302 redirect.
	 * 
	 * @param serverSideRedirect
	 */
	public void setServerSideRedirect(boolean serverSideRedirect) {
		this.serverSideRedirect = serverSideRedirect;
	}

	protected boolean isServerSideRedirect() {
		return serverSideRedirect;
	}
}
