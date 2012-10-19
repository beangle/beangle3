/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
