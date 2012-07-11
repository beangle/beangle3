/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.ldap.auth;

import org.beangle.security.auth.BadCredentialsException;
import org.beangle.security.auth.dao.AbstractUserDetailAuthenticationProvider;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.AuthenticationException;
import org.beangle.security.core.userdetail.UserDetail;
import org.beangle.security.core.userdetail.UserDetailService;

/**
 * 读取ldap的用户信息<br>
 * 
 * @author chaostone
 */
public class DefaultLdapAuthenticationProvider extends AbstractUserDetailAuthenticationProvider {

  protected LdapValidator ldapValidator;

  private UserDetailService userDetailService;

  @Override
  protected void additionalAuthenticationChecks(UserDetail user, Authentication authentication)
      throws AuthenticationException {
    if (!ldapValidator.verifyPassword(user.getUsername(), (String) authentication.getCredentials())) { throw new BadCredentialsException(); }
  }

  @Override
  protected UserDetail retrieveUser(String username, Authentication authentication)
      throws AuthenticationException {
    return userDetailService.loadDetail(username);
  }

  public void setUserDetailService(UserDetailService userDetailService) {
    this.userDetailService = userDetailService;
  }

  public void setLdapValidator(LdapValidator ldapValidator) {
    this.ldapValidator = ldapValidator;
  }

}
