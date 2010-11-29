/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.userdetail;

import org.beangle.commons.text.TextResource;
import org.beangle.security.auth.AccountExpiredException;
import org.beangle.security.auth.CredentialsExpiredException;
import org.beangle.security.auth.DisabledException;
import org.beangle.security.auth.LockedException;

/**
 * @author chaostone
 */
public class AccountStatusChecker implements UserDetailChecker {

	protected TextResource textResource;

	public void check(UserDetail user) {
		if (user.isAccountLocked()) { throw new LockedException(textResource.getText(
				"AbstractUserDetailsAuthenticationProvider.locked", "User account is locked"), user); }

		if (!user.isEnabled()) { throw new DisabledException(textResource.getText(
				"AbstractUserDetailsAuthenticationProvider.disabled", "User is disabled"), user); }

		if (user.isAccountExpired()) { throw new AccountExpiredException(textResource.getText(
				"AbstractUserDetailsAuthenticationProvider.expired", "User account has expired"),
				user); }

		if (user.isCredentialsExpired()) { throw new CredentialsExpiredException(
				textResource.getText(
						"AbstractUserDetailsAuthenticationProvider.credentialsExpired",
						"User credentials have expired"), user); }
	}

	public void setTextResource(TextResource textResource) {
		this.textResource = textResource;
	}

}
