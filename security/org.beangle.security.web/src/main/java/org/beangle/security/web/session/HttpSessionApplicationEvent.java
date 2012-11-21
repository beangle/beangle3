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
package org.beangle.security.web.session;

import javax.servlet.http.HttpSession;

import org.beangle.commons.context.event.Event;

/**
 * Parent class for published HttpSession events
 */
public abstract class HttpSessionApplicationEvent extends Event {
  private static final long serialVersionUID = 1L;

  /**
   * Base constructor for all subclasses must have an HttpSession
   * 
   * @param httpSession
   *          The session to carry as the event source.
   */
  public HttpSessionApplicationEvent(HttpSession httpSession) {
    super(httpSession);
  }

  /**
   * Get the HttpSession that is the cause of the event
   * 
   * @return HttpSession instance
   */
  public HttpSession getSession() {
    return (HttpSession) getSource();
  }
}
