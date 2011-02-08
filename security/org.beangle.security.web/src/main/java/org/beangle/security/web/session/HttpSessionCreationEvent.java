/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.session;

import javax.servlet.http.HttpSession;

import org.beangle.security.core.session.SessionCreationEvent;

/**
 * Published by the {@link HttpSessionEventPublisher} when a HttpSession is
 * destroyed by the container
 */
public class HttpSessionCreationEvent extends SessionCreationEvent {

	private static final long serialVersionUID = 1L;

	public HttpSessionCreationEvent(HttpSession o) {
		super(o);
	}
}
