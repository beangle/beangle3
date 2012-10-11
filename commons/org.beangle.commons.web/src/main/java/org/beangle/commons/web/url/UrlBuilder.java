/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License; Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.web.url;

import org.beangle.commons.lang.Objects;
import org.beangle.commons.lang.Strings;

/**
 * @author chaostone
 * @version $Id: UrlBuilder.java Nov 12; 2010 5:42:10 PM chaostone $
 */
public class UrlBuilder {
  private String scheme;
  private String serverName;
  private int port;
  // start with /
  private String contextPath;
  // start with /
  private String servletPath;
  // start with /
  private String requestURI;
  private String pathInfo;
  private String queryString;

  public UrlBuilder(String contextPath) {
    super();
    if (Strings.isEmpty(contextPath)) {
      this.contextPath = "/";
    } else {
      this.contextPath = contextPath.trim();
    }
  }

  /**
   * Returns servetPath without contextPath
   */
  private String buildServletPath() {
    String uri = servletPath;
    if (uri == null && null != requestURI) {
      uri = requestURI;
      if (!contextPath.equals("/")) uri = uri.substring(contextPath.length());
    }
    return (null == uri) ? "" : uri;
  }

  /**
   * Returns request Url contain pathinfo and queryString but without contextPath.
   */
  public String buildRequestUrl() {
    StringBuilder sb = new StringBuilder();
    sb.append(buildServletPath());
    if (null != pathInfo) {
      sb.append(pathInfo);
    }
    if (null != queryString) {
      sb.append('?').append(queryString);
    }
    return sb.toString();
  }

  /**
   * Returns full url
   */
  public String buildUrl() {
    StringBuilder sb = new StringBuilder();
    boolean includePort = true;
    if (null != scheme) {
      sb.append(scheme).append("://");
      includePort = (port != (scheme.equals("http") ? 80 : 443));
    }
    if (null != serverName) {
      sb.append(serverName);
      if (includePort && port > 0) {
        sb.append(':').append(port);
      }
    }
    if (!Objects.equals(contextPath, "/")) {
      sb.append(contextPath);
    }
    sb.append(buildRequestUrl());
    return sb.toString();
  }

  public UrlBuilder scheme(String scheme) {
    this.scheme = scheme;
    return this;
  }

  public UrlBuilder serverName(String serverName) {
    this.serverName = serverName;
    return this;
  }

  public UrlBuilder port(int port) {
    this.port = port;
    return this;
  }

  /**
   * ContextPath should start with / but not ended with /
   * 
   * @param contextPath
   */
  public UrlBuilder contextPath(String contextPath) {
    this.contextPath = contextPath;
    return this;
  }

  /**
   * Set servletPath ,start with /
   * 
   * @param servletPath
   */
  public UrlBuilder servletPath(String servletPath) {
    this.servletPath = servletPath;
    return this;
  }

  /**
   * Set requestURI ,it should start with /
   * 
   * @param requestURI
   */
  public UrlBuilder requestURI(String requestURI) {
    this.requestURI = requestURI;
    return this;
  }

  public UrlBuilder pathInfo(String pathInfo) {
    this.pathInfo = pathInfo;
    return this;
  }

  public UrlBuilder queryString(String queryString) {
    this.queryString = queryString;
    return this;
  }
}
