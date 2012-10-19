/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.auth;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.commons.bean.Initializing;
import org.beangle.commons.lang.Assert;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.web.url.UrlBuilder;
import org.beangle.commons.web.util.RedirectUtils;
import org.beangle.security.core.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * By setting the <em>forceHttps</em> property to true, you may configure the class to force the
 * protocol used for the login form to be <code>HTTPS</code>, even if the original intercepted
 * request for a resource used the <code>HTTP</code> protocol. When this happens, after a successful
 * login (via HTTPS), the original resource will still be accessed as HTTP, via the original request
 * URL.
 * 
 * @author chaostone
 */
public class LoginUrlEntryPoint implements UrlEntryPoint, Initializing {

  private static final Logger logger = LoggerFactory.getLogger(LoginUrlEntryPoint.class);

  private String loginUrl;

  private boolean serverSideRedirect = false;

  public void init() throws Exception {
    Assert.notEmpty(loginUrl, "loginFormUrl must be specified");
  }

  /**
   * Allows subclasses to modify the login form URL that should be applicable
   * for a given request.
   * 
   * @param request the request
   * @param response the response
   * @param exception the exception
   * @return the URL (cannot be null or empty)
   */
  protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) {
    String originLoginUrl = getLoginUrl();
    if (originLoginUrl.contains("${goto}")) {
      UrlBuilder builder = new UrlBuilder(request.getContextPath());
      builder.scheme(request.getScheme()).serverName(request.getServerName()).port(request.getServerPort())
          .queryString(request.getQueryString());
      originLoginUrl = Strings.replace(originLoginUrl, "${goto}", builder.buildUrl());
    }
    return originLoginUrl;
  }

  /** Performs the redirect (or forward) to the login form URL. */
  public void commence(ServletRequest request, ServletResponse response, AuthenticationException authException)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    String redirectUrl = null;
    if (serverSideRedirect) {
      String loginForm = determineUrlToUseForThisRequest(httpRequest, httpResponse, authException);
      logger.debug("Server side forward to: {}", loginForm);
      RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(loginForm);
      dispatcher.forward(request, response);
      return;
    } else {
      // redirect to login page. Use https if forceHttps true
      redirectUrl = determineUrlToUseForThisRequest(httpRequest, httpResponse, authException);
    }
    RedirectUtils.sendRedirect(httpRequest, httpResponse, redirectUrl);
  }

  /**
   * The URL where the <code>AuthenticationProcessingFilter</code> login page
   * can be found. Should be relative to the web-app context path, and include
   * a leading <code>/</code>
   */
  public void setLoginUrl(String loginUrl) {
    this.loginUrl = loginUrl;
  }

  public String getLoginUrl() {
    return loginUrl;
  }

  /**
   * Tells if we are to do a server side include of the <code>loginFormUrl</code> instead of a 302
   * redirect.
   * 
   * @param serverSideRedirect
   */
  public void setServerSideRedirect(boolean serverSideRedirect) {
    this.serverSideRedirect = serverSideRedirect;
  }

  protected boolean isServerSideRedirect() {
    return serverSideRedirect;
  }
}
