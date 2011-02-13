/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.auth.preauth;

import org.beangle.security.auth.UsernamePasswordAuthentication;
import org.beangle.security.auth.dao.AbstractUserDetailAuthenticationProvider;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.AuthenticationException;
import org.beangle.security.core.userdetail.UserDetail;
import org.beangle.security.core.userdetail.UserDetailService;

/**
 * Provider preauthed authentication detail.
 * 
 * @author chaostone
 * @version $Id: PreauthUserDetailProvider.java Oct 29, 2010 8:24:15 AM
 *          chaostone $
 */
@SuppressWarnings("rawtypes")
public class PreauthUserDetailProvider extends AbstractUserDetailAuthenticationProvider {

	private UserDetailService userDetailService;

	@Override
	protected void additionalAuthenticationChecks(UserDetail userDetails,
			UsernamePasswordAuthentication authentication) throws AuthenticationException {
		// preauthed token,ignore password validation;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected UserDetail retrieveUser(String username, UsernamePasswordAuthentication authentication)
			throws AuthenticationException {
		return userDetailService.loadDetail(authentication);
	}

	public boolean supports(Class<? extends Authentication> authentication) {
		return (PreauthAuthentication.class.isAssignableFrom(authentication));
	}

	public void setUserDetailService(UserDetailService userDetailService) {
		this.userDetailService = userDetailService;
	}
}
