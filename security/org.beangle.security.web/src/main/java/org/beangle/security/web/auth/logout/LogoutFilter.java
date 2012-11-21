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
package org.beangle.security.web.auth.logout;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.commons.lang.Assert;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.web.filter.GenericHttpFilter;
import org.beangle.commons.web.util.RedirectUtils;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.context.SecurityContextHolder;

/**
 * Logs a principal out.
 * <p>
 * Polls a series of {@link LogoutHandler}s. The handlers should be specified in the order they are
 * required. Generally you will want to call logout handlers
 * <code>TokenBasedRememberMeServices</code> and <code>SecurityContextLogoutHandler</code> (in that
 * order).
 * </p>
 * <p>
 * After logout, the URL specified by {@link #logoutSuccessUrl} will be shown.
 * </p>
 */
public class LogoutFilter extends GenericHttpFilter {
  private String filterProcessesUrl = "/logout";
  private String logoutSuccessUrl;
  private LogoutHandlerStack handlerStack;

  public LogoutFilter(String logoutSuccessUrl, LogoutHandlerStack stack) {
    Assert.notNull(handlerStack, "LogoutHandlerStack are required");
    this.handlerStack = stack;
    this.logoutSuccessUrl = logoutSuccessUrl;
    Assert.isTrue(RedirectUtils.isValidRedirectUrl(logoutSuccessUrl), logoutSuccessUrl
        + " isn't a valid redirect URL");
  }

  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
      ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;

    if (requiresLogout(request, response)) {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      logger.debug("Logging out user '{}' and redirecting to logout page", auth);
      handlerStack.logout(request, response, auth);
      String targetUrl = determineTargetUrl(request, response);
      RedirectUtils.sendRedirect(request, response, targetUrl);
      return;
    }
    chain.doFilter(request, response);
  }

  /**
   * Allow subclasses to modify when a logout should take place.
   * 
   * @param request
   *          the request
   * @param response
   *          the response
   * @return <code>true</code> if logout should occur, <code>false</code> otherwise
   */
  protected boolean requiresLogout(HttpServletRequest request, HttpServletResponse response) {
    String uri = request.getRequestURI();
    int pathParamIndex = uri.indexOf(';');

    if (pathParamIndex > 0) {
      // strip everything from the first semi-colon
      uri = uri.substring(0, pathParamIndex);
    }

    int queryParamIndex = uri.indexOf('?');

    if (queryParamIndex > 0) {
      // strip everything from the first question mark
      uri = uri.substring(0, queryParamIndex);
    }

    if ("".equals(request.getContextPath())) { return uri.endsWith(filterProcessesUrl); }

    return uri.endsWith(request.getContextPath() + filterProcessesUrl);
  }

  /**
   * Returns the target URL to redirect to after logout.
   * <p>
   * By default it will check for a <tt>logoutSuccessUrl</tt> parameter in the request and use this.
   * If that isn't present it will use the configured <tt>logoutSuccessUrl</tt>. If this hasn't been
   * set it will check the Referer header and use the URL from there.
   */
  protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
    String targetUrl = request.getParameter("logoutSuccessUrl");

    if (!Strings.isNotEmpty(targetUrl)) {
      targetUrl = getLogoutSuccessUrl();
    }

    if (!Strings.isNotEmpty(targetUrl)) {
      targetUrl = request.getHeader("Referer");
    }

    if (!Strings.isNotEmpty(targetUrl)) {
      targetUrl = "/";
    }

    return targetUrl;
  }

  public void setFilterProcessesUrl(String filterProcessesUrl) {
    Assert.notEmpty(filterProcessesUrl, "FilterProcessesUrl required");
    Assert.isTrue(RedirectUtils.isValidRedirectUrl(filterProcessesUrl), filterProcessesUrl
        + " isn't a valid redirect URL");
    this.filterProcessesUrl = filterProcessesUrl;
  }

  protected String getLogoutSuccessUrl() {
    return logoutSuccessUrl;
  }

  protected String getFilterProcessesUrl() {
    return filterProcessesUrl;
  }
}
