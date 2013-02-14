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
package org.beangle.commons.web.session;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.beangle.commons.event.EventMulticaster;
import org.beangle.commons.lang.Assert;
import org.beangle.commons.web.context.ContainerUtils;

/**
 * Declared in web.xml as
 * 
 * <pre>
 * &lt;listener&gt;
 *     &lt;listener-class&gt;org.beangle.commons.web.session.HttpSessionEventPublisher&lt;/listener-class&gt;
 * &lt;/listener&gt;
 * </pre>
 * 
 * Publishes <code>HttpSessionApplicationEvent</code>s to the Bean Root Context.
 * Maps javax.servlet.http.HttpSessionListener.sessionCreated() to {@link HttpSessionCreationEvent}.
 * Maps javax.servlet.http.HttpSessionListener.sessionDestroyed() to
 * {@link HttpSessionDestroyedEvent}.
 */
public class HttpSessionEventPublisher implements HttpSessionListener {

  protected EventMulticaster eventMulticaster;

  /**
   * Handles the HttpSessionEvent by publishing a {@link HttpSessionCreationEvent} to the
   * application appContext.
   * 
   * @param event HttpSessionEvent passed in by the container
   */
  public void sessionCreated(HttpSessionEvent event) {
    if (null == eventMulticaster) {
      eventMulticaster = ContainerUtils.getContainer(event.getSession().getServletContext())
          .getBean(EventMulticaster.class).get();
      Assert.notNull(eventMulticaster);
    }
    eventMulticaster.multicast(new HttpSessionCreationEvent(event.getSession()));
  }

  /**
   * Handles the HttpSessionEvent by publishing a {@link HttpSessionDestroyedEvent} to the
   * application appContext.
   * 
   * @param event The HttpSessionEvent pass in by the container
   */
  public void sessionDestroyed(HttpSessionEvent event) {
    if (null != eventMulticaster) {
      eventMulticaster.multicast(new HttpSessionDestroyedEvent(event.getSession()));
    }
  }
}
