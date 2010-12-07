/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.web.util;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.net.BCodec;
import org.apache.commons.lang.StringUtils;
import org.beangle.commons.lang.StrUtils;
import org.beangle.web.Useragent;

public final class RequestUtils {

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
	 * 例如http://localhost/myapp/dd.do 返回dd.do<br>
	 * http://localhost/myapp/dir/to/dd.do 返回dir/to/dd.do
	 * 
	 * @return
	 */
	public static String getRequestURI(HttpServletRequest request) {
		String actionName = request.getServletPath();
		if (actionName.startsWith("/")) {
			actionName = actionName.substring(1);
		}
		return actionName;
	}

	public static String encodeAttachName(HttpServletRequest request, String attch_name)
			throws Exception {
		String agent = request.getHeader("USER-AGENT");
		String newName = null;
		if (null != agent && -1 != agent.indexOf("MSIE")) {
			newName = URLEncoder.encode(attch_name, "UTF-8");
		} else if (null != agent && -1 != agent.indexOf("Mozilla")) {
			newName = new BCodec("UTF-8").encode(attch_name);
			// newName = MimeUtility.encodeText(attch_name, "UTF-8", "B");
		}
		return newName;
	}

	/**
	 * FIXME just for firefox
	 * 
	 * @param request
	 * @return
	 */
	public static Useragent getUserAgent(HttpServletRequest request) {
		StringBuilder head = new StringBuilder(request.getHeader("USER-AGENT"));
		// delete char in (),then split
		int start = head.indexOf("(");
		int end = head.indexOf(")", start);
		head.delete(start, end + 1);
		// String remark=head.substring(start, end);
		String[] headers = StrUtils.split(head.toString());
		String browser = headers[headers.length - 1];
		String os = headers[headers.length - 2];
		// Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.2.12) Gecko/20101027
		// Fedora/3.6.12-1.fc14 Firefox/3.6.12
		return new Useragent(getIpAddr(request), StringUtils.substringBefore(browser, "/"),
				StringUtils.substringAfter(browser, "/"), StringUtils.substringBefore(os, "/"),
				StringUtils.substringAfterLast(os, "."));
	}
}
