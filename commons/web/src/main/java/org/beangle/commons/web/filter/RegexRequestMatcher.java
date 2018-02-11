/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.web.filter;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.beangle.commons.http.HttpMethod;
import org.beangle.commons.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Uses a regular expression to decide whether a supplied the URL of a supplied
 * {@code HttpServletRequest}. Can also be configured to match a specific HTTP
 * method. The match is performed against the {@code servletPath + pathInfo + queryString} of the
 * request and is
 * case-sensitive by default. Case-insensitive matching can be used by using the
 * constructor which takes the {@code caseInsentitive} argument.
 * 
 * @author chaostone
 */
public final class RegexRequestMatcher implements RequestMatcher {
  private final static Logger logger = LoggerFactory.getLogger(RegexRequestMatcher.class);

  private final Pattern pattern;
  private final HttpMethod httpMethod;

  /**
   * Creates a case-sensitive {@code Pattern} instance to match against the
   * request.
   * 
   * @param pattern
   *          the regular expression to compile into a pattern.
   * @param httpMethod
   *          the HTTP method to match. May be null to match all methods.
   */
  public RegexRequestMatcher(String pattern, String httpMethod) {
    this(pattern, httpMethod, false);
  }

  /**
   * As above, but allows setting of whether case-insensitive matching should
   * be used.
   * 
   * @param pattern
   *          the regular expression to compile into a pattern.
   * @param httpMethod
   *          the HTTP method to match. May be null to match all methods.
   * @param caseInsensitive
   *          if true, the pattern will be compiled with the {@link Pattern#CASE_INSENSITIVE} flag
   *          set.
   */
  public RegexRequestMatcher(String pattern, String httpMethod, boolean caseInsensitive) {
    if (caseInsensitive) this.pattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
    else this.pattern = Pattern.compile(pattern);
    this.httpMethod = Strings.isNotEmpty(httpMethod) ? HttpMethod.valueOf(httpMethod) : null;
  }

  /**
   * Performs the match of the request URL ( {@code servletPath + pathInfo + queryString}) against
   * the compiled
   * pattern.
   * 
   * @param request the request to match
   * @return true if the pattern matches the URL, false otherwise.
   */
  public boolean matches(HttpServletRequest request) {
    if (httpMethod != null && httpMethod != HttpMethod.valueOf(request.getMethod())) { return false; }
    String url = request.getServletPath();
    String pathInfo = request.getPathInfo();
    String query = request.getQueryString();

    if (pathInfo != null || query != null) {
      StringBuilder sb = new StringBuilder(url);
      if (pathInfo != null) sb.append(pathInfo);
      if (query != null) sb.append(query);
      url = sb.toString();
    }
    logger.debug("Checking match of request : '{}'; against '{}'", url, pattern);
    return pattern.matcher(url).matches();
  }
}
