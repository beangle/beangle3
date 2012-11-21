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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.beangle.security.cas.CasConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.testng.annotations.Test;

@Test
public class CasEntryPointTest {

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
    MockHttpServletRequest request = new MockHttpServletRequest(null, "/bigWebApp/some_path");
    request.setServerName("mycompany.com");
    request.setScheme("https");
    request.setServerPort(443);
    MockHttpServletResponse response = new MockHttpServletResponse();
    ep.init();
    ep.commence(request, response, null);
    assertEquals(
        response.getRedirectedUrl(),
        "https://cas/login?service="
            + URLEncoder.encode("https://mycompany.com/bigWebApp/some_path", "UTF-8"));
  }

  public void testNormalOperationWithRenewTrue() throws Exception {
    CasConfig config = new CasConfig("https://cas");
    config.setRenew(true);
    CasEntryPoint ep = new CasEntryPoint(config);
    MockHttpServletRequest request = new MockHttpServletRequest(null, "/bigWebApp/some_path");
    request.setServerName("mycompany.com");
    request.setScheme("https");
    request.setServerPort(443);

    MockHttpServletResponse response = new MockHttpServletResponse();
    ep.init();
    ep.commence(request, response, null);
    assertEquals(
        "https://cas/login?service="
            + URLEncoder.encode("https://mycompany.com/bigWebApp/some_path", "UTF-8") + "&renew=true",
        response.getRedirectedUrl());
  }

  public void testConstuctServiceUrl() {
    CasConfig config = new CasConfig();
    config.setCasServer("http://www.mycompany.com/cas");
    // config.setLocalServer("localhost:8080/demo");
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setContextPath("/demo");
    request.setServletPath("/home.action");
    request.setRequestURI("/demo/home.action");
    HttpServletResponse response = new MockHttpServletResponse();
    final String urlEncodedService = CasEntryPoint.constructServiceUrl(request, response, null,
        CasConfig.getLocalServer(request), "ticket", config.isEncode());
    System.out.println(urlEncodedService);

    final String urlEncodedService2 = CasEntryPoint.constructServiceUrl(request, response, null,
        "localhost:8080", "ticket", config.isEncode());
    System.out.println(urlEncodedService2);
  }

}
