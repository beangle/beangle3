/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.core.userdetail;

import org.beangle.commons.text.i18n.TextResource;
import org.beangle.commons.text.i18n.impl.NullTextResource;
import org.beangle.security.auth.AccountExpiredException;
import org.beangle.security.auth.CredentialsExpiredException;
import org.beangle.security.auth.DisabledException;
import org.beangle.security.auth.LockedException;

/**
 * @author chaostone
 */
public class AccountStatusChecker implements UserDetailChecker {

  protected TextResource textResource = new NullTextResource();

  public void check(UserDetail user) {
    if (user.isAccountLocked()) { throw new LockedException(textResource.getText(
        "AbstractUserDetailsAuthenticationProvider.locked", "User account is locked"), user); }

    if (!user.isEnabled()) { throw new DisabledException(textResource.getText(
        "AbstractUserDetailsAuthenticationProvider.disabled", "User is disabled"), user); }

    if (user.isAccountExpired()) { throw new AccountExpiredException(textResource.getText(
        "AbstractUserDetailsAuthenticationProvider.expired", "User account has expired"), user); }

    if (user.isCredentialsExpired()) { throw new CredentialsExpiredException(textResource.getText(
        "AbstractUserDetailsAuthenticationProvider.credentialsExpired", "User credentials have expired"),
        user); }
  }

  public void setTextResource(TextResource textResource) {
    this.textResource = textResource;
  }

}
