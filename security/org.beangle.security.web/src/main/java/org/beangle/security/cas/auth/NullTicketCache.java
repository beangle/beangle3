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
package org.beangle.security.cas.auth;

/**
 * Implementation of @link {@link StatelessTicketCache} that has no backing
 * cache. Useful in instances where storing of tickets for stateless session
 * management is not required.
 * <p>
 * This is the default StatelessTicketCache of the @link {@link CasAuthenticationProvider} to
 * eliminate the unnecessary dependency on EhCache that applications have even if they are not using
 * the stateless session management.
 * 
 * @author chaostone
 * @see CasAuthenticationProvider
 */
public final class NullTicketCache implements StatelessTicketCache {

  /**
   * @return null since we are not storing any tickets.
   */
  public CasAuthentication get(final String serviceTicket) {
    return null;
  }

  /**
   * This is a no-op since we are not storing tickets.
   */
  public void put(final CasAuthentication token) {
    // nothing to do
  }

  /**
   * This is a no-op since we are not storing tickets.
   */
  public void remove(final CasAuthentication token) {
    // nothing to do
  }

  /**
   * This is a no-op since we are not storing tickets.
   */
  public void remove(final String serviceTicket) {
    // nothing to do
  }
}
