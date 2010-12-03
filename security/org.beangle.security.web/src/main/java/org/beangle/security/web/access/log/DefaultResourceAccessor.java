/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.access.log;

import javax.servlet.http.HttpServletRequest;

import org.beangle.security.core.context.SecurityContextHolder;
import org.beangle.web.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultResourceAccessor implements ResourceAccessor {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected static Accesslog buildLog(HttpServletRequest request) {
		Accesslog log = new Accesslog();
		log.setUser(SecurityContextHolder.getContext().getAuthentication());
		log.setUri(RequestUtils.getRequestURI(request));
		log.setParams(request.getQueryString());
		return log;
	}

	public Accesslog beginAccess(HttpServletRequest request, long time) {
		Accesslog accesslog = buildLog(request);
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
