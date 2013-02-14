/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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

import org.beangle.commons.lang.Assert;
import org.beangle.commons.lang.Objects;
import org.beangle.commons.lang.Option;
import org.beangle.security.core.Authentication;

/**
 * @author chaostone
 * @version $Id: UsernameAliveChecker.java Nov 7, 2010 9:48:50 PM chaostone $
 */
public class UsernameAliveChecker implements AuthenticationAliveChecker {
  private UsernameSource usernameSource;

  /**
   * @param usernameSource
   *          the usernameSource to set
   */
  public void setUsernameSource(UsernameSource usernameSource) {
    Assert.notNull(usernameSource, "usernameSource must be specified");
    this.usernameSource = usernameSource;
  }

  public boolean check(Authentication auth, HttpServletRequest request) {
    Option<String> newUsername = usernameSource.obtainUsername(request);
    return Objects.equals(newUsername.orNull(), auth.getName());
  }

}
