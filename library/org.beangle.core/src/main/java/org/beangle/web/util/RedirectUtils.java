/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.web.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public final class RedirectUtils {

	public static final void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url)
			throws IOException {
		if (!url.startsWith("http")) {
			String cxtPath = request.getContextPath();
			String redirectUrl = response.encodeRedirectURL((cxtPath.equals("/") ? "" : (cxtPath)) + url);
			response.sendRedirect(redirectUrl);
		} else {
			response.sendRedirect(url);
		}
	}

	public static boolean isValidRedirectUrl(String url) {
		return StringUtils.isBlank(url) || url.startsWith("/") || url.toLowerCase().startsWith("http");
	}
}
