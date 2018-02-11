/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.blueprint.session.service;

import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.beangle.commons.cache.Cache;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.lang.Option;
import org.beangle.commons.lang.time.Stopwatch;
import org.beangle.security.core.session.SessionStatus;
import org.beangle.security.core.session.Sessioninfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Session status cache Synchronizer
 *
 * @author chaostone
 * @since 3.1.0
 */
public class DbSessionCacheSyncDaemon extends TimerTask {

  protected static final Logger logger = LoggerFactory.getLogger(DbSessionCacheSyncDaemon.class);

  private EntityDao entityDao;

  private Cache<String, SessionStatus> cache;

  private Class<? extends Sessioninfo> sessioninfoType;

  /**
   * Session status idle time(default 1 min).
   */
  private int idleTime = 60 * 1000;

  /**
   * Default synchronize interval(10 sec) for update access time.
   */
  private int interval = 10 * 1000;
  /**
   * Last synchronize time.
   */
  private long lastSyncTime = System.currentTimeMillis();

  public DbSessionCacheSyncDaemon() {
    super();
  }

  public DbSessionCacheSyncDaemon(EntityDao entityDao, Cache<String, SessionStatus> cache,
      Class<? extends Sessioninfo> sessioninfoType) {
    super();
    this.entityDao = entityDao;
    this.cache = cache;
    this.sessioninfoType = sessioninfoType;
  }

  @Override
  public void run() {
    sync();
  }

  /**
   * Synchronize last access time and expired at.
   * <ul>
   * <li>Update sessioninfo's lastAccessAt</li>
   * <li>Evict session status when 1) id not exists 2) expired 3) lastAccessAt updated by other
   * cache.</li>
   * </ul>
   */
  public void sync() {
    Stopwatch watch = new Stopwatch(true);
    long now = System.currentTimeMillis();

    List<Object[]> arguments = CollectUtils.newArrayList();

    for (String id : cache.keys()) {
      Option<SessionStatus> statusOp = cache.get(id);
      if (statusOp.isEmpty()) continue;
      SessionStatus status = statusOp.get();
      // session status is idle toolong,then evict it.
      if (now - status.getLastAccessedTime() >= idleTime) {
        cache.evict(id);
        continue;
      }

      if (status.getLastAccessedTime() > lastSyncTime)
        arguments.add(new Object[] { id, new Date(status.getLastAccessedTime()) });
    }
    if (!arguments.isEmpty()) {
      try {
        int[] updates = entityDao
            .executeUpdateRepeatly(
                "update "
                    + sessioninfoType.getName()
                    + " info set info.lastAccessAt=?2 where info.id=?1 and info.lastAccessAt < ?2 and info.expiredAt is null",
                arguments);

        // evict local cache
        for (int i = 0; i < updates.length; i++)
          if (0 == updates[i]) cache.evict((String) arguments.get(i)[0]);
      } catch (Exception e) {
        logger.error("Beangle session update last accessed time failure.", e);
      }
      logger.debug("Sync {} session last access time in {}", arguments.size(), watch);
    }
    // update last update timestamp.
    lastSyncTime = now;
  }

  public void setIdleTime(int idleTime) {
    this.idleTime = idleTime;
  }

  public int getInterval() {
    return interval;
  }

  public void setInterval(int interval) {
    this.interval = interval;
  }

  public void setEntityDao(EntityDao entityDao) {
    this.entityDao = entityDao;
  }

  public void setCache(Cache<String, SessionStatus> cache) {
    this.cache = cache;
  }

  public void setSessioninfoType(Class<? extends Sessioninfo> sessioninfoType) {
    this.sessioninfoType = sessioninfoType;
  }

}
