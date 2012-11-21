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
package org.beangle.security.core.session;

import org.beangle.commons.context.event.Event;
import org.beangle.security.core.context.SecurityContext;

/**
 * Generic "session termination" event which indicates that a session
 * (potentially represented by a security context) has ended.
 * 
 * @author chaostone
 */
public abstract class SessionDestroyedEvent extends Event {

  private static final long serialVersionUID = 1L;

  public SessionDestroyedEvent(Object source) {
    super(source);
  }

  /**
   * Provides the <tt>SecurityContext</tt> under which the session was
   * running.
   * 
   * @return the <tt>SecurityContext</tt> associated with the session, or null
   *         if there is no context.
   */
  public abstract SecurityContext getSecurityContext();

  /**
   * @return the identifier associated with the destroyed session.
   */
  public abstract String getId();
}
