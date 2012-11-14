/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.web.util;

import java.net.URLEncoder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.net.BCodec;
import org.beangle.commons.http.agent.Browser;
import org.beangle.commons.http.agent.Os;
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
      //process context
      String context = request.getContextPath();
      if (context.length() == 1) context = "";
      if ('/' == context.charAt(context.length() - 1)) context = context.substring(0, context.length() - 1);
      
      return servletPath = uri.substring(context.length());
    }
  }

  public static String getRealPath(ServletContext servletContext, String path) {
    if (!path.startsWith("/")) {
      path = "/" + path;
    }
    String realPath = servletContext.getRealPath(path);
    if (realPath == null) { throw new RuntimeException("ServletContext resource [" + path
        + "] cannot be resolved to absolute file path - " + "web application archive not expanded?"); }
    return realPath;
  }

  public static String encodeAttachName(HttpServletRequest request, String attach_name) {
    String agent = request.getHeader("USER-AGENT");
    String newName = attach_name;
    try {
      if (null != agent && -1 != agent.indexOf("MSIE")) {
        newName = URLEncoder.encode(attach_name, "UTF-8");
      } else {
        newName = new BCodec("UTF-8").encode(attach_name);
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
    return new Useragent(getIpAddr(request), Browser.parse(head), Os.parse(head));
  }
}
