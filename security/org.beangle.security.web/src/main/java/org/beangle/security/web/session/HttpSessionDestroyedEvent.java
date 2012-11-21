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

import org.beangle.security.core.context.SecurityContext;
import org.beangle.security.core.session.SessionDestroyedEvent;
import org.beangle.security.web.context.HttpSessionContextFilter;

/**
 * Published by the {@link HttpSessionEventPublisher} when a HttpSession is
 * created in the container
 */
public class HttpSessionDestroyedEvent extends SessionDestroyedEvent {

  private static final long serialVersionUID = 1L;

  public HttpSessionDestroyedEvent(HttpSession o) {
    super(o);
  }

  public HttpSession getSession() {
    return (HttpSession) getSource();
  }

  @Override
  public SecurityContext getSecurityContext() {
    return (SecurityContext) getSession().getAttribute(
        HttpSessionContextFilter.SECURITY_CONTEXT_KEY);
  }

  @Override
  public String getId() {
    return getSession().getId();
  }
}
