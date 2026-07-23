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

import org.beangle.commons.security.Request;
import org.beangle.security.core.session.Session;
import org.beangle.security.core.userdetail.Account;
import org.beangle.security.core.userdetail.Profile;

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

  private static ThreadLocal<SecurityContext> holder = new ThreadLocal<SecurityContext>();

  private final Session session;

  private final Request request;

  private final Profile profile;

  private final String runAs;

  public SecurityContext(Session session, Request request, Profile profile, String runAs) {
    super();
    this.session = session;
    this.request = request;
    this.profile = profile;
    this.runAs = runAs;
  }

  /**
   * Explicitly clears the context value from the current thread.
   */
  public static void clear() {
    holder.set(null);
  }

  public static void set(SecurityContext context) {
    holder.set(context);
  }

  public static SecurityContext get() {
    return holder.get();
  }

  public static ThreadLocal<SecurityContext> getHolder() {
    return holder;
  }

  public static void setHolder(ThreadLocal<SecurityContext> holder) {
    SecurityContext.holder = holder;
  }

  public Session getSession() {
    return session;
  }

  public Request getRequest() {
    return request;
  }

  public String getUser() {
    if (isRoot() && null != runAs) {
      return runAs;
    } else {
      if (null == session) return "anonymous";
      else return session.getPrincipal().getName();
    }
  }

  public boolean isValid() {
    return null != session;
  }

  public boolean isRoot() {
    return null != session && ((Account) session.getPrincipal()).isRoot() && runAs == null;
  }

  public String getRunAs() {
    return runAs;
  }

  public Profile getProfile() {
    return profile;
  }
}
