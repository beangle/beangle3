/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.blueprint.session.service;

import java.util.Calendar;
import java.util.List;
import java.util.TimerTask;

import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Dates;
import org.beangle.commons.lang.time.Stopwatch;
import org.beangle.security.core.session.Sessioninfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Database session registry cleanup daemon
 * 
 * @author chaostone
 * @since 3.1.0
 */
public class DbSessionCleanupDaemon extends TimerTask {

  protected static final Logger logger = LoggerFactory.getLogger(DbSessionCleanupDaemon.class);

  private DbSessionRegistry registry;

  /** 默认 过期时间 30分钟 */
  private int expiredTime = 30;

  /**
   * Default interval(5 minutes) for clean up expired session infos.
   */
  private int cleanInterval = 5 * 60 * 1000;

  public DbSessionCleanupDaemon() {
  }

  public DbSessionCleanupDaemon(DbSessionRegistry registry) {
    this.registry = registry;
  }

  @Override
  public void run() {
    // FIXME multi registry clean up
    cleanup();
  }

  public int getCleanInterval() {
    return cleanInterval;
  }

  public void setCleanInterval(int cleanInterval) {
    this.cleanInterval = cleanInterval;
  }

  public int getExpiredTime() {
    return expiredTime;
  }

  public void setExpiredTime(int expiredTime) {
    this.expiredTime = expiredTime;
  }

  /**
   * Check expired or will expire session(now-lastAccessAt>=expiredTime),clean them
   */
  public void cleanup() {
    Stopwatch watch = new Stopwatch().start();
    logger.debug("clean up expired or over expired time session start ...");
    Calendar calendar = Calendar.getInstance();
    try {
      OqlBuilder<? extends Sessioninfo> builder = OqlBuilder.from(registry.getSessioninfoTypename(), "info");
      builder.where(
          "info.lastAccessAt is null or info.lastAccessAt<:givenTime or info.expiredAt is not null",
          Dates.rollMinutes(calendar.getTime(), -expiredTime));
      List<? extends Sessioninfo> infos = registry.getEntityDao().search(builder);
      int removed = 0;
      for (Sessioninfo info : infos) {
        registry.remove(info.getId());
        removed++;
      }
      if (removed > 0) logger.debug("removed {} expired sessions in {}", removed, watch);
      registry.getController().stat();
    } catch (Exception e) {
      logger.error("Beangle session cleanup failure.", e);
    }
  }

  public void setRegistry(DbSessionRegistry registry) {
    this.registry = registry;
  }
  
}
