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

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.commons.lang.Strings;

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
    return Strings.isBlank(url) || url.startsWith("/") || url.toLowerCase().startsWith("http");
  }
}
