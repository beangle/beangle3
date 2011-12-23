/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.auth.logout;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.context.SecurityContextHolder;
import org.beangle.web.filter.GenericHttpFilterBean;
import org.beangle.web.util.RedirectUtils;
import org.springframework.util.StringUtils;

/**
 * Logs a principal out.
 * <p>
 * Polls a series of {@link LogoutHandler}s. The handlers should be specified in the order they are
 * required. Generally you will want to call logout handlers
 * <code>TokenBasedRememberMeServices</code> and <code>SecurityContextLogoutHandler</code> (in that
 * order).
 * </p>
 * <p>
 * After logout, the URL specified by {@link #logoutSuccessUrl} will be shown.
 * </p>
 */
public class LogoutFilter extends GenericHttpFilterBean {
	private String filterProcessesUrl = "/logout";
	private String logoutSuccessUrl;
	private LogoutHandlerStack handlerStack;

	public LogoutFilter(String logoutSuccessUrl, LogoutHandlerStack stack) {
		Validate.notNull(handlerStack, "LogoutHandlerStack are required");
		this.handlerStack = stack;
		this.logoutSuccessUrl = logoutSuccessUrl;
		Validate.isTrue(RedirectUtils.isValidRedirectUrl(logoutSuccessUrl), logoutSuccessUrl
				+ " isn't a valid redirect URL");
	}

	@Override
	protected void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (requiresLogout(request, response)) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			logger.debug("Logging out user '{}' and redirecting to logout page", auth);
			handlerStack.logout(request, response, auth);
			String targetUrl = determineTargetUrl(request, response);
			RedirectUtils.sendRedirect(request, response, targetUrl);
			return;
		}
		chain.doFilter(request, response);
	}

	/**
	 * Allow subclasses to modify when a logout should take place.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return <code>true</code> if logout should occur, <code>false</code> otherwise
	 */
	protected boolean requiresLogout(HttpServletRequest request, HttpServletResponse response) {
		String uri = request.getRequestURI();
		int pathParamIndex = uri.indexOf(';');

		if (pathParamIndex > 0) {
			// strip everything from the first semi-colon
			uri = uri.substring(0, pathParamIndex);
		}

		int queryParamIndex = uri.indexOf('?');

		if (queryParamIndex > 0) {
			// strip everything from the first question mark
			uri = uri.substring(0, queryParamIndex);
		}

		if ("".equals(request.getContextPath())) { return uri.endsWith(filterProcessesUrl); }

		return uri.endsWith(request.getContextPath() + filterProcessesUrl);
	}

	/**
	 * Returns the target URL to redirect to after logout.
	 * <p>
	 * By default it will check for a <tt>logoutSuccessUrl</tt> parameter in the request and use
	 * this. If that isn't present it will use the configured <tt>logoutSuccessUrl</tt>. If this
	 * hasn't been set it will check the Referer header and use the URL from there.
	 */
	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
		String targetUrl = request.getParameter("logoutSuccessUrl");

		if (!StringUtils.hasLength(targetUrl)) {
			targetUrl = getLogoutSuccessUrl();
		}

		if (!StringUtils.hasLength(targetUrl)) {
			targetUrl = request.getHeader("Referer");
		}

		if (!StringUtils.hasLength(targetUrl)) {
			targetUrl = "/";
		}

		return targetUrl;
	}

	public void setFilterProcessesUrl(String filterProcessesUrl) {
		Validate.notEmpty(filterProcessesUrl, "FilterProcessesUrl required");
		Validate.isTrue(RedirectUtils.isValidRedirectUrl(filterProcessesUrl), filterProcessesUrl
				+ " isn't a valid redirect URL");
		this.filterProcessesUrl = filterProcessesUrl;
	}

	protected String getLogoutSuccessUrl() {
		return logoutSuccessUrl;
	}

	protected String getFilterProcessesUrl() {
		return filterProcessesUrl;
	}
}
