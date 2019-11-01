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
package org.beangle.security.ids.session;

import org.beangle.commons.bean.Initializing;
import org.beangle.commons.cache.Cache;
import org.beangle.commons.cache.CacheManager;
import org.beangle.commons.cache.caffeine.CaffeineCacheManager;
import org.beangle.commons.lang.Option;
import org.beangle.security.core.session.Session;
import org.beangle.security.core.session.SessionRepo;
import org.beangle.security.ids.util.SessionDaemon;

import java.time.Instant;
import java.util.Random;

abstract class CacheSessionRepo implements SessionRepo, Initializing {
  private CacheManager cacheManager = new CaffeineCacheManager();

  private Cache<String, Session> sessions;

  private final int accessDelaySeconds = genDelay(60, 120);

  protected AccessReporter reporter;

  /**
   * interval (5 min) report heartbeat.
   */
  int flushInterval = 5 * 60;

  @Override
  public void init() {
    sessions = cacheManager.getCache("sessions", String.class, Session.class);
    reporter = new AccessReporter(sessions, this);
    SessionDaemon.start(flushInterval, reporter);
  }

  private int genDelay(int minDelay, int maxDelay) {
    return (int) (((new Random()).nextDouble() * (maxDelay - minDelay)) + minDelay);
  }

  @Override
  public Session get(String sessionId) {
    if (null == sessionId) return null;
    Option<Session> data = sessions.get(sessionId);
    if (data.isEmpty()) {
      Option<Session> newData = getInternal(sessionId);
      if (!newData.isEmpty()) {
        sessions.put(sessionId, newData.get());
        return newData.get();
      } else {
        return null;
      }
    } else {
      return data.get();
    }
  }

  @Override
  public Session access(String sessionId, Instant accessAt) {
    Session s = get(sessionId);
    if (null == s) {
      return null;
    }
    Long elapse = s.access(accessAt);
    if (elapse > accessDelaySeconds) {
      reporter.addSessionId(s.getId());
      return s;
    } else {
      if (elapse == -1) {
        this.expire(s.getId());
        return null;
      } else {
        return s;
      }
    }
  }

  protected abstract Option<Session> getInternal(String sessionId);

  abstract boolean flush(Session session);

  abstract void expire(String sid);

  public void setCacheManager(CacheManager cacheManager) {
    this.cacheManager = cacheManager;
  }

  public void setFlushInterval(int flushInterval) {
    this.flushInterval = flushInterval;
  }

}
