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
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertTrue;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.beangle.security.auth.AuthenticationManager;
import org.beangle.security.auth.BadCredentialsException;
import org.beangle.security.cas.CasConfig;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.AuthenticationException;
import org.beangle.security.web.auth.AuthenticationServiceImpl;
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

  private HttpServletRequest mockRequest() {
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter("ticket")).thenReturn("ST-0-ER94xMJmn6pha35CQRoZ");
    when(request.getMethod()).thenReturn("GET");
    when(request.getSession()).thenReturn(mock(HttpSession.class));
    return request;
  }

  public void testNormalOperation() throws Exception {
    HttpServletRequest request = mockRequest();
    Authentication result = filter.getPreauthAuthentication(request, mock(HttpServletResponse.class));
    assertTrue(result != null);
  }

  @Test(expectedExceptions = AuthenticationException.class)
  public void testNullServiceTicketHandledGracefully() throws Exception {
    HttpServletRequest request = mockRequest();
    AuthenticationManager manager = new AuthenticationManager() {
      public Authentication authenticate(Authentication a) {
        throw new BadCredentialsException("Rejected");
      }
    };
    filter.setAuthenticationService(new AuthenticationServiceImpl(manager));
    filter.setContinueOnFail(false);
    filter.doFilter(request, mock(HttpServletResponse.class), mock(FilterChain.class));
  }

}
