/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.access;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import org.beangle.security.web.auth.LoginUrlEntryPoint;
import org.testng.annotations.Test;

@Test
public class LoginUrlEntryPointTest {

  public void testDetectsMissingLoginFormUrl() throws Exception {
    LoginUrlEntryPoint ep = new LoginUrlEntryPoint();
    try {
      ep.init();
      fail("Should have thrown IllegalArgumentException");
    } catch (NullPointerException expected) {
      assertEquals("loginFormUrl must be specified", expected.getMessage());
    }
  }

  /*
   * public void testHttpsOperationFromOriginalHttpUrl() throws Exception {
   * MockHttpServletRequest request = new MockHttpServletRequest();
   * request.setRequestURI("/some_path"); request.setScheme("http");
   * request.setServerName("www.example.com");
   * request.setContextPath("/bigWebApp"); request.setServerPort(80);
   * MockHttpServletResponse response = new MockHttpServletResponse();
   * AuthenticationProcessingFilterEntryPoint ep = new
   * AuthenticationProcessingFilterEntryPoint(); ep.setLoginFormUrl("/hello");
   * ep.afterPropertiesSet(); ep.commence(request, response, null);
   * assertEquals("https://www.example.com/bigWebApp/hello",
   * response.getRedirectedUrl()); request.setServerPort(8080); response = new
   * MockHttpServletResponse(); ep.commence(request, response, null);
   * assertEquals("https://www.example.com:8443/bigWebApp/hello",
   * response.getRedirectedUrl()); // Now test an unusual custom HTTP:HTTPS is
   * handled properly request.setServerPort(8888); response = new
   * MockHttpServletResponse(); ep.commence(request, response, null);
   * assertEquals("https://www.example.com:8443/bigWebApp/hello",
   * response.getRedirectedUrl()); response = new MockHttpServletResponse();
   * ep = new AuthenticationProcessingFilterEntryPoint();
   * ep.setLoginFormUrl("/hello"); ep.afterPropertiesSet();
   * ep.commence(request, response, null);
   * assertEquals("https://www.example.com:9999/bigWebApp/hello",
   * response.getRedirectedUrl()); } public void
   * testHttpsOperationFromOriginalHttpsUrl() throws Exception {
   * MockHttpServletRequest request = new MockHttpServletRequest();
   * request.setRequestURI("/some_path"); request.setScheme("https");
   * request.setServerName("www.example.com");
   * request.setContextPath("/bigWebApp"); request.setServerPort(443);
   * MockHttpServletResponse response = new MockHttpServletResponse();
   * AuthenticationProcessingFilterEntryPoint ep = new
   * AuthenticationProcessingFilterEntryPoint(); ep.setLoginFormUrl("/hello");
   * ep.afterPropertiesSet(); ep.commence(request, response, null);
   * assertEquals("https://www.example.com/bigWebApp/hello",
   * response.getRedirectedUrl()); request.setServerPort(8443); response = new
   * MockHttpServletResponse(); ep.commence(request, response, null);
   * assertEquals("https://www.example.com:8443/bigWebApp/hello",
   * response.getRedirectedUrl()); } public void testNormalOperation() throws
   * Exception { AuthenticationProcessingFilterEntryPoint ep = new
   * AuthenticationProcessingFilterEntryPoint(); ep.setLoginFormUrl("/hello");
   * ep.afterPropertiesSet(); MockHttpServletRequest request = new
   * MockHttpServletRequest(); request.setRequestURI("/some_path");
   * request.setContextPath("/bigWebApp"); request.setScheme("http");
   * request.setServerName("www.example.com");
   * request.setContextPath("/bigWebApp"); request.setServerPort(80);
   * MockHttpServletResponse response = new MockHttpServletResponse();
   * ep.commence(request, response, null);
   * assertEquals("http://www.example.com/bigWebApp/hello",
   * response.getRedirectedUrl()); } public void
   * testOperationWhenHttpsRequestsButHttpsPortUnknown() throws Exception {
   * AuthenticationProcessingFilterEntryPoint ep = new
   * AuthenticationProcessingFilterEntryPoint(); ep.setLoginFormUrl("/hello");
   * ep.afterPropertiesSet(); MockHttpServletRequest request = new
   * MockHttpServletRequest(); request.setRequestURI("/some_path");
   * request.setContextPath("/bigWebApp"); request.setScheme("http");
   * request.setServerName("www.example.com");
   * request.setContextPath("/bigWebApp"); request.setServerPort(8888); // NB:
   * Port we can't resolve MockHttpServletResponse response = new
   * MockHttpServletResponse(); ep.commence(request, response, null); //
   * Response doesn't switch to HTTPS, as we didn't know HTTP port 8888 to //
   * HTTP port mapping
   * assertEquals("http://www.example.com:8888/bigWebApp/hello",
   * response.getRedirectedUrl()); } public void
   * testServerSideRedirectWithoutForceHttpsForwardsToLoginPage() throws
   * Exception { AuthenticationProcessingFilterEntryPoint ep = new
   * AuthenticationProcessingFilterEntryPoint(); ep.setLoginFormUrl("/hello");
   * ep.setServerSideRedirect(true); ep.afterPropertiesSet();
   * MockHttpServletRequest request = new MockHttpServletRequest();
   * request.setRequestURI("/bigWebApp/some_path");
   * request.setServletPath("/some_path");
   * request.setContextPath("/bigWebApp"); request.setScheme("http");
   * request.setServerName("www.example.com");
   * request.setContextPath("/bigWebApp"); request.setServerPort(80);
   * MockHttpServletResponse response = new MockHttpServletResponse();
   * ep.commence(request, response, null); assertEquals("/hello",
   * response.getForwardedUrl()); } public void
   * testServerSideRedirectWithForceHttpsRedirectsCurrentRequest() throws
   * Exception { AuthenticationProcessingFilterEntryPoint ep = new
   * AuthenticationProcessingFilterEntryPoint(); ep.setLoginFormUrl("/hello");
   * ep.setServerSideRedirect(true); ep.afterPropertiesSet();
   * MockHttpServletRequest request = new MockHttpServletRequest();
   * request.setRequestURI("/bigWebApp/some_path");
   * request.setServletPath("/some_path");
   * request.setContextPath("/bigWebApp"); request.setScheme("http");
   * request.setServerName("www.example.com");
   * request.setContextPath("/bigWebApp"); request.setServerPort(80);
   * MockHttpServletResponse response = new MockHttpServletResponse();
   * ep.commence(request, response, null);
   * assertEquals("https://www.example.com/bigWebApp/some_path",
   * response.getRedirectedUrl()); }
   */

}
