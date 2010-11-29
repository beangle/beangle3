/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.access.log;

import javax.servlet.http.HttpServletRequest;


public class AccesslogFactory {

	public static Accesslog getLog(HttpServletRequest request) {
		DefaultAccesslog log = new DefaultAccesslog();
		log.setUser(request.getSession().getAttribute(AccessConfig.getInstance().getUserKey()));
		log.setUri(getRequestURI(request));
		log.setParams(request.getQueryString());
		return log;
	}

	public static String getRequestURI(HttpServletRequest request) {
		String actionName = request.getServletPath();
		if (actionName.startsWith("/")) {
			actionName = actionName.substring(1);
		}
		return actionName;
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
}
