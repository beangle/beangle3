/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session;

import java.util.List;
import java.util.TimerTask;

import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chaostone
 * @version $Id: SessionCleaner.java Jun 6, 2011 12:21:24 PM chaostone $
 */
public class SessionCleaner extends TimerTask {

	private final Logger logger = LoggerFactory.getLogger(SessionCleaner.class);

	private final SessionRegistry registry;

	/**
	 * 最大过期时间
	 */
	private final long maxOnlineTime;

	// 最大过期时间为12小时
	public SessionCleaner(SessionRegistry registry) {
		this(registry, 1000 * 60 * 60 * 12);
	}

	public SessionCleaner(SessionRegistry registry, long maxOnlineTime) {
		super();
		this.registry = registry;
		this.maxOnlineTime = maxOnlineTime;
	}

	protected boolean shouldRemove(SessionInfo info) {
		return info.isExpired() || (info.getOnlineTime() >= maxOnlineTime);
	}

	@Override
	public void run() {
		StopWatch watch = new StopWatch();
		watch.start();
		logger.debug("clean up expired or over maxOnlineTime session start ...");
		List<SessionInfo> infos = registry.getAll();
		int removed = 0;
		for (SessionInfo info : infos) {
			if (shouldRemove(info)) {
				registry.remove(info.getSessionid());
				removed++;
			}
		}
		if (removed > 0 || watch.getTime() > 50) {
			logger.info("removed {} expired or over maxOnlineTime sessions in {} ms", removed,
					watch.getTime());
		}
	}

}
