/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.cas.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.testng.Assert.assertTrue;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import org.beangle.security.auth.AuthenticationManager;
import org.beangle.security.auth.BadCredentialsException;
import org.beangle.security.cas.CasConfig;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.AuthenticationException;
import org.jasig.cas.client.proxy.ProxyGrantingTicketStorage;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class CasPreauthFilterTest {
	CasPreauthFilter filter;
	CasConfig config;

	@BeforeMethod
	public void setUp() {
		filter = new CasPreauthFilter();
		config = new CasConfig();
		filter.setConfig(config);
		config.setLocalServer("http://localhost/demo");
	}

	public void testGettersSetters() {
		filter.setConfig(mock(CasConfig.class));
		filter.setProxyGrantingTicketStorage(mock(ProxyGrantingTicketStorage.class));
	}

	public void testNormalOperation() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest("GET", "/demo/any-path");
		request.addParameter("ticket", "ST-0-ER94xMJmn6pha35CQRoZ");
		Authentication result = filter.getPreauthAuthentication(request, new MockHttpServletResponse());
		assertTrue(result != null);
	}

	@Test(expectedExceptions = AuthenticationException.class)
	public void testNullServiceTicketHandledGracefully() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest("GET", "/demo/any-path");
		request.addParameter("ticket", "ST-0-ER94xMJmn6pha35CQRoZ");
		filter.setAuthenticationManager(new AuthenticationManager() {
			public Authentication authenticate(Authentication a) {
				throw new BadCredentialsException("Rejected");
			}
		});
		filter.setContinueOnFail(false);
		filter.doFilter(request, new MockHttpServletResponse(), mock(FilterChain.class));
	}

	public void testAuthenticateProxyUrl() throws IOException, ServletException {
		config.setProxyReceptor("/proxy/receptor");
		filter.setConfig(config);
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		request.setRequestURI("/demo/proxy/receptor");
		FilterChain chain = mock(FilterChain.class);
		filter.setProxyGrantingTicketStorage(mock(ProxyGrantingTicketStorage.class));
		filter.doFilterHttp(request, response, chain);
		verifyZeroInteractions(chain);
		request.setRequestURI("/other");
		filter.doFilterHttp(request, response, chain);
		verify(chain, times(1)).doFilter(request, response);
	}
}
