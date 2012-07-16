/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint;

import org.beangle.security.blueprint.service.UserToken;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.AuthenticationException;
import org.beangle.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

  private static ThreadLocal<String> resource = new ThreadLocal<String>();

  public static UserToken getPrincipal() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (null == auth) throw new AuthenticationException();
    UserToken user = (UserToken) auth.getPrincipal();
    if (null == user.getId()) throw new AuthenticationException();
    return user;
  }

  public static Long getUserId() {
    return getPrincipal().getId();
  }

  public static String getUsername() {
    return getPrincipal().getUsername();
  }

  public static String getFullname() {
    return getPrincipal().getFullname();
  }

  public static String getResource() {
    return resource.get();
  }

  public static void setResource(String name) {
    resource.set(name);
  }
}
