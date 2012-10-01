/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.session;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.beangle.commons.context.event.EventMulticaster;
import org.beangle.commons.web.spring.ContextLoader;
import org.springframework.util.Assert;

/**
 * Declared in web.xml as
 * 
 * <pre>
 * &lt;listener&gt;
 *     &lt;listener-class&gt;org.beangle.security.web.session.HttpSessionEventPublisher&lt;/listener-class&gt;
 * &lt;/listener&gt;
 * </pre>
 * 
 * Publishes <code>HttpSessionApplicationEvent</code>s to the Spring Root
 * WebApplicationContext. Maps
 * javax.servlet.http.HttpSessionListener.sessionCreated() to {@link HttpSessionCreationEvent}. Maps
 * javax.servlet.http.HttpSessionListener.sessionDestroyed() to {@link HttpSessionDestroyedEvent}.
 */
public class HttpSessionEventPublisher implements HttpSessionListener {

  protected EventMulticaster eventMulticaster;

  /**
   * Handles the HttpSessionEvent by publishing a {@link HttpSessionCreationEvent} to the
   * application appContext.
   * 
   * @param event
   *          HttpSessionEvent passed in by the container
   */
  public void sessionCreated(HttpSessionEvent event) {
    if (null == eventMulticaster) {
      eventMulticaster = ContextLoader.getContext(event.getSession().getServletContext()).getBean(
          EventMulticaster.class);
      Assert.notNull(eventMulticaster);
    }
    eventMulticaster.multicast(new HttpSessionCreationEvent(event.getSession()));
  }

  /**
   * Handles the HttpSessionEvent by publishing a {@link HttpSessionDestroyedEvent} to the
   * application appContext.
   * 
   * @param event
   *          The HttpSessionEvent pass in by the container
   */
  public void sessionDestroyed(HttpSessionEvent event) {
    if (null != eventMulticaster) {
      eventMulticaster.multicast(new HttpSessionDestroyedEvent(event.getSession()));
    }
  }
}
