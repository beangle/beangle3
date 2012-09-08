/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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

  /**
   * 根据用户确定单个用户的最大会话数
   * 
   * @param auth
   * @return -1 or positive number
   */
  protected abstract int getMaxSessions(Authentication auth);
}
