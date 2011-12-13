/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.ldap.auth;

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

	private UserDetailService<Authentication> userDetailService;

	@Override
	protected void additionalAuthenticationChecks(UserDetail user, Authentication authentication)
			throws AuthenticationException {
		if (!ldapValidator.verifyPassword(user.getUsername(), (String) authentication.getCredentials())) { throw new BadLdapCredentialsException(); }
	}

	@Override
	protected UserDetail retrieveUser(String username, Authentication authentication)
			throws AuthenticationException {
		return userDetailService.loadDetail(authentication);
	}

	public void setUserDetailService(UserDetailService<Authentication> userDetailService) {
		this.userDetailService = userDetailService;
	}

	public void setLdapValidator(LdapValidator ldapValidator) {
		this.ldapValidator = ldapValidator;
	}

}
