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
package org.beangle.security.ids;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Cas {

  public static final String cleanup(CasConfig config, HttpServletRequest request,
                                     HttpServletResponse response) {
    Cookie[] cookies = request.getCookies();
    String contextPath = request.getContextPath();
    if (contextPath.length() == 0) contextPath = "/";

    if (null != cookies) {
      for (Cookie c : cookies) {
        if (c.getMaxAge() < 0) {
          String domain = c.getDomain();
          if (null == domain || domain.equals(request.getServerName())) {
            c.setMaxAge(0);
            c.setPath(contextPath);
            response.addCookie(c);
          }
        }
      }
    }
    return config.getCasServer() + "/logout";
  }
}
