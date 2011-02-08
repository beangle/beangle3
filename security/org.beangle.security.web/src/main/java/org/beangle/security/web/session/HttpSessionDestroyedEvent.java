/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.session;

import javax.servlet.http.HttpSession;

import org.beangle.security.core.context.SecurityContext;
import org.beangle.security.core.session.SessionDestroyedEvent;
import org.beangle.security.web.context.HttpSessionContextIntegrationFilter;

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
				HttpSessionContextIntegrationFilter.SECURITY_CONTEXT_KEY);
	}

	@Override
	public String getId() {
		return getSession().getId();
	}
}
