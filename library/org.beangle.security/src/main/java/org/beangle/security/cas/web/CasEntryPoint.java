/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.cas.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.beangle.bean.Initializing;
import org.beangle.security.cas.CasConfig;
import org.beangle.security.core.AuthenticationException;
import org.beangle.security.core.userdetail.UsernameNotFoundException;
import org.beangle.security.web.AuthenticationEntryPoint;

/**
 * Used by the <code>ExceptionTranslationFilter</code> to commence
 * authentication via the JA-SIG Central Authentication Service (CAS).
 * <p>
 * The user's browser will be redirected to the JA-SIG CAS enterprise-wide login page. This page is
 * specified by the <code>loginUrl</code> property. Once login is complete, the CAS login page will
 * redirect to the page indicated by the <code>service</code> property. The <code>service</code> is
 * a HTTP URL belonging to the current application. The <code>service</code> URL is monitored by the
 * {@link CasPreauthFilter}, which will validate the CAS login was successful.
 * 
 * @author chaostone
 * @version $Id: CasProcessingFilterEntryPoint.java $
 */
public class CasEntryPoint implements AuthenticationEntryPoint, Initializing {

	private CasConfig config;

	public CasEntryPoint() {
		super();
	}

	public CasEntryPoint(CasConfig config) {
		super();
		this.config = config;
	}

	public void init() throws Exception {
		Validate.notNull(this.config, "cas config must be specified");
	}

	public void commence(final ServletRequest servletRequest, final ServletResponse servletResponse,
			final AuthenticationException ae) throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) servletRequest;
		final HttpServletResponse response = (HttpServletResponse) servletResponse;
		if (null != ae && (ae instanceof UsernameNotFoundException)) {
			response.getWriter().append(String.valueOf(ae.getAuthentication().getPrincipal()))
					.append(ae.getMessage());
		} else {
			final String encodedServiceUrl = constructServiceUrl(request, response, null,
					CasConfig.getLocalServer(request), config.getArtifactName(), config.isEncode());
			final String redirectUrl = constructRedirectUrl(config.getLoginUrl(), "service",
					encodedServiceUrl, config.isRenew(), false);
			response.sendRedirect(redirectUrl);
		}
	}

	/**
	 * Constructs a service url from the HttpServletRequest or from the given
	 * serviceUrl. Prefers the serviceUrl provided if both a serviceUrl and a
	 * serviceName.
	 * 
	 * @param request
	 *        the HttpServletRequest
	 * @param response
	 *        the HttpServletResponse
	 * @param service
	 *        the configured service url (this will be used if not null)
	 * @param serverName
	 *        the server name to use to constuct the service url if the service param is empty
	 * @param artifactParameterName
	 *        the artifact parameter name to remove (i.e. ticket)
	 * @param encode
	 *        whether to encode the url or not (i.e. Jsession).
	 * @return the service url to use.
	 */
	public static String constructServiceUrl(final HttpServletRequest request,
			final HttpServletResponse response, final String service, final String serverName,
			final String artifactParameterName, final boolean encode) {
		if (StringUtils.isNotBlank(service)) { return encode ? response.encodeURL(service) : service; }

		final StringBuilder buffer = new StringBuilder();

		if (!serverName.startsWith("https://") && !serverName.startsWith("http://")) {
			buffer.append(request.isSecure() ? "https://" : "http://");
		}

		buffer.append(serverName);
		buffer.append(request.getRequestURI());

		if (StringUtils.isNotBlank(request.getQueryString())) {
			final int location = request.getQueryString().indexOf(artifactParameterName + "=");

			if (location == 0) {
				final String returnValue = encode ? response.encodeURL(buffer.toString()) : buffer.toString();
				return returnValue;
			}

			buffer.append("?");

			if (location == -1) {
				buffer.append(request.getQueryString());
			} else if (location > 0) {
				final int actualLocation = request.getQueryString()
						.indexOf("&" + artifactParameterName + "=");

				if (actualLocation == -1) {
					buffer.append(request.getQueryString());
				} else if (actualLocation > 0) {
					buffer.append(request.getQueryString().substring(0, actualLocation));
				}
			}
		}

		final String returnValue = encode ? response.encodeURL(buffer.toString()) : buffer.toString();
		return returnValue;
	}

	/**
	 * Constructs the URL to use to redirect to the CAS server.
	 * 
	 * @param casServerLoginUrl the CAS Server login url.
	 * @param serviceParameterName the name of the parameter that defines the service.
	 * @param serviceUrl the actual service's url.
	 * @param renew whether we should send renew or not.
	 * @param gateway where we should send gateway or not.
	 * @return the fully constructed redirect url.
	 */
	public String constructRedirectUrl(final String casServerLoginUrl, final String serviceParameterName,
			final String serviceUrl, final boolean renew, final boolean gateway) {
		try {
			return casServerLoginUrl + (casServerLoginUrl.indexOf("?") != -1 ? "&" : "?")
					+ serviceParameterName + "=" + URLEncoder.encode(serviceUrl, "UTF-8")
					+ (renew ? "&renew=true" : "") + (gateway ? "&gateway=true" : "");
		} catch (final UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public CasConfig getConfig() {
		return this.config;
	}

	public void setConfig(CasConfig config) {
		this.config = config;
	}
}
