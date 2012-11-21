/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
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
import org.beangle.security.web.auth.AuthenticationServiceImpl;
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
    AuthenticationManager manager = new AuthenticationManager() {
      public Authentication authenticate(Authentication a) {
        throw new BadCredentialsException("Rejected");
      }
    };
    filter.setAuthenticationService(new AuthenticationServiceImpl(manager));
    filter.setContinueOnFail(false);
    filter.doFilter(request, new MockHttpServletResponse(), mock(FilterChain.class));
  }

}
