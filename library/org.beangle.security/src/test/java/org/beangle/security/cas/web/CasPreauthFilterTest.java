/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.cas.web;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertTrue;

import javax.servlet.FilterChain;

import org.beangle.security.auth.AuthenticationManager;
import org.beangle.security.auth.BadCredentialsException;
import org.beangle.security.cas.CasConfig;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.AuthenticationException;
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
	}

	public void testGettersSetters() {
		filter.setConfig(mock(CasConfig.class));
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

}
