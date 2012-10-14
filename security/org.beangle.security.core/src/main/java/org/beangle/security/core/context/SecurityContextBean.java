/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.context;

import org.beangle.security.core.Authentication;

/**
 * Base implementation of {@link SecurityContext}.
 * <p>
 * Used by default by {@link SecurityContextHolder}.
 * </p>
 * 
 * @author chaostone
 * @version $Id: SecurityContextImpl.java 2217 2007-10-27 00:45:30Z $
 */
public class SecurityContextBean implements SecurityContext {

  private static final long serialVersionUID = 3146265469090172129L;

  private Authentication authentication;

  public boolean equals(Object obj) {
    if (obj instanceof SecurityContextBean) {
      SecurityContextBean test = (SecurityContextBean) obj;

      if ((this.getAuthentication() == null) && (test.getAuthentication() == null)) { return true; }

      if ((this.getAuthentication() != null) && (test.getAuthentication() != null)
          && this.getAuthentication().equals(test.getAuthentication())) { return true; }
    }

    return false;
  }

  public Authentication getAuthentication() {
    return authentication;
  }

  public int hashCode() {
    if (this.authentication == null) {
      return -1;
    } else {
      return this.authentication.hashCode();
    }
  }

  public void setAuthentication(Authentication authentication) {
    this.authentication = authentication;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append(super.toString());

    if (this.authentication == null) {
      sb.append(": Null authentication");
    } else {
      sb.append(": Authentication: ").append(this.authentication);
    }

    return sb.toString();
  }
}
