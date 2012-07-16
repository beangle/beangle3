/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security;

import org.beangle.security.auth.AnonymousAuthentication;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.context.SecurityContext;
import org.beangle.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

  public static boolean isValid(Authentication auth) {
    return (null != auth && !AnonymousAuthentication.class.isAssignableFrom(auth.getClass()));
  }

  public static boolean hasValidAuthentication() {
    return isValid(SecurityContextHolder.getContext().getAuthentication());
  }

  public static String getUsername() {
    SecurityContext context = SecurityContextHolder.getContext();
    if (null != context && null != context.getAuthentication()) {
      return context.getAuthentication().getName();
    } else return null;
  }

  public static Authentication getAuthentication() {
    SecurityContext context = SecurityContextHolder.getContext();
    return (null != context) ? context.getAuthentication() : null;
  }
}
