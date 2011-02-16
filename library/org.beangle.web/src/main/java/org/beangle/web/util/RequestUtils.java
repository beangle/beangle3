/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.web.util;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.net.BCodec;
import org.apache.commons.lang.StringUtils;
import org.beangle.web.agent.Browser;
import org.beangle.web.agent.Os;
import org.beangle.web.agent.Useragent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RequestUtils {

	private static final Logger logger = LoggerFactory.getLogger(RequestUtils.class);

	private RequestUtils() {
	}

	/**
	 * 获取远程访问的客户IP
	 * 
	 * @param request
	 * @return
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
	 * 查找当前调用的action对应的.do<br>
	 * 例如http://localhost/myapp/dd.do 返回/dd.do<br>
	 * http://localhost/myapp/dir/to/dd.do 返回/dir/to/dd.do
	 * 
	 * @return
	 */
	public static String getServletPath(HttpServletRequest request) {
		String servletPath = request.getServletPath();
		if (StringUtils.isNotEmpty(servletPath)) {
			return servletPath;
		} else {
			String uri = request.getRequestURI();
			if (uri.length() == 1) return "";
			int start = uri.indexOf('/', 1);
			return servletPath = uri.substring(-1 == start ? 0 : start);
		}
	}

	public static String encodeAttachName(HttpServletRequest request, String attach_name) {
		String agent = request.getHeader("USER-AGENT");
		String newName = attach_name;
		try {
			if (null != agent && -1 != agent.indexOf("MSIE")) {
				newName = URLEncoder.encode(attach_name, "UTF-8");
			} else {
				// if (null != agent && -1 != agent.indexOf("Mozilla"))
				newName = new BCodec("UTF-8").encode(attach_name);
			}
		} catch (Exception e) {
			logger.error("cannot encode " + attach_name, e);
			return attach_name;
		}
		return newName;
	}

	/**
	 * @param request
	 * @return
	 */
	public static Useragent getUserAgent(HttpServletRequest request) {
		String head = request.getHeader("USER-AGENT");
		return new Useragent(getIpAddr(request), Browser.parse(head), Os.parse(head));
	}
}
