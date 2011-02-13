/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.access.log;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.Validate;
import org.beangle.commons.collection.CollectUtils;
import org.springframework.beans.factory.InitializingBean;

public class CachedResourceAccessor extends DefaultResourceAccessor implements InitializingBean {

	private List<Accesslog> accesslogs = CollectUtils.newArrayList();

	// 3000 milliseconds
	private long minDuration = 3000;

	// default 500
	private int cacheSize = 500;

	public void afterPropertiesSet() throws Exception {
		Validate.isTrue(minDuration > 0, "minDuration must greater then 0");
		Validate.isTrue(cacheSize > 100, "cacheSize should greate then 100");
	}

	public Accesslog beginAccess(HttpServletRequest request, long time) {
		Accesslog accesslog = buildLog(request);
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

	public Long getMinDuration() {
		return minDuration;
	}

	public void setMinDuration(Long minDuration) {
		this.minDuration = minDuration;
	}

	public Integer getCacheSize() {
		return cacheSize;
	}

	public void setCacheSize(Integer cacheSize) {
		this.cacheSize = cacheSize;
	}
}
