/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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
package org.beangle.security.web;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.commons.web.url.UrlBuilder;

public class FilterInvocation {

  private FilterChain chain;
  private ServletRequest request;
  private ServletResponse response;

  public FilterInvocation(ServletRequest request, ServletResponse response, FilterChain chain) {
    if ((request == null) || (response == null) || (chain == null))
      throw new IllegalArgumentException("Cannot pass null values to constructor");

    if (!(request instanceof HttpServletRequest))
      throw new IllegalArgumentException("Can only process HttpServletRequest");

    if (!(response instanceof HttpServletResponse))
      throw new IllegalArgumentException("Can only process HttpServletResponse");

    this.request = request;
    this.response = response;
    this.chain = chain;
  }

  public FilterChain getChain() {
    return chain;
  }

  /**
   * Indicates the URL that the user agent used for this request.
   * 
   * @return the full URL of this request
   */
  public String getFullRequestUrl() {
    HttpServletRequest r = getHttpRequest();
    UrlBuilder builder = new UrlBuilder(r.getContextPath());
    builder.scheme(r.getScheme()).serverName(r.getServerName()).port(r.getServerPort());
    builder.requestURI(r.getRequestURI()).pathInfo(r.getPathInfo());
    builder.queryString(r.getQueryString());
    return builder.buildUrl();
  }

  public HttpServletRequest getHttpRequest() {
    return (HttpServletRequest) request;
  }

  public HttpServletResponse getHttpResponse() {
    return (HttpServletResponse) response;
  }

  public ServletRequest getRequest() {
    return request;
  }

  /**
   * Obtains the web application-specific fragment of the URL.
   * 
   * @return the URL, excluding any server name, context path or servlet path
   */
  public String getRequestUrl() {
    HttpServletRequest r = getHttpRequest();
    UrlBuilder builder = new UrlBuilder(r.getContextPath());
    builder.servletPath(r.getServletPath());
    builder.requestURI(r.getRequestURI()).pathInfo(r.getPathInfo());
    builder.queryString(r.getQueryString());
    return builder.buildRequestUrl();
  }

  public ServletResponse getResponse() {
    return response;
  }

  public String toString() {
    return "FilterInvocation: URL: " + getRequestUrl();
  }
}
