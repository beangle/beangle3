/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.web.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

public final class RedirectUtils {

	public static final void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url)
			throws IOException {
		String contextPath = request.getContextPath();
		String redirectUrl = response.encodeRedirectURL((contextPath.equals("/") ? "" : (contextPath)) + url);
		response.sendRedirect(redirectUrl);
	}

	public static boolean isValidRedirectUrl(String url) {
		return !StringUtils.hasText(url) || url.startsWith("/") || url.toLowerCase().startsWith("http");
	}
}
