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
package org.beangle.security.core.session;

import java.util.List;

import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.security.core.Authentication;

/**
 * @author chaostone
 * @version $Id: AbstractSessionController.java Jun 16, 2011 8:00:10 PM chaostone $
 */
public abstract class AbstractSessionController extends BaseServiceImpl implements SessionController {

  public boolean onRegister(Authentication auth, String sessionId, SessionRegistry registry)
      throws SessionException {
    List<Sessioninfo> sessions = registry.getSessioninfos(auth.getName(), false);
    int sessionCount = 0;
    if (sessions != null) sessionCount = sessions.size();

    if (sessionCount <= 0) return allocate(auth, sessionId);

    boolean allocated = false;
    int allowableSessions = getMaxSessions(auth);
    if (sessionCount < allowableSessions || allowableSessions == -1) {
      allocated = allocate(auth, sessionId);
    }

    // Determine least recently used session, and mark it for invalidation
    if (!allocated) {
      Sessioninfo leastRecentlyUsed = null;
      for (int i = 0; i < sessions.size(); i++) {
        if ((leastRecentlyUsed == null)
            || sessions.get(i).getLoginAt().before(leastRecentlyUsed.getLoginAt())) {
          leastRecentlyUsed = sessions.get(i);
        }
      }
      if (null != leastRecentlyUsed) {
        registry.expire(leastRecentlyUsed.getId());
        allocated = true;
      }
    }
    return allocated;
  }

  /**
   * 试图为该用户保留一个登录空间
   * 
   * @param auth
   * @param sessionId
   * @return 如果成功返回true,否则false
   */
  protected abstract boolean allocate(Authentication auth, String sessionId);
}
