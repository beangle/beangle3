/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.access;

import javax.servlet.http.HttpServletRequest;

public interface ServletRequestAware {

	public HttpServletRequest getRequest();

	public void setRequest(HttpServletRequest request);

}
