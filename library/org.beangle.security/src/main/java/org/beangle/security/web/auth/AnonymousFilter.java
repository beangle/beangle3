/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.beangle.security.auth.AnonymousAuthentication;
import org.beangle.security.auth.AuthenticationDetailsSource;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.context.SecurityContextHolder;
import org.beangle.web.filter.GenericHttpFilterBean;

/**
 * Detects if there is no <code>Authentication</code> object in the
 * <code>SecurityContextHolder</code>, and populates it with one if needed.
 */
public class AnonymousFilter extends GenericHttpFilterBean {

	private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
	private String key;
	private boolean removeAfterRequest = true;

	/**
	 * Enables subclasses to determine whether or not an anonymous
	 * authentication token should be setup for this request. This is useful if
	 * anonymous authentication should be allowed only for specific IP subnet
	 * ranges etc.
	 * 
	 * @param request
	 *            to assist the method determine request details
	 * @return <code>true</code> if the anonymous token should be setup for this
	 *         request (provided that the request doesn't already have some
	 *         other <code>Authentication</code> inside it), or <code>false</code> if no anonymous
	 *         token should be setup for this
	 *         request
	 */
	protected boolean applyAnonymousForThisRequest(HttpServletRequest request) {
		return true;
	}

	protected Authentication createAuthentication(HttpServletRequest request) {
		AnonymousAuthentication auth = new AnonymousAuthentication(key, null);
		auth.setDetails(authenticationDetailsSource.buildDetails((HttpServletRequest) request));
		return auth;
	}

	protected void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		boolean addedToken = false;

		if (applyAnonymousForThisRequest(request)) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth == null) {
				auth = createAuthentication(request);
				SecurityContextHolder.getContext().setAuthentication(auth);
				addedToken = true;
				logger.debug("Populated SecurityContextHolder with anonymous token: '{}'", auth);
			} else {
				logger.debug(
						"SecurityContextHolder not populated with anonymous token, already contained: '{}'",
						auth);
			}
		}
		try {
			chain.doFilter(request, response);
		} finally {
			if (addedToken
					&& removeAfterRequest
					&& createAuthentication(request).equals(
							SecurityContextHolder.getContext().getAuthentication())) {
				SecurityContextHolder.getContext().setAuthentication(null);
			}
		}
	}

	public String getKey() {
		return key;
	}

	public boolean isRemoveAfterRequest() {
		return removeAfterRequest;
	}

	public void setAuthenticationDetailsSource(
			AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
		Validate.notNull(authenticationDetailsSource, "AuthenticationDetailsSource required");
		this.authenticationDetailsSource = authenticationDetailsSource;
	}

	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Controls whether the filter will remove the Anonymous token after the
	 * request is complete. Generally this is desired to avoid the expense of a
	 * session being created by
	 * {@link org.beangle.security.web.context.HttpSessionContextIntegrationFilter
	 * HttpSessionContextIntegrationFilter} simply to store the Anonymous
	 * authentication token.
	 * <p>
	 * Defaults to <code>true</code>, being the most optimal and appropriate option (ie
	 * <code>AnonymousProcessingFilter</code> will clear the token at the end of each request, thus
	 * avoiding the session creation overhead in a typical configuration.
	 * </p>
	 * 
	 * @param removeAfterRequest
	 *            DOCUMENT ME!
	 */
	public void setRemoveAfterRequest(boolean removeAfterRequest) {
		this.removeAfterRequest = removeAfterRequest;
	}
}
