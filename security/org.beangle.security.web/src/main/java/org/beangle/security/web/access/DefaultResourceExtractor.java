/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.access;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * 去掉扩展名和!符号的资源提取器
 * 
 * @author chaostone
 */
public class DefaultResourceExtractor implements ResourceExtractor {

	public String extract(HttpServletRequest request) {
		String servletPath = request.getServletPath();
		if (StringUtils.isEmpty(servletPath)) {
			servletPath = StringUtils.substringAfter(request.getRequestURI(), request
					.getContextPath());
		}
		return extract(servletPath);
	}

	/**
	 * 过滤多余的/和空格
	 */
	public String extract(final String uri) {
		int firstSlot = 0;
		while ('/' == uri.charAt(firstSlot) || ' ' == uri.charAt(firstSlot)) {
			firstSlot++;
		}
		int lastDot = -1;
		for (int i = 0; i < uri.length(); i++) {
			char a = uri.charAt(i);
			if (a == '.' || a == '!') {
				lastDot = i;
				break;
			}
		}
		if (lastDot < 0) {
			lastDot = uri.length();
		}
		return uri.substring(firstSlot, lastDot);
	}
}
