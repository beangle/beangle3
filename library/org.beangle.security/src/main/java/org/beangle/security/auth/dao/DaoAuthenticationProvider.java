/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.auth.dao;

import org.beangle.security.auth.BadCredentialsException;
import org.beangle.security.auth.encoding.PasswordEncoder;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.AuthenticationException;
import org.beangle.security.core.userdetail.UserDetail;
import org.beangle.security.core.userdetail.UserDetailService;

public class DaoAuthenticationProvider extends AbstractUserDetailAuthenticationProvider {

	private PasswordEncoder passwordEncoder;

	private UserDetailService userDetailService;

	@Override
	protected void additionalAuthenticationChecks(UserDetail user, Authentication auth)
			throws AuthenticationException {
		if (!passwordEncoder.isPasswordValid(user.getPassword(), (String) auth.getCredentials())) { throw new BadCredentialsException(); }
	}

	@Override
	protected UserDetail retrieveUser(String username, Authentication authentication)
			throws AuthenticationException {
		return userDetailService.loadDetail(username);
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public UserDetailService getUserDetailService() {
		return userDetailService;
	}

	public void setUserDetailService(UserDetailService userDetailService) {
		this.userDetailService = userDetailService;
	}
}
