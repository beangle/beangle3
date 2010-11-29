/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.access.log;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.beangle.commons.collection.CollectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CachedResourceAccessor implements ResourceAccessor {
	private static final Logger logger = LoggerFactory.getLogger(CachedResourceAccessor.class);

	private List<Accesslog> accesslogs = CollectUtils.newArrayList();

	private int cacheSize = AccessConfig.getInstance().getCacheSize();

	public Accesslog beginAccess(HttpServletRequest request, long time) {
		Accesslog accesslog = AccesslogFactory.getLog(request);
		accesslog.setBeginAt(time);
		synchronized (accesslogs) {
			accesslogs.add(accesslog);
			if (accesslogs.size() >= cacheSize) {
				shrink(accesslogs);
				if (accesslogs.size() >= cacheSize) {
					flush(accesslogs);
				}
			}
		}
		return accesslog;
	}

	public void endAccess(Accesslog log, long time) {
		log.setEndAt(time);
	}

	public void finish() {
		if (!accesslogs.isEmpty()) {
			flush(accesslogs);
		}
	}

	public void start() {

	}

	public List<Accesslog> getAccessLogs() {
		return accesslogs;
	}

	protected void shrink(List<Accesslog> accesslogs) {
		long minDuration = AccessConfig.getInstance().getMinDuration().longValue();
		for (Iterator<Accesslog> iterator = accesslogs.iterator(); iterator.hasNext();) {
			Accesslog accesslog = (Accesslog) iterator.next();
			if (accesslog.getDuration() < minDuration) {
				iterator.remove();
			}
		}
	}

	protected void flush(List<Accesslog> accesslogs) {
		for (final Accesslog accesslog : accesslogs) {
			logger.info(accesslog.toString());
		}
		accesslogs.clear();
	}
}
