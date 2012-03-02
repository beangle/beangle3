/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.auth;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.Validate;
import org.beangle.security.auth.AuthenticationDetailsSource;
import org.beangle.security.auth.AuthenticationManager;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.AuthenticationException;
import org.beangle.security.core.context.SecurityContextHolder;
import org.beangle.security.core.session.SessionRegistry;
import org.beangle.web.filter.GenericHttpFilter;
import org.beangle.web.util.RedirectUtils;

/**
 * Abstract processor of browser-based HTTP-based authentication requests.
 * <p>
 * This filter is responsible for processing authentication requests. If authentication is
 * successful, the resulting {@link Authentication} object will be placed into the
 * <code>SecurityContext</code>, which is guaranteed to have already been created by an earlier
 * filter.
 * <p>
 * If authentication fails, the <code>AuthenticationException</code> will be placed into the
 * <code>HttpSession</code> with the attribute defined by {@link #SECURITY_LAST_EXCEPTION_KEY}.
 * <p>
 * To use this filter, it is necessary to specify the following properties:
 * <ul>
 * <li><code>defaultTargetUrl</code> indicates the URL that should be used for redirection if the
 * <code>HttpSession</code> attribute named {@link #SECURITY_SAVED_REQUEST_KEY} does not indicate
 * the target URL once authentication is completed successfully. eg: <code>/</code>. The
 * <code>defaultTargetUrl</code> will be treated as relative to the web-app's context path, and
 * should include the leading <code>/</code>. Alternatively, inclusion of a scheme name (eg http://
 * or https://) as the prefix will denote a fully-qualified URL and this is also supported. More
 * complex behaviour can be implemented by using a customised {@link TargetUrlResolver}.</li>
 * <li><code>authenticationFailureUrl</code> (optional) indicates the URL that should be used for
 * redirection if the authentication request fails. eg: <code>/login.jsp?login_error=1</code>. If
 * not configured, <tt>sendError</tt> will be called on the response, with the error code
 * SC_UNAUTHORIZED.</li>
 * <li><code>filterProcessesUrl</code> indicates the URL that this filter will respond to. This
 * parameter varies by subclass.</li>
 * <li><code>alwaysUseDefaultTargetUrl</code> causes successful authentication to always redirect to
 * the <code>defaultTargetUrl</code>, even if the <code>HttpSession</code> attribute named
 * {@link # SECURITY_SAVED_REQUEST_KEY} defines the intended target URL.</li>
 * </ul>
 * <p>
 * To configure this filter to redirect to specific pages as the result of specific
 * {@link AuthenticationException}s you can do the following. Configure the
 * <code>exceptionMappings</code> property in your application xml. This property is a
 * java.util.Properties object that maps a fully-qualified exception class name to a redirection url
 * target. For example:
 * 
 * <pre>
 *  &lt;property name=&quot;exceptionMappings&quot;&gt;
 *    &lt;props&gt;
 *      &lt;prop&gt; key=&quot;org.beangle.security.BadCredentialsException&quot;&gt;/bad_credentials.jsp&lt;/prop&gt;
 *    &lt;/props&gt;
 *  &lt;/property&gt;
 * </pre>
 * 
 * The example above would redirect all {@link org.beangle.security.auth.BadCredentialsException}s
 * thrown, to a page in the web-application called /bad_credentials.jsp.
 * <p>
 * Any {@link AuthenticationException} thrown that cannot be matched in the
 * <code>exceptionMappings</code> will be redirected to the <code>authenticationFailureUrl</code>
 * <p>
 * If authentication is successful, an
 * {@link org.beangle.security.event.authentication.InteractiveAuthenticationSuccessEvent} will be
 * published to the application context. No events will be published if authentication was
 * unsuccessful, because this would generally be recorded via an <code>AuthenticationManager</code>
 * -specific application event.
 * <p>
 * The filter has an optional attribute <tt>invalidateSessionOnSuccessfulAuthentication</tt> that
 * will invalidate the current session on successful authentication. This is to protect against
 * session fixation attacks (see <a href="http://en.wikipedia.org/wiki/Session_fixation">this
 * Wikipedia article</a> for more information). The behaviour is turned off by default. Additionally
 * there is a property <tt>migrateInvalidatedSessionAttributes</tt> which tells if on session
 * invalidation we are to migrate all session attributes from the old session to a newly created
 * one. This is turned on by default, but not used unless
 * <tt>invalidateSessionOnSuccessfulAuthentication</tt> is true. If you are using this feature in
 * combination with concurrent session control, you should set the <tt>sessionRegistry</tt> property
 * to make sure that the session information is updated consistently.
 * 
 * @author chaostone
 * @version $Id: AbstractProcessingFilter.java 3280 2008-09-12 14:57:21Z $
 */
