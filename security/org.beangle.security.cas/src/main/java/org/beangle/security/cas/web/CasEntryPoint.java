/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.cas.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.beangle.security.cas.CasConfig;
import org.beangle.security.core.AuthenticationException;
import org.beangle.security.web.AuthenticationEntryPoint;
import org.jasig.cas.client.util.CommonUtils;
import org.springframework.beans.factory.InitializingBean;

/**
 * Used by the <code>ExceptionTranslationFilter</code> to commence
 * authentication via the JA-SIG Central Authentication Service (CAS).
 * <p>
 * The user's browser will be redirected to the JA-SIG CAS enterprise-wide login
 * page. This page is specified by the <code>loginUrl</code> property. Once
 * login is complete, the CAS login page will redirect to the page indicated by
 * the <code>service</code> property. The <code>service</code> is a HTTP URL
 * belonging to the current application. The <code>service</code> URL is
 * monitored by the {@link CasPreauthFilter}, which will validate the CAS login
 * was successful.
 * 
 * @author chaostone
 * @version $Id: CasProcessingFilterEntryPoint.java $
 */
public class CasEntryPoint implements AuthenticationEntryPoint, InitializingBean {

	private CasConfig config;

	public void afterPropertiesSet() throws Exception {
		Validate.notNull(this.config, "serviceProperties must be specified");
	}

	public void commence(final ServletRequest servletRequest,
			final ServletResponse servletResponse,
			final AuthenticationException authenticationException) throws IOException,
			ServletException {
		final HttpServletRequest request = (HttpServletRequest) servletRequest;
		final HttpServletResponse response = (HttpServletResponse) servletResponse;
		final String urlEncodedService = CommonUtils.constructServiceUrl(request, response, null,
				config.getLocalServer(), "ticket", config.isEncode());
		final String redirectUrl = CommonUtils.constructRedirectUrl(config.getLoginUrl(), "service",
				urlEncodedService, config.isRenew(), false);
		response.sendRedirect(redirectUrl);
	}

	public CasConfig getConfig() {
		return this.config;
	}

	public void setConfig(CasConfig config) {
		this.config = config;
	}
}
