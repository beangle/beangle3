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
package org.beangle.commons.web.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.beangle.commons.codec.net.BCoder;
import org.beangle.commons.http.agent.Browser;
import org.beangle.commons.http.agent.BrowserCategory;
import org.beangle.commons.http.agent.Os;
import org.beangle.commons.http.agent.OsCategory;
import org.beangle.commons.http.agent.Useragent;
import org.beangle.commons.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Request Utility
 *
 * @author chaostone
 * @since 2.0
 */
public final class RequestUtils {

  private static final Logger logger = LoggerFactory.getLogger(RequestUtils.class);

  private RequestUtils() {
  }

  /**
   * Returns remote ip address.
   * <ul>
   * <li>First,it lookup request header("x-forwarded-for"->"Proxy-Client-IP"->"WL-Proxy-Client-IP")
   * <li>Second,invoke request.getRemoteAddr()
   * </ul>
   *
   * @param request
   */
  public static String getIpAddr(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }

  /**
   * Return the true servlet path.
   * When servletPath provided by container is empty,It will return requestURI'
   * <p>
   * 查找当前调用的action对应的.do<br>
   * 例如http://localhost/myapp/dd.do 返回/dd.do<br>
   * http://localhost/myapp/dir/to/dd.do 返回/dir/to/dd.do
   */
  public static String getServletPath(HttpServletRequest request) {
    String servletPath = request.getServletPath();
    if (Strings.isNotEmpty(servletPath)) {
      return servletPath;
    } else {
      String uri = request.getRequestURI();
      if (uri.length() == 1) return "";
      // process context
      String context = request.getContextPath();
      int length = context.length();
      if (length > 2) {
        if ('/' == context.charAt(length - 1)) context = context.substring(0, length - 1);
        return servletPath = uri.substring(context.length());
      } else {
        return uri;
      }
    }
  }

  public static String getRealPath(ServletContext servletContext, String path) {
    if (!path.startsWith("/")) path = "/" + path;

    String realPath = servletContext.getRealPath(path);
    if (realPath == null) { throw new RuntimeException("ServletContext resource [" + path
        + "] cannot be resolved to absolute file path - " + "web application archive not expanded?"); }
    return realPath;
  }

  public static String encodeAttachName(HttpServletRequest request, String attach_name) {
    String agent = request.getHeader("USER-AGENT");
    String newName = attach_name;
    try {
      if (null != agent && (agent.contains("MSIE") || agent.contains("Trident"))) {
        newName = URLEncoder.encode(attach_name, "UTF-8");
      } else {
        newName = new BCoder().encode(attach_name);
      }
    } catch (Exception e) {
      logger.error("cannot encode " + attach_name, e);
      return attach_name;
    }
    return newName;
  }

  /**
   * Return {@code Useragent} of request.
   *
   * @param request
   */
  public static Useragent getUserAgent(HttpServletRequest request) {
    String head = request.getHeader("USER-AGENT");
    Useragent agent = new Useragent(getIpAddr(request), Browser.parse(head), Os.parse(head));
    if (agent.getOs().equals(OsCategory.Unknown) || agent.getBrowser().equals(BrowserCategory.Unknown)) {
      logger.info("Cannot parser user agent:{}", request.getHeader("USER-AGENT"));
    }
    return agent;
  }

  public static Map getParams(HttpServletRequest request, String prefix) {
    return getParamsMap(request, prefix, null, true);
  }

  public static Map getParams(HttpServletRequest request, String prefix, String exclusiveAttrNames) {
    return getParamsMap(request, prefix, exclusiveAttrNames, true);
  }

  public static Map getParamsMap(HttpServletRequest request, String prefix, String exclusiveAttrNames) {
    return getParamsMap(request, prefix, exclusiveAttrNames, false);
  }

  public static Map getParamsMap(HttpServletRequest request, String prefix, String exclusiveAttrNames,
      boolean stripPrefix) {
    Map params = new HashMap();
    if (Strings.isNotEmpty(exclusiveAttrNames)) exclusiveAttrNames = "," + exclusiveAttrNames + ",";
    else exclusiveAttrNames = null;
    Enumeration names = request.getParameterNames();
    do {
      if (!names.hasMoreElements()) break;
      String attr = (String) names.nextElement();
      if (attr.indexOf(prefix + ".") == 0
          && (null == exclusiveAttrNames || !Strings.contains(exclusiveAttrNames, "," + attr + ",")))
        params.put(stripPrefix ? ((Object) (attr.substring(prefix.length() + 1))) : ((Object) (attr)),
            request.getParameter(attr));
    } while (true);
    String queryString = request.getQueryString();
    if (Strings.isNotEmpty(queryString)) {
      String paramPairs[] = Strings.split(queryString, "&");
      for (int i = 0; i < paramPairs.length; i++) {
        String paramPair = paramPairs[i];
        if (paramPair.indexOf(prefix + ".") != 0) continue;
        int equalIndex = paramPair.indexOf("=");
        if (-1 == equalIndex) continue;
        String param = paramPair.substring(0, equalIndex);
        if (Strings.contains(exclusiveAttrNames, "," + param + ",")) continue;
        try {
          params.put(stripPrefix ? ((Object) (param.substring(prefix.length() + 1))) : ((Object) (param)),
              URLDecoder.decode(paramPair.substring(equalIndex + 1), "UTF-8"));
        } catch (Exception e) {
        }
      }

    }
    return params;
  }

  public static String get(HttpServletRequest request, String name) {
    return request.getParameter(name);
  }

  public static Float getFloat(HttpServletRequest request, String name) {
    String v = request.getParameter(name);
    return (v == null) ? null : Float.valueOf(v);
  }

  public static Integer getInteger(HttpServletRequest request, String name) {
    String v = request.getParameter(name);
    return (v == null) ? null : Integer.valueOf(v);
  }

  public static Long getLong(HttpServletRequest request, String name) {
    String v = request.getParameter(name);
    return (v == null) ? null : Long.valueOf(v);
  }

  public static Boolean getBoolean(HttpServletRequest request, String name) {
    String v = request.getParameter(name);
    if (v == null) {
      return null;
    } else {
      if (v.equals("1") || v.equals("true") || v.equals("yes") || v.equals("on")) {
        return Boolean.TRUE;
      } else {
        return Boolean.FALSE;
      }
    }
  }

  public static java.sql.Date getDate(HttpServletRequest request, String name) {
    String dateStr = get(request, name);
    if (Strings.isBlank(dateStr)) {
      return null;
    } else {
      return java.sql.Date.valueOf(name);
    }
  }
}
