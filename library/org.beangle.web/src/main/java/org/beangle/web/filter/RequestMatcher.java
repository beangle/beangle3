/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.web.filter;

import javax.servlet.http.HttpServletRequest;

/**
 * Simple strategy to match an <tt>HttpServletRequest</tt>.
 * 
 * @author chaostone
 */
public interface RequestMatcher {

	/**
	 * Decides whether the rule implemented by the strategy matches the supplied
	 * request.
	 * 
	 * @param request
	 *            the request to check for a match
	 * @return true if the request matches, false otherwise
	 */
	boolean matches(HttpServletRequest request);

}
