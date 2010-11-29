/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session;

import org.beangle.security.core.Authentication;

/**
 * Session limit controller
 * 
 * @author chaostone
 */
public interface SessionController {

	/**
	 * get max session for system
	 * 
	 * @return
	 */
	public int getMax();

	/**
	 * get count of session in system
	 * 
	 * @return
	 */
	public int getSessionCount();

	/**
	 * get max sessions for given auth
	 * 
	 * @param auth
	 * @return -1 or positive number
	 */
	public int getMaximumSessions(Authentication auth);
}
