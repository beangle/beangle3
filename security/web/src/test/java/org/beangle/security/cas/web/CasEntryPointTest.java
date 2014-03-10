/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.security.cas.CasConfig;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

@Test
public class CasEntryPointTest {

  private static final Logger logger = LoggerFactory.getLogger(CasEntryPointTest.class);

  @Test(expectedExceptions = NullPointerException.class)
  public void testDetectsMissingUrl() throws Exception {
    CasConfig config = new CasConfig();
    config.init();
    CasEntryPoint ep = new CasEntryPoint(config);
    ep.init();
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testDetectsMissingConfig() throws Exception {
    new CasEntryPoint().init();
  }

  public void testGettersSetters() {
    CasConfig config = new CasConfig("https://cas");
    assertEquals("https://cas", config.getCasServer());

    CasEntryPoint ep = new CasEntryPoint(config);
    assertTrue(ep.getConfig() != null);
  }

  public void testNormalOperationWithRenewFalse() throws Exception {
    CasConfig config = new CasConfig("https://cas");
    config.setRenew(false);
    CasEntryPoint ep = new CasEntryPoint(config);
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getRequestURI()).thenReturn("/bigWebApp/some_path");
    when(request.getServerName()).thenReturn("mycompany.com");
    when(request.getScheme()).thenReturn("https");
    when(request.getServerPort()).thenReturn(443);
    HttpServletResponse response = mockResponse();
    ep.init();

    ep.commence(request, response, null);
    verify(response).sendRedirect(
        "https://cas/login?service="
            + URLEncoder.encode("https://mycompany.com/bigWebApp/some_path", "UTF-8"));
  }

  private HttpServletResponse mockResponse() {
    HttpServletResponse response = mock(HttpServletResponse.class);
    when(response.encodeURL(any(String.class))).then(new Answer<String>() {
      public String answer(InvocationOnMock invocation) throws Throwable {
        return invocation.getArguments()[0].toString();
      }
    });
    return response;
  }

  public void testNormalOperationWithRenewTrue() throws Exception {
    CasConfig config = new CasConfig("https://cas");
    config.setRenew(true);
    CasEntryPoint ep = new CasEntryPoint(config);
    ep.init();
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getRequestURI()).thenReturn("/bigWebApp/some_path");
    when(request.getServerName()).thenReturn("mycompany.com");
    when(request.getScheme()).thenReturn("https");
    when(request.getServerPort()).thenReturn(443);

    HttpServletResponse response = mockResponse();

    ep.commence(request, response, null);
    verify(response).sendRedirect(
        "https://cas/login?service="
            + URLEncoder.encode("https://mycompany.com/bigWebApp/some_path", "UTF-8") + "&renew=true");
  }

  public void testConstuctServiceUrl() {
    CasConfig config = new CasConfig();
    config.setCasServer("http://www.mycompany.com/cas");
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getRequestURI()).thenReturn("/demo/home.action");
    when(request.getServletPath()).thenReturn("/home.action");
    when(request.getContextPath()).thenReturn("/demo");
    when(request.getScheme()).thenReturn("http");

    HttpServletResponse response = mock(HttpServletResponse.class);
    final String urlEncodedService = CasEntryPoint.constructServiceUrl(request, response, null,
        CasConfig.getLocalServer(request), "ticket", config.isEncode());
    logger.debug(urlEncodedService);

    final String urlEncodedService2 = CasEntryPoint.constructServiceUrl(request, response, null,
        "localhost:8080", "ticket", config.isEncode());
    logger.debug(urlEncodedService2);
  }

}
