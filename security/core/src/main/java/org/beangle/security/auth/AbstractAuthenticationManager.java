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
package org.beangle.security.auth;

import org.beangle.security.core.Authentication;
import org.beangle.security.core.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractAuthenticationManager implements AuthenticationManager {

  private boolean clearExtraInfo = false;
  protected Logger logger = LoggerFactory.getLogger(AbstractAuthenticationManager.class);

  public final Authentication authenticate(Authentication authRequest) throws AuthenticationException {
    try {
      Authentication auth = doAuthentication(authRequest);
      logger.debug("Successfully Authenticated: {}", auth);
      return auth;
    } catch (AuthenticationException e) {
      e.setAuthentication(authRequest);
      if (clearExtraInfo) e.clearExtraInfo();
      throw e;
    }
  }

  protected abstract Authentication doAuthentication(Authentication authentication)
      throws AuthenticationException;

  public void setClearExtraInfo(boolean clearExtraInfo) {
    this.clearExtraInfo = clearExtraInfo;
  }
}
