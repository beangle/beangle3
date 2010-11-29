/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.access.log;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultResourceAccessor implements ResourceAccessor {
	private static final Logger logger = LoggerFactory.getLogger(DefaultResourceAccessor.class);

	public Accesslog beginAccess(HttpServletRequest request, long time) {
		Accesslog accesslog = AccesslogFactory.getLog(request);
		accesslog.setBeginAt(time);
		if (logger.isDebugEnabled()) {
			logger.debug(accesslog.toString());
		}
		return accesslog;
	}

	public void endAccess(Accesslog accesslog, long time) {
		accesslog.setEndAt(time);
		if (logger.isDebugEnabled()) {
			logger.debug(accesslog.toString());
		}
	}

	public void finish() {

	}

	public void start() {

	}
}
