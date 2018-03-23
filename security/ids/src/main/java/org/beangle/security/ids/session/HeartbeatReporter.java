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

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.beangle.commons.cache.Cache;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Option;
import org.beangle.security.core.session.Session;
import org.beangle.security.ids.util.Task;

/**
 * Report heartbeat every 5 min.
 */
class HeartbeatReporter implements Task {

  private final Cache<String, Session> sessions;

  private final CacheSessionRepo repo;

  public HeartbeatReporter(Cache<String, Session> sessions, CacheSessionRepo repo) {
    super();
    this.sessions = sessions;
    this.repo = repo;
  }

  private Instant lastReportAt = Instant.now();

  private ConcurrentHashMap<String, Boolean> sessionIds = new ConcurrentHashMap<String, Boolean>();

  public void addSessionId(String sessionId) {
    sessionIds.put(sessionId, true);
  }

  @Override
  public void run() {
    Instant last = lastReportAt;
    lastReportAt = Instant.now();
    List<String> expired = CollectUtils.newArrayList();
    Set<String> keys = sessionIds.keySet();
    for (String sessionId : keys) {
      Option<Session> rs = sessions.get(sessionId);
      if (rs.isEmpty()) {
        expired.add(sessionId);
      } else {
        Session s = rs.get();
        if (s.getLastAccessAt().isAfter(last) && !repo.heartbeat(s)) {
          expired.add(sessionId);
        }
      }
    }
    for (String e : expired) {
      sessionIds.remove(e);
      sessions.evict(e);
    }
  }
}