public abstract class AbstractAuthenticationFilter extends GenericHttpFilter {

	public static final String SECURITY_SAVED_REQUEST_KEY = "BEANGLE_SECURITY_SAVED_REQUEST_KEY";

	public static final String SECURITY_LAST_EXCEPTION_KEY = "BEANGLE_SECURITY_LAST_EXCEPTION";

	protected AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

	private AuthenticationManager authenticationManager;

	private Properties exceptionMappings = new Properties();

	/** Where to redirect the browser to if authentication fails */
	private String authenticationFailureUrl;

	/**
	 * Where to redirect the browser to if authentication is successful but
	 * SECURITY_SAVED_REQUEST_KEY is <code>null</code>
	 */
	private String defaultTargetUrl;

	/**
	 * The URL destination that this filter intercepts and processes (usually
	 * something like <code>/j_security_check</code>)
	 */
	private String filterUrl = getDefaultFilterProcessesUrl();

	/**
	 * If <code>true</code>, will always redirect to the value of {@link #getDefaultTargetUrl} upon
	 * successful authentication, irrespective
	 * of the page that caused the authentication request (defaults to <code>false</code>).
	 */
	private boolean alwaysUseDefaultTargetUrl = false;

	/**
	 * Indicates if the filter chain should be continued prior to delegation to
	 * {@link #successfulAuthentication(HttpServletRequest, HttpServletResponse, Authentication)} ,
	 * which may be useful in certain environment (eg Tapestry). Defaults to <code>false</code>.
	 */
	private boolean continueChainBeforeSuccessfulAuthentication = false;

	private boolean allowSessionCreation = true;

	private boolean serverSideRedirect = false;

	private SessionRegistry sessionRegistry;

	protected void initFilterBean() throws ServletException {
		Validate.notEmpty(filterUrl, "filterUrl must be specified");
		Validate.isTrue(RedirectUtils.isValidRedirectUrl(filterUrl), filterUrl
				+ " isn't a valid redirect URL");
		Validate.notEmpty(defaultTargetUrl, "defaultTargetUrl must be specified");
		Validate.isTrue(RedirectUtils.isValidRedirectUrl(defaultTargetUrl), defaultTargetUrl
				+ " isn't a valid redirect URL");
		Validate.isTrue(RedirectUtils.isValidRedirectUrl(authenticationFailureUrl), authenticationFailureUrl
				+ " isn't a valid redirect URL");
		Validate.notNull(authenticationManager, "authenticationManager must be specified");
	}

	/**
	 * Performs actual authentication.
	 * 
	 * @param request
	 *        from which to extract parameters and perform the
	 *        authentication
	 * @return the authenticated user
	 * @throws AuthenticationException
	 *         if authentication fails
	 */
	public abstract Authentication attemptAuthentication(HttpServletRequest request)
			throws AuthenticationException;

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		if (requiresAuthentication(request, response)) {
			logger.debug("Request is to process authentication");
			Authentication authResult;
			try {
				authResult = attemptAuthentication(request);
				if (null == authResult) { return; }
				sessionRegistry.register(authResult, request.getSession().getId());
			} catch (AuthenticationException failed) {
				// Authentication failed
				unsuccessfulAuthentication(request, response, failed);
				return;
			}
			// Authentication success
			if (continueChainBeforeSuccessfulAuthentication) {
				chain.doFilter(request, response);
			}
			successfulAuthentication(request, response, authResult);
			return;
		}

