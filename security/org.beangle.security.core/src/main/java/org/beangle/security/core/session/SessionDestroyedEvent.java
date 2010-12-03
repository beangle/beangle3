/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session;

import org.beangle.security.core.context.SecurityContext;
import org.springframework.context.ApplicationEvent;

/**
 * Generic "session termination" event which indicates that a session
 * (potentially represented by a security context) has ended.
 * 
 * @author chaostone
 */
public abstract class SessionDestroyedEvent extends ApplicationEvent {

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
