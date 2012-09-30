/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.auth;

import java.util.Collection;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.beangle.security.core.GrantedAuthority;

public class AnonymousAuthentication extends AbstractAuthentication {

  private static final long serialVersionUID = 3236987468644441586L;

  private Object principal;

  /**
   * Default anonymous instance
   */
  public static AnonymousAuthentication Instance = new AnonymousAuthentication("anonymous", null);

  public AnonymousAuthentication(Object principal, Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.principal = principal;
    setAuthenticated(true);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof AnonymousAuthentication) {
      AbstractAuthentication test = (AbstractAuthentication) obj;
      return new EqualsBuilder().append(getPrincipal(), test.getPrincipal()).isEquals();
    }
    return false;
  }

  public Object getCredentials() {
    return "";
  }

  public Object getPrincipal() {
    return principal;
  }

}
