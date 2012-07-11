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

import org.apache.commons.lang3.time.StopWatch;
import org.beangle.commons.bean.Initializing;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Dates;
import org.beangle.security.core.session.SessionRegistry;
import org.beangle.security.core.session.Sessioninfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chaostone
 * @version $Id: SessionCleaner.java Jun 6, 2011 12:21:24 PM chaostone $
 */
public class SessioninfoCleaner extends BaseServiceImpl implements Initializing {

  private SessionRegistry sessionRegistry;

  // 默认5分钟清理一次
  private int cleanInterval = 1000 * 300;

  public void init() throws Exception {
    if (null != sessionRegistry) {
      SessionCleanerTask sessionCleanerTask = new SessionCleanerTask(sessionRegistry);
      sessionCleanerTask.setEntityDao(entityDao);
      new Timer("Beangle Session Cleaner", true).schedule(sessionCleanerTask, new Date(), cleanInterval);
    }
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
    @SuppressWarnings("unchecked")
    OqlBuilder<Sessioninfo> builder = OqlBuilder.from(registry.getSessioninfoBuilder().getSessioninfoClass(),
        "info");
    builder.where("info.lastAccessAt<:givenTime", Dates.rollMinutes(calendar.getTime(), -expiredTime));
    List<Sessioninfo> infos = entityDao.search(builder);
    int removed = 0;
    for (Sessioninfo info : infos) {
      registry.remove(info.getId());
      removed++;
    }
    if (removed > 0 || watch.getTime() > 50) {
      logger.info("removed {} expired sessions in {} ms", removed, watch.getTime());
    }
    registry.getController().stat();
  }

  public void setEntityDao(EntityDao entityDao) {
    this.entityDao = entityDao;
  }

}
