/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session.category;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.time.StopWatch;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.security.core.session.SessionRegistry;
import org.beangle.security.core.session.Sessioninfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author chaostone
 * @version $Id: SessionCleaner.java Jun 6, 2011 12:21:24 PM chaostone $
 */
public class SessioninfoCleaner extends BaseServiceImpl implements InitializingBean {

	private SessionRegistry sessionRegistry;

	// 默认5分钟清理一次
	private int cleanInterval = 1000 * 300;

	public void afterPropertiesSet() throws Exception {
		Validate.notNull(sessionRegistry);
		SessionCleanerTask sessionCleanerTask = new SessionCleanerTask(sessionRegistry);
		sessionCleanerTask.setEntityDao(entityDao);
		new Timer("Beangle Session Cleaner", true).schedule(sessionCleanerTask, new Date(), cleanInterval);
	}

	public SessionRegistry getSessionRegistry() {
		return sessionRegistry;
	}

	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}

	public int getCleanInterval() {
		return cleanInterval;
	}

	public void setCleanInterval(int cleanInterval) {
		this.cleanInterval = cleanInterval;
	}

}

class SessionCleanerTask extends TimerTask {

	private final Logger logger = LoggerFactory.getLogger(SessioninfoCleaner.class);

	private final SessionRegistry registry;

	private EntityDao entityDao;

	/** 默认 过期时间 30分钟 */
	private int expiredTime = 30;

	public SessionCleanerTask(SessionRegistry registry) {
		super();
		this.registry = registry;
	}

	public SessionCleanerTask(SessionRegistry registry, int expiredTime) {
		this.registry = registry;
		this.expiredTime = expiredTime;
	}

	@Override
	public void run() {
		StopWatch watch = new StopWatch();
		watch.start();
		logger.debug("clean up expired or over maxOnlineTime session start ...");
		Calendar calendar = Calendar.getInstance();
		calendar.roll(Calendar.MINUTE, -expiredTime);
		@SuppressWarnings("unchecked")
		OqlBuilder<Sessioninfo> builder = OqlBuilder.from(registry.getSessioninfoBuilder()
				.getSessioninfoClass(), "info");
		builder.where("info.serverName=:server and info.lastAccessAt<:givenTime", registry.getController()
				.getServerName(), calendar.getTime());
		List<Sessioninfo> activities = entityDao.search(builder);
		int removed = 0;
		for (Sessioninfo activity : activities) {
			registry.remove(activity.getId());
			removed++;
		}
		if (removed > 0 || watch.getTime() > 50) {
			logger.info("removed {} expired or over maxOnlineTime sessions in {} ms", removed,
					watch.getTime());
		}
		registry.getController().stat();
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
