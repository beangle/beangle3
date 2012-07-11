/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.auth;

import org.beangle.security.auth.AbstractAuthenticationManager;
import org.beangle.security.auth.BadCredentialsException;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.AuthenticationException;

public class MockAuthenticationManager extends AbstractAuthenticationManager {

  private boolean grantAccess = true;

  public MockAuthenticationManager(boolean grantAccess) {
    this.grantAccess = grantAccess;
  }

  public MockAuthenticationManager() {
    super();
  }

  public Authentication doAuthentication(Authentication authentication) throws AuthenticationException {
    if (grantAccess) {
      return authentication;
    } else {
      throw new BadCredentialsException("MockAuthenticationManager instructed to deny access");
    }
  }
}
