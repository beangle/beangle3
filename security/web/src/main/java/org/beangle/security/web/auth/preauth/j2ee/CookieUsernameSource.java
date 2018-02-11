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
package org.beangle.security.web.auth.preauth.j2ee;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.beangle.commons.lang.Option;
import org.beangle.security.web.auth.preauth.UsernameSource;

public class CookieUsernameSource implements UsernameSource {

  private String cookie;

  public Option<String> obtainUsername(HttpServletRequest request) {
    Cookie all_cookies[] = request.getCookies();
    if (all_cookies != null) {
      for (int i = 0; i < all_cookies.length; i++) {
        Cookie myCookie = all_cookies[i];
        if (myCookie.getName().equals(cookie)) return Option.some(myCookie.getValue());
      }
    }
    return Option.none();
  }

  public String getCookie() {
    return cookie;
  }

  public void setCookie(String cookie) {
    this.cookie = cookie;
  }

}
