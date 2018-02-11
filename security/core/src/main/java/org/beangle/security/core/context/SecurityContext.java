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
package org.beangle.security.core.context;

import java.io.Serializable;

import org.beangle.security.core.Authentication;

/**
 * Interface defining the minimum security information associated with the
 * current thread of execution.
 * <p>
 * The security context is stored in a {@link SecurityContextHolder}.
 * </p>
 *
 * @author chaostone
 * @version $Id: SecurityContext.java 2217 2007-10-27 00:45:30Z $
 */
public interface SecurityContext extends Serializable {

  /**
   * Obtains the currently authenticated principal, or an authentication
   * request token.
   *
   * @return the <code>Authentication</code> or <code>null</code> if no
   *         authentication information is available
   */
  Authentication getAuthentication();

  /**
   * Changes the currently authenticated principal, or removes the
   * authentication information.
   *
   * @param authentication
   *          the new <code>Authentication</code> token, or <code>null</code> if no further
   *          authentication information
   *          should be stored
   */
  void setAuthentication(Authentication authentication);
}
