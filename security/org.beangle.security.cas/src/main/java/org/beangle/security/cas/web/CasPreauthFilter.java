/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.cas.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.beangle.security.cas.CasConfig;
import org.beangle.security.cas.auth.CasAuthentication;
import org.beangle.security.web.auth.preauth.AbstractPreauthFilter;
import org.beangle.security.web.auth.preauth.PreauthAuthentication;
import org.jasig.cas.client.proxy.ProxyGrantingTicketStorage;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.validation.TicketValidator;

/**
 * Processes a CAS service ticket.
 * <p>
 * A service ticket consists of an opaque ticket string. It arrives at this
 * filter by the user's browser successfully authenticating using CAS, and then
 * receiving a HTTP redirect to a <code>service</code>. The opaque ticket string
 * is presented in the <code>ticket</code> request parameter. This filter
 * monitors the <code>service</code> URL so it can receive the service ticket
 * and process it. The CAS server knows which <code>service</code> URL to use
 * via the {@link CasConfig#getService()} method.
 * </p>
 * <p>
 * Processing the service ticket involves creating a
 * <code>PreauthAuthentication</code> which uses {@link #STATEFUL_ID} for the
 * <code>principal</code> and the opaque ticket string as the
 * <code>credentials</code>.
 * </p>
 * <p>
 * The configured <code>AuthenticationManager</code> is expected to provide a
 * provider that can recognise <code>PreauthAuthentication</code>s containing
 * this special <code>principal</code> name, and process them accordingly by
 * validation with the CAS server.
 * </p>
 * <p>
 * By configuring a shared {@link ProxyGrantingTicketStorage} between the
 * {@link TicketValidator} and the CasPreauthFilter one can have the
 * CasPreauthFilter handle the proxying requirements for CAS. In addition, the
 * URI endpoint for the proxying would also need to be configured (i.e. the part
 * after protocol, hostname, and port).
 * 
 * @author chaostone
 * @version $Id: CasPreauthFilter.java$
 */
public class CasPreauthFilter extends AbstractPreauthFilter {

	/**
	 * The backing storage to store ProxyGrantingTicket requests.
	 */
	private ProxyGrantingTicketStorage proxyGrantingTicketStorage;
	/**
	 * The last portion of the receptor url, i.e. /proxy/receptor
	 */
	private String proxyReceptor;

	private CasConfig config;

	protected void initFilterBean() {
		super.initFilterBean();
		Validate.notNull(config, "config is a required field.");
	}

	@Override
	protected PreauthAuthentication getPreauthAuthentication(HttpServletRequest request,
			HttpServletResponse response) {
		final String username = CasAuthentication.STATEFUL_ID;
		String password = request.getParameter("ticket");
		if (password == null) {
			return null;
		} else {
			String url = CommonUtils.constructServiceUrl(request, response, null,
					config.getLocalServer(), "ticket", config.isEncode());
			return new CasAuthentication(username, password, url);
		}
	}

	/**
	 * Try to authenticate a pre-authenticated user with Beangle Security if the
	 * user has not yet been authenticated.
	 */
	public void doFilterHttp(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		if (null != proxyGrantingTicketStorage && CommonUtils.isNotEmpty(proxyReceptor)
				&& request.getRequestURI().endsWith(proxyReceptor)) {
			CommonUtils.readAndRespondToProxyReceptorRequest(request, response,
					proxyGrantingTicketStorage);
		} else {
			super.doFilterHttp(request, response, filterChain);
		}
	}

	public final void setProxyGrantingTicketStorage(
			final ProxyGrantingTicketStorage proxyGrantingTicketStorage) {
		this.proxyGrantingTicketStorage = proxyGrantingTicketStorage;
	}

	public void setConfig(CasConfig config) {
		this.config = config;
		if (null != config) {
			this.proxyReceptor = config.getProxyReceptor();
		}
	}
}