		chain.doFilter(request, response);
	}

	protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			Authentication authResult) throws IOException {
	}

	protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException {
	}

	/**
	 * <p>
	 * Indicates whether this filter should attempt to process a login request for the current
	 * invocation.
	 * </p>
	 * <p>
	 * It strips any parameters from the "path" section of the request URL (such as the jsessionid
	 * parameter in <em>http://host/myapp/index.html;jsessionid=blah</em>) before matching against
	 * the <code>filterProcessesUrl</code> property.
	 * </p>
	 * <p>
	 * Subclasses may override for special requirements, such as Tapestry integration.
	 * </p>
	 * 
	 * @param request
	 *        as received from the filter chain
	 * @param response
	 *        as received from the filter chain
	 * @return <code>true</code> if the filter should attempt authentication, <code>false</code>
	 *         otherwise
	 */
	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		String uri = request.getRequestURI();
		int pathParamIndex = uri.indexOf(';');

		if (pathParamIndex > 0) {
			// strip everything after the first semi-colon
			uri = uri.substring(0, pathParamIndex);
		}

		if ("".equals(request.getContextPath())) { return uri.endsWith(filterUrl); }

		return uri.endsWith(request.getContextPath() + filterUrl);
	}

	protected void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url)
			throws IOException {
		RedirectUtils.sendRedirect(request, response, url);
	}

	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			Authentication authResult) throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(authResult);
		logger.debug("Authentication success.Updated SecurityContextHolder to contain: '{}'", authResult);
		String targetUrl = determineTargetUrl(request);
		logger.debug("Redirecting to target URL from HTTP Session (or default): {}", targetUrl);
		onSuccessfulAuthentication(request, response, authResult);
		sendRedirect(request, response, targetUrl);
	}

	protected String determineTargetUrl(HttpServletRequest request) {
		// Don't attempt to obtain the url from the saved request if
		// alwaysUsedefaultTargetUrl is set
		String targetUrl = alwaysUseDefaultTargetUrl ? null : request.getParameter("redirectTo");
		if (targetUrl == null) {
			targetUrl = getDefaultTargetUrl();
		}

		return targetUrl;
	}

	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(null);
		logger.debug("Updated SecurityContextHolder to contain null Authentication");
		String failureUrl = determineFailureUrl(request, failed);
		logger.debug("Authentication request failed: {}", failed);
		try {
			HttpSession session = request.getSession(false);
			if (session != null || allowSessionCreation) {
				request.getSession().setAttribute(SECURITY_LAST_EXCEPTION_KEY, failed);
			}
		} catch (Exception ignored) {
		}

		onUnsuccessfulAuthentication(request, response, failed);
		if (failureUrl == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"Authentication Failed:" + failed.getMessage());
		} else if (serverSideRedirect) {
			request.getRequestDispatcher(failureUrl).forward(request, response);
		} else {
			sendRedirect(request, response, failureUrl);
		}
	}

	protected String determineFailureUrl(HttpServletRequest request, AuthenticationException failed) {
		return exceptionMappings.getProperty(failed.getClass().getName(), authenticationFailureUrl);
	}

	public String getAuthenticationFailureUrl() {
		return authenticationFailureUrl;
	}

	public void setAuthenticationFailureUrl(String authenticationFailureUrl) {
		this.authenticationFailureUrl = authenticationFailureUrl;
	}

	protected AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	/**
	 * Specifies the default <code>filterProcessesUrl</code> for the
	 * implementation.
	 * 
	 * @return the default <code>filterProcessesUrl</code>
	 */
	public abstract String getDefaultFilterProcessesUrl();

	/**
	 * Supplies the default target Url that will be used if no saved request is
	 * found or the <tt>alwaysUseDefaultTargetUrl</tt> propert is set to true.
	 * Override this method of you want to provide a customized default Url (for
	 * example if you want different Urls depending on the authorities of the
	 * user who has just logged in).
	 * 
	 * @return the defaultTargetUrl property
	 */
	public String getDefaultTargetUrl() {
		return defaultTargetUrl;
	}

	public void setDefaultTargetUrl(String defaultTargetUrl) {
		Validate.isTrue(defaultTargetUrl.startsWith("/") | defaultTargetUrl.startsWith("http"),
				"defaultTarget must start with '/' or with 'http(s)'");
		this.defaultTargetUrl = defaultTargetUrl;
	}

	Properties getExceptionMappings() {
		return new Properties(exceptionMappings);
	}

	public void setExceptionMappings(Properties exceptionMappings) {
		this.exceptionMappings = exceptionMappings;
	}

	public String getFilterUrl() {
		return filterUrl;
	}

	public void setFilterUrl(String filterProcessesUrl) {
		this.filterUrl = filterProcessesUrl;
	}

	boolean isAlwaysUseDefaultTargetUrl() {
		return alwaysUseDefaultTargetUrl;
	}

	public void setAlwaysUseDefaultTargetUrl(boolean alwaysUseDefaultTargetUrl) {
		this.alwaysUseDefaultTargetUrl = alwaysUseDefaultTargetUrl;
	}

	public void setContinueChainBeforeSuccessfulAuthentication(
			boolean continueChainBeforeSuccessfulAuthentication) {
		this.continueChainBeforeSuccessfulAuthentication = continueChainBeforeSuccessfulAuthentication;
	}

	public AuthenticationDetailsSource<HttpServletRequest, ?> getAuthenticationDetailsSource() {
		return authenticationDetailsSource;
	}

	public void setAuthenticationDetailsSource(
			AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
		this.authenticationDetailsSource = authenticationDetailsSource;
	}

	protected boolean getAllowSessionCreation() {
		return allowSessionCreation;
	}

	public void setAllowSessionCreation(boolean allowSessionCreation) {
		this.allowSessionCreation = allowSessionCreation;
	}

	/**
	 * Tells if we are to do a server side include of the error URL instead of a
	 * 302 redirect.
	 * 
	 * @param serverSideRedirect
	 */
	public void setServerSideRedirect(boolean serverSideRedirect) {
		this.serverSideRedirect = serverSideRedirect;
	}

	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}

}
