/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.security.core.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemSessionRegistry implements SessionRegistry {

	protected static final Logger logger = LoggerFactory.getLogger(MemSessionRegistry.class);

	// <principal:Object,SessionIdSet>
	protected Map<Object, Set<String>> principals = new ConcurrentHashMap<Object, Set<String>>();

	// <sessionId:Object,OnlineActvities>
	protected Map<String, SessionInfo> sessionIds = new ConcurrentHashMap<String, SessionInfo>();

	public List<SessionInfo> getAll() {
		return CollectUtils.newArrayList(sessionIds.values());
	}

	public boolean isRegisted(Object principal) {
		Set<String> sessionsUsedByPrincipal = principals.get(principal);
		return (null != sessionsUsedByPrincipal && !sessionsUsedByPrincipal.isEmpty());
	}

	public List<SessionInfo> getSessionInfos(Object principal, boolean includeExpiredSessions) {
		Set<String> sessionsUsedByPrincipal = principals.get(principal);
		List<SessionInfo> list = CollectUtils.newArrayList();
		if (null == sessionsUsedByPrincipal) { return list; }
		synchronized (sessionsUsedByPrincipal) {
			for (final String sessionId : sessionsUsedByPrincipal) {
				SessionInfo activity = getSessionInfo(sessionId);
				if (activity == null) {
					continue;
				}
				if (includeExpiredSessions || !activity.isExpired()) {
					list.add(activity);
				}
			}
		}

		return list;
	}

	public SessionInfo getSessionInfo(String sessionId) {
		return sessionIds.get(sessionId);
	}

	public void refreshLastRequest(String sessionId) {
		SessionInfo info = getSessionInfo(sessionId);
		if (info != null) {
			info.refreshLastRequest();
		}
	}

	private void register(String sessionId, Object principal, SessionInfo newActivity) {
		SessionInfo existed = getSessionInfo(sessionId);
		if (null != existed) {
			existed.appendRemark(" expired with replacement.");
			remove(sessionId);
		}
		sessionIds.put(sessionId, newActivity);
		Set<String> sessionsUsedByPrincipal = principals.get(principal);
		if (sessionsUsedByPrincipal == null) {
			sessionsUsedByPrincipal = Collections.synchronizedSet(new HashSet<String>(4));
			principals.put(principal, sessionsUsedByPrincipal);
		}
		sessionsUsedByPrincipal.add(sessionId);
	}

	public void register(String sessionId, Authentication authentication) {
		register(sessionId, authentication.getPrincipal(), new SessionInfo(sessionId,
				authentication));
	}

	public SessionInfo remove(String sessionId) {
		SessionInfo info = getSessionInfo(sessionId);
		if (null == info) { return null; }
		sessionIds.remove(sessionId);
		Object principal = info.getAuthentication().getPrincipal();
		logger.debug("Remove session {} for {}", sessionId, principal);
		Set<String> sessionsUsedByPrincipal = principals.get(principal);
		if (null != sessionsUsedByPrincipal) {
			synchronized (sessionsUsedByPrincipal) {
				sessionsUsedByPrincipal.remove(sessionId);
				// No need to keep object in principals Map anymore
				if (sessionsUsedByPrincipal.size() == 0) {
					principals.remove(principal);
					logger.debug("Remove principal {} from registry", principal);
				}
			}
		}
		return info;
	}

	public int count() {
		return sessionIds.size();
	}

}
