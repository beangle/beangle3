/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.access.log;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.beangle.security.core.session.impl.AccessLog;

public interface ResourceAccessor {

	public AccessLog beginAccess(HttpServletRequest request, Date date);

	public void endAccess(AccessLog log, Date date);

	public void start();

	public void finish();
}
