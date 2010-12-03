/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.access.log;

import javax.servlet.http.HttpServletRequest;

public interface ResourceAccessor {

	public Accesslog beginAccess(HttpServletRequest request, long time);

	public void endAccess(Accesslog log, long time);

	public void start();

	public void finish();
}
