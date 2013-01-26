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

import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.lang.time.Stopwatch;
import org.beangle.security.core.session.SessionStatus;
import org.beangle.security.core.session.SessionStatusCache;
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

  private DbSessionRegistry registry;

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

  public DbSessionCacheSyncDaemon(DbSessionRegistry registry) {
    super();
    this.registry = registry;
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
    SessionStatusCache cache = registry.getCache();

    for (String id : cache.getIds()) {
      SessionStatus status = registry.getCache().get(id);
      if (null == status) continue;
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
        EntityDao entityDao = registry.getEntityDao();
        int[] updates = entityDao
            .executeUpdateRepeatly(
                "update "
                    + registry.getSessioninfoTypename()
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

  public void setRegistry(DbSessionRegistry registry) {
    this.registry = registry;
  }

}
