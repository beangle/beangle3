/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.session;

import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpSession;

/**
 * Parent class for published HttpSession events
 */
public abstract class HttpSessionApplicationEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;

	/**
	 * Base constructor for all subclasses must have an HttpSession
	 * 
	 * @param httpSession
	 *            The session to carry as the event source.
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
