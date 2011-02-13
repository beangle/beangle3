/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.access;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.beangle.security.BeangleSecurityException;
import org.beangle.security.access.AccessDeniedException;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.AuthenticationException;
import org.beangle.security.core.context.AuthenticationUtils;
import org.beangle.security.core.context.SecurityContextHolder;
import org.beangle.security.web.AuthenticationEntryPoint;
import org.beangle.security.web.util.ThrowableAnalyzer;
import org.beangle.security.web.util.ThrowableCauseExtractor;
import org.beangle.web.filter.GenericHttpFilterBean;

public class ExceptionTranslationFilter extends GenericHttpFilterBean {

	private AccessDeniedHandler accessDeniedHandler;
	private AuthenticationEntryPoint authenticationEntryPoint;
	private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();
	private boolean createSessionAllowed = true;

	protected void initFilerBean() throws ServletException {
		Validate.notNull(authenticationEntryPoint, "authenticationEntryPoint must be specified");
		Validate.notNull(throwableAnalyzer, "throwableAnalyzer must be specified");
	}

	public void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} catch (IOException ex) {
			throw ex;
		} catch (Exception ex) {
			// Try to extract a BeangleSecurityException from the stacktrace
			Throwable[] causeChain = this.throwableAnalyzer.determineCauseChain(ex);
			BeangleSecurityException ase = (BeangleSecurityException) this.throwableAnalyzer
					.getFirstThrowableOfType(BeangleSecurityException.class, causeChain);

			if (ase != null) {
				handleException(request, response, chain, ase);
			} else {
				// Rethrow ServletExceptions and RuntimeExceptions as-is
				if (ex instanceof ServletException) {
					throw (ServletException) ex;
				} else if (ex instanceof RuntimeException) { throw (RuntimeException) ex; }

				// Wrap other Exceptions. These are not expected to happen
				throw new RuntimeException(ex);
			}
		}
	}

	private void handleException(ServletRequest request, ServletResponse response, FilterChain chain,
			BeangleSecurityException exception) throws IOException, ServletException {
		if (exception instanceof AuthenticationException) {
			logger.debug("Authentication exception occurred", exception);
			sendStartAuthentication(request, response, chain, (AuthenticationException) exception);
		} else if (exception instanceof AccessDeniedException) {
			AccessDeniedException ae = (AccessDeniedException) exception;
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (AuthenticationUtils.isValid(auth)) {
				logger.debug("{} access {} is denied", auth.getName(), ae.getResource());
				accessDeniedHandler.handle(request, response, (AccessDeniedException) exception);
			} else {
				logger.debug("anonymous access {} is denied", ae.getResource());
				sendStartAuthentication(request, response, chain,
						new AuthenticationException(ae.getMessage()));
			}
		}
	}

	/**
	 * If <code>true</code>, indicates that <code>SecurityEnforcementFilter</code> is permitted to
	 * store the target
	 * URL and exception information in the <code>HttpSession</code> (the
	 * default). In situations where you do not wish to unnecessarily create
	 * <code>HttpSession</code>s - because the user agent will know the failed
	 * URL, such as with BASIC or Digest authentication - you may wish to set
	 * this property to <code>false</code>. Remember to also set the
	 * {@link org.beangle.security.web.context.security.context.HttpSessionContextIntegrationFilter#allowSessionCreation}
	 * to <code>false</code> if you set this property to <code>false</code>.
	 * 
	 * @return <code>true</code> if the <code>HttpSession</code> will be used to
	 *         store information about the failed request, <code>false</code> if
	 *         the <code>HttpSession</code> will not be used
	 */
	public boolean isCreateSessionAllowed() {
		return createSessionAllowed;
	}

	protected void sendStartAuthentication(ServletRequest request, ServletResponse response,
			FilterChain chain, AuthenticationException reason) throws ServletException, IOException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		SecurityContextHolder.getContext().setAuthentication(null);
		authenticationEntryPoint.commence(httpRequest, response, reason);
	}

	public void setAccessDeniedHandler(AccessDeniedHandler accessDeniedHandler) {
		Validate.notNull(accessDeniedHandler, "AccessDeniedHandler required");
		this.accessDeniedHandler = accessDeniedHandler;
	}

	public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	public void setCreateSessionAllowed(boolean createSessionAllowed) {
		this.createSessionAllowed = createSessionAllowed;
	}

	public void setThrowableAnalyzer(ThrowableAnalyzer throwableAnalyzer) {
		this.throwableAnalyzer = throwableAnalyzer;
	}

	/**
	 * Default implementation of <code>ThrowableAnalyzer</code> which is capable
	 * of also unwrapping <code>ServletException</code>s.
	 */
	private static final class DefaultThrowableAnalyzer extends ThrowableAnalyzer {
		/**
		 * @see org.beangle.security.web.util.ThrowableAnalyzer#initExtractorMap()
		 */
		protected void initExtractorMap() {
			super.initExtractorMap();
			registerExtractor(ServletException.class, new ThrowableCauseExtractor() {
				public Throwable extractCause(Throwable throwable) {
					ThrowableAnalyzer.verifyThrowableHierarchy(throwable, ServletException.class);
					return ((ServletException) throwable).getRootCause();
				}
			});
		}

	}

}
