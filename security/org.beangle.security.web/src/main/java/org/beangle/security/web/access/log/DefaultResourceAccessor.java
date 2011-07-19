/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.access.log;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.beangle.security.core.context.SecurityContextHolder;
import org.beangle.security.core.session.impl.AccessLog;
import org.beangle.web.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultResourceAccessor implements ResourceAccessor {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected static AccessLog buildLog(HttpServletRequest request) {
		AccessLog log = new AccessLog();
		log.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		log.setResource(RequestUtils.getServletPath(request));
		//log.setParams(request.getQueryString());
		return log;
	}

	public AccessLog beginAccess(HttpServletRequest request, Date date) {
		AccessLog accessLog = buildLog(request);
		accessLog.setBeginAt(date);
		if (logger.isDebugEnabled()) {
			logger.debug(accessLog.toString());
		}
		return accessLog;
	}

	public void endAccess(AccessLog accessLog, Date date) {
		accessLog.setEndAt(date);
		if (logger.isDebugEnabled()) {
			logger.debug(accessLog.toString());
		}
	}

	public void finish() {

	}

	public void start() {

	}
}
