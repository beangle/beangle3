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
package org.beangle.security.cas.auth;

/**
 * Caches CAS service tickets and CAS proxy tickets for stateless connections.
 * <p>
 * When a service ticket or proxy ticket is validated against the CAS server, it is unable to be
 * used again. Most types of callers are stateful and are associated with a given
 * <code>HttpSession</code>. This allows the affirmative CAS validation outcome to be stored in the
 * <code>HttpSession</code>, meaning the removal of the ticket from the CAS server is not an issue.
 * </p>
 * <P>
 * Stateless callers, such as remoting protocols, cannot take advantage of <code>HttpSession</code>.
 * If the stateless caller is located a significant network distance from the CAS server, acquiring
 * a fresh service ticket or proxy ticket for each invocation would be expensive.
 * </p>
 * <P>
 * To avoid this issue with stateless callers, it is expected stateless callers will obtain a single
 * service ticket or proxy ticket, and then present this same ticket to the Beangle Security secured
 * application on each occasion. As no <code>HttpSession</code> is available for such callers, the
 * affirmative CAS validation outcome cannot be stored in this location.
 * </p>
 * <P>
 * The <code>StatelessTicketCache</code> enables the service tickets and proxy tickets belonging to
 * stateless callers to be placed in a cache. This in-memory cache stores the
 * <code>CasAuthentication</code>, effectively providing the same capability as a
 * <code>HttpSession</code> with the ticket identifier being the key rather than a session
 * identifier.
 * </p>
 * <P>
 * Implementations should provide a reasonable timeout on stored entries, such that the stateless
 * caller are not required to unnecessarily acquire fresh CAS service tickets or proxy tickets.
 * </p>
 * 
 * @author chaostone
 */
public interface StatelessTicketCache {

  /**
   * Retrieves the <code>CasAuthentication</code> associated with the
   * specified ticket.
   * <P>
   * If not found, returns a <code>null</code><code>CasAuthentication</code>.
   * </p>
   * 
   * @return the fully populated authentication token
   */
  CasAuthentication get(String serviceTicket);

  /**
   * Adds the specified <code>CasAuthentication</code> to the cache.
   * <P>
   * The {@link CasAuthentication#getCredentials()} method is used to retrieve the service ticket
   * number.
   * </p>
   * 
   * @param token
   *          to be added to the cache
   */
  void put(CasAuthentication token);

  /**
   * Removes the specified ticket from the cache, as per {@link #remove(String)}.
   * <P>
   * Implementations should use {@link CasAuthentication#getCredentials()} to obtain the ticket and
   * then delegate to to the {@link #remove(String)} method.
   * </p>
   * 
   * @param token
   *          to be removed
   */
  void remove(CasAuthentication token);

  /**
   * Removes the specified ticket from the cache, meaning that future calls
   * will require a new service ticket.
   * <P>
   * This is in case applications wish to provide a session termination capability for their
   * stateless clients.
   * </p>
   * 
   * @param serviceTicket
   *          to be removed
   */
  void remove(String serviceTicket);
}
