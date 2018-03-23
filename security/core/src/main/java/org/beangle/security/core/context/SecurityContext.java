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

import java.security.Principal;

import org.beangle.commons.lang.Assert;
import org.beangle.security.core.session.Session;

/**
 * Interface defining the minimum security information associated with the
 * current thread of execution.
 * <p>
 * The security context is stored in a {@link ThreadLocalHolder}.
 * </p>
 *
 * @author chaostone
 * @version $Id: SecurityContext.java 2217 2007-10-27 00:45:30Z $
 */
public class SecurityContext {

  private static ThreadLocal<Session> holder = new ThreadLocal<Session>();

  /**
   * Explicitly clears the context value from the current thread.
   */
  public static void clear() {
    holder.set(null);
  }

  public static Session getSession() {
    return holder.get();
  }

  public static void setSession(Session session) {
    Assert.notNull(session, "Only non-null SecurityContext instances are permitted");
    holder.set(session);
  }

  public Principal getPrincipal() {
    return (null == holder.get()) ? null : holder.get().getPrincipal();
  }

}
