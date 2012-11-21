/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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
package org.beangle.security.web.auth.preauth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.commons.lang.Assert;
import org.beangle.commons.lang.Option;
import org.beangle.commons.lang.Strings;

/**
 * Flexible pre-authenticated filter which obtains username and other values
 * supplied in the request (in headers, or in cookies, or in
 * HttpServletRequest.getRemoteUser()), for use with SSO systems.
 * <p>
 * Has additional <tt>usernameSource</tt> property.
 * <p>
 * Will create Authentication object (and attach it to the SecurityContextHolder), if such object
 * does not exist yet.
 * <p>
 * As with most pre-authenticated scenarios, it is essential that the external authentication system
 * is set up correctly as this filter does no authentication whatsoever. All the protection is
 * assumed to be provided externally and if this filter is included inappropriately in a
 * configuration, it would be possible to assume the identity of a user merely by setting the
 * correct header name. This also means it should not be used in combination with other Spring
 * Security authentication mechanisms such as form login, as this would imply there was a means of
 * bypassing the external system which would be risky.
 * <p>
 */
public class UsernamePreauthFilter extends AbstractPreauthFilter {
  private UsernameSource usernameSource;

  protected void initFilterBean() {
    super.initFilterBean();
    Assert.notNull(usernameSource, "usernameSource must be set");
  }

  /**
   * @param usernameSource the usernameSource to set
   */
  public void setUsernameSource(UsernameSource usernameSource) {
    Assert.notNull(usernameSource, "usernameSource must be specified");
    this.usernameSource = usernameSource;
  }

  @Override
  protected PreauthAuthentication getPreauthAuthentication(HttpServletRequest request,
      HttpServletResponse response) {
    Option<String> username = usernameSource.obtainUsername(request);
    if (username.isDefined() && Strings.isNotBlank(username.get())) return new PreauthAuthentication(
        username.get());
    else return null;

  }
}
