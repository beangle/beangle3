/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session;

import java.util.List;

import org.beangle.security.core.Authentication;

/**
 * @author chaostone
 * @version $Id: AbstractSessionController.java Jun 16, 2011 8:00:10 PM chaostone $
 */
public abstract class AbstractSessionController implements SessionController {

	public boolean onRegister(Authentication auth, String sessionId, SessionRegistry registry)
			throws SessionException {
		if (!allocate(auth, sessionId)) return false;
		processSamePrincipal(auth, sessionId, registry);
		return true;
	}

	private void processSamePrincipal(Authentication auth, String sessionId, SessionRegistry registry)
			throws SessionException {
		Object principal = auth.getPrincipal();
		List<SessionInfo> sessions = registry.getSessionInfos(principal, false);
		int sessionCount = 0;
		if (sessions != null) sessionCount = sessions.size();

		int allowableSessions = getMaxSessions(auth);
		if (sessionCount < allowableSessions) {
			return;
		} else if (allowableSessions == -1) {
			return;
		} else if (sessionCount == allowableSessions) {
			for (int i = 0; i < sessionCount; i++) {
				if ((sessions.get(i)).getSessionid().equals(sessionId)) { return; }
			}
		}
		// Determine least recently used session, and mark it for invalidation
		SessionInfo leastRecentlyUsed = null;
		for (int i = 0; i < sessions.size(); i++) {
			if ((leastRecentlyUsed == null)
					|| sessions.get(i).getLastAccessAt().before(leastRecentlyUsed.getLastAccessAt())) {
				leastRecentlyUsed = (SessionInfo) sessions.get(i);
			}
		}
		leastRecentlyUsed.expireNow();
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
