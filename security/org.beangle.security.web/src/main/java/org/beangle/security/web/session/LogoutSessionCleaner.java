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

import org.beangle.commons.context.event.Event;
import org.beangle.commons.context.event.EventListener;
import org.beangle.commons.web.session.HttpSessionDestroyedEvent;
import org.beangle.security.core.session.SessionRegistry;

/**
 * LogoutSessionCleaner
 * 
 * @author chaostone
 * @since 3.0.1
 */
public class LogoutSessionCleaner implements EventListener<HttpSessionDestroyedEvent> {

  SessionRegistry sessionRegistry;

  // 当会话消失时，退出用户
  public void onEvent(HttpSessionDestroyedEvent event) {
    sessionRegistry.remove(event.getSession().getId());
  }

  public boolean supportsEventType(Class<? extends Event> eventType) {
    return HttpSessionDestroyedEvent.class.isAssignableFrom(eventType);
  }

  public boolean supportsSourceType(Class<?> sourceType) {
    return true;
  }

  public void setSessionRegistry(SessionRegistry sessionRegistry) {
    this.sessionRegistry = sessionRegistry;
  }

}
