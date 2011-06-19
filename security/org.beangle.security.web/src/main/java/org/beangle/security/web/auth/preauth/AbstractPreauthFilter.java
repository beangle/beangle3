/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.auth.preauth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.beangle.security.auth.AuthenticationDetailsSource;
import org.beangle.security.auth.AuthenticationManager;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.AuthenticationException;
import org.beangle.security.core.context.SecurityContextHolder;
import org.beangle.security.core.session.SessionRegistry;
import org.beangle.security.core.userdetail.UsernameNotFoundException;
import org.beangle.security.web.auth.AbstractAuthenticationFilter;
import org.beangle.security.web.auth.WebAuthenticationDetailsSource;
import org.beangle.web.filter.GenericHttpFilterBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Base class for processing filters that handle pre-authenticated
 * authentication requests. Subclasses must implement the
 * getPreAuthenticatedPrincipal() and getPreAuthenticatedCredentials() methods.
 * <p>
 * By default, the filter chain will proceed when an authentication attempt fails in order to allow
 * other authentication mechanisms to process the request. To reject the credentials immediately,
 * set the <tt>continueFilterChainOnUnsuccessfulAuthentication</tt> flag to false. The exception
 * raised by the <tt>AuthenticationManager</tt> will the be re-thrown. Note that this will not
 * affect cases where the principal returned by {@link #getPreauthAuthentication} is null, when the
 * chain will still proceed as normal.
 */
public abstract class AbstractPreauthFilter extends GenericHttpFilterBean implements InitializingBean {

	private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

	private AuthenticationManager authenticationManager = null;

	private SessionRegistry sessionRegistry;

	private AuthenticationAliveChecker authenticationAliveChecker;

	/**
	 * fail for invalid cookie/ticket etc.
	 */
	private boolean continueOnFail = true;

	/**
	 * Check whether all required properties have been set.
	 */
	protected void initFilterBean() {
		Validate.notNull(authenticationManager, "authenticationManager must be set");
		Validate.notNull(sessionRegistry, "sessionRegistry must be set");
	}

	/**
	 * 是否应该尝试进行认证
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) { return true; }
		return (null != authenticationAliveChecker) ? !authenticationAliveChecker.check(auth, request)
				: false;
	}

	/**
	 * Try to authenticate a pre-authenticated user with Beangle Security if the
	 * user has not yet been authenticated.
	 */
	public void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		if (requiresAuthentication(request, response)) {
			doAuthenticate(request, response);
		}
		filterChain.doFilter(request, response);
	}

	/**
	 * Do the actual authentication for a pre-authenticated user.
	 */
	private void doAuthenticate(HttpServletRequest request, HttpServletResponse response) {
		Authentication authResult = null;
		PreauthAuthentication auth = getPreauthAuthentication(request, response);
		if (auth == null) {
			logger.debug("No pre-authenticated principal found in request");
			return;
		} else {
			logger.debug("trying to authenticate preauth={}", auth);
		}
		try {
			auth.setDetails(authenticationDetailsSource.buildDetails(request));
			authResult = authenticationManager.authenticate(auth);
			sessionRegistry.register(authResult, request.getSession().getId());
			successfulAuthentication(request, response, authResult);
		} catch (AuthenticationException failed) {
			unsuccessfulAuthentication(request, response, failed);
			if (!continueOnFail) { throw failed; }
		}
	}

	/**
	 * Puts the <code>Authentication</code> instance returned by the
	 * authentication manager into the secure context.
	 */
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			Authentication authResult) {
		logger.debug("PreAuthentication success: {}", authResult);
		SecurityContextHolder.getContext().setAuthentication(authResult);
	}

	/**
	 * Ensures the authentication object in the secure context is set to null
	 * when authentication fails.
	 */
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) {
		SecurityContextHolder.clearContext();
		logger.debug("Cleared security context due to exception", failed);
		request.getSession().setAttribute(AbstractAuthenticationFilter.SECURITY_LAST_EXCEPTION_KEY, failed);
		if(failed instanceof UsernameNotFoundException){
			throw failed;
		}
	}

	/**
	 * @param userDetailsSource
	 *            The UserDetailsSource to use
	 */
	public void setAuthenticationDetailsSource(
			AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
		Validate.notNull(authenticationDetailsSource, "AuthenticationDetailsSource required");
		this.authenticationDetailsSource = authenticationDetailsSource;
	}

	/**
	 * @param authenticationManager
	 *            The AuthenticationManager to use
	 */
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}

	public void setContinueOnFail(boolean continueOnFail) {
		this.continueOnFail = continueOnFail;
	}

	public void setAuthenticationAliveChecker(AuthenticationAliveChecker authenticationAliveChecker) {
		this.authenticationAliveChecker = authenticationAliveChecker;
	}

	/**
	 * Override to extract the principal information from the current request
	 */
	protected abstract PreauthAuthentication getPreauthAuthentication(HttpServletRequest request,
			HttpServletResponse response);
}
