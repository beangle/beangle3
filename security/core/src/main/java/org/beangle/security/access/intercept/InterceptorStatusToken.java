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
package org.beangle.security.access.intercept;

import org.beangle.security.core.Authentication;

/**
 * A return object received by {@link AbstractSecurityInterceptor} subclasses.
 * <p>
 * This class reflects the status of the security interception, so that the final call to
 * {@link org.beangle.security.access.intercept.AbstractSecurityInterceptor#afterInvocation(InterceptorStatusToken, Object)}
 * can tidy up correctly.
 */
public class InterceptorStatusToken {
  private Authentication authentication;
  private Object secureObject;
  private boolean contextHolderRefreshRequired;

  public InterceptorStatusToken(Authentication authentication, boolean contextHolderRefreshRequired,
      Object secureObject) {
    this.authentication = authentication;
    this.contextHolderRefreshRequired = contextHolderRefreshRequired;
    this.secureObject = secureObject;
  }

  public Authentication getAuthentication() {
    return authentication;
  }

  public Object getSecureObject() {
    return secureObject;
  }

  public boolean isContextHolderRefreshRequired() {
    return contextHolderRefreshRequired;
  }
}
