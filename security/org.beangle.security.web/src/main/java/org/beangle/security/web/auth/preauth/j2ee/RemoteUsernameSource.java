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
package org.beangle.security.web.auth.preauth.j2ee;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.beangle.commons.lang.Option;
import org.beangle.commons.lang.Strings;
import org.beangle.security.web.auth.preauth.UsernameSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Source of the username supplied with pre-authenticated authentication request
 * as remote user header value. Optionally can strip prefix: "domain\\username"
 * -> "username", if <tt>stripPrefix</tt> property value is "true".
 */
public class RemoteUsernameSource implements UsernameSource {
  /**
   * Logger for this class and subclasses
   */
  protected final Logger logger = LoggerFactory.getLogger(this.getClass());

  private boolean stripPrefix = true;

  public Option<String> obtainUsername(HttpServletRequest request) {
    String username = null;
    Principal p = request.getUserPrincipal();
    if (null != p) username = p.getName();
    if (Strings.isEmpty(username)) username = request.getRemoteUser();
    if (null != username && isStripPrefix()) username = stripPrefix(username);
    if (null != username) logger.debug("Obtained username=[{}] from remote user", username);
    return Option.from(username);
  }

  private String stripPrefix(String userName) {
    if (Strings.isNotBlank(userName)) {
      int index = userName.lastIndexOf("\\");
      if (index != -1) userName = userName.substring(index + 1);
    }
    return userName;
  }

  /**
   * @return the stripPrefix
   */
  public boolean isStripPrefix() {
    return stripPrefix;
  }

  /**
   * @param stripPrefix the stripPrefix to set
   */
  public void setStripPrefix(boolean stripPrefix) {
    this.stripPrefix = stripPrefix;
  }
}
