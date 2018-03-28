/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.core.userdetail;

import org.beangle.commons.text.i18n.TextResource;
import org.beangle.commons.text.i18n.impl.NullTextResource;
import org.beangle.security.authc.AccountExpiredException;
import org.beangle.security.authc.CredentialsExpiredException;
import org.beangle.security.authc.DisabledException;
import org.beangle.security.authc.LockedException;

/**
 * @author chaostone
 */
public class AccountStatusChecker implements UserDetailChecker {

  protected TextResource textResource = new NullTextResource();

  public void check(Account user) {
    if (user.isAccountLocked()) { throw new LockedException(textResource.getText(
        "AbstractUserDetailsAuthenticationProvider.locked", "User account is locked"), user); }

    if (!user.isEnabled()) { throw new DisabledException(textResource.getText(
        "AbstractUserDetailsAuthenticationProvider.disabled", "User is disabled"), user); }

    if (user.isAccountExpired()) { throw new AccountExpiredException(textResource.getText(
        "AbstractUserDetailsAuthenticationProvider.expired", "User account has expired"), user); }

    if (user.isCredentialExpired()) { throw new CredentialsExpiredException(textResource.getText(
        "AbstractUserDetailsAuthenticationProvider.credentialsExpired", "User credentials have expired"),
        user); }
  }

  public void setTextResource(TextResource textResource) {
    this.textResource = textResource;
  }

}
