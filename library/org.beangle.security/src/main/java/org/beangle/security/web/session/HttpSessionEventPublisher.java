/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.session;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.beangle.model.persist.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Declared in web.xml as
 * 
 * <pre>
 * &lt;listener&gt;
 *     &lt;listener-class&gt;org.beangle.security.ui.session.HttpSessionEventPublisher&lt;/listener-class&gt;
 * &lt;/listener&gt;
 * </pre>
 * 
 * Publishes <code>HttpSessionApplicationEvent</code>s to the Spring Root
 * WebApplicationContext. Maps
 * javax.servlet.http.HttpSessionListener.sessionCreated() to {@link HttpSessionCreationEvent}. Maps
 * javax.servlet.http.HttpSessionListener.sessionDestroyed() to {@link HttpSessionDestroyedEvent}.
 */
public class HttpSessionEventPublisher  extends BaseServiceImpl implements HttpSessionListener {

	private static final Logger logger = LoggerFactory.getLogger(HttpSessionEventPublisher.class);

	/**
	 * Handles the HttpSessionEvent by publishing a {@link HttpSessionCreationEvent} to the
	 * application appContext.
	 * 
	 * @param event
	 *            HttpSessionEvent passed in by the container
	 */
	public void sessionCreated(HttpSessionEvent event) {
		HttpSessionCreationEvent e = new HttpSessionCreationEvent(event.getSession());
		logger.debug("Publishing event: {}", e);
		publish(e);
	}

	/**
	 * Handles the HttpSessionEvent by publishing a {@link HttpSessionDestroyedEvent} to the
	 * application appContext.
	 * 
	 * @param event
	 *            The HttpSessionEvent pass in by the container
	 */
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSessionDestroyedEvent e = new HttpSessionDestroyedEvent(event.getSession());
		logger.debug("Publishing event: {}", e);
		publish(e);
	}
}
