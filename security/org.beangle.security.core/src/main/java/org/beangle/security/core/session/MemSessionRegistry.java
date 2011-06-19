/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.Validate;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.security.core.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;

public class MemSessionRegistry implements SessionRegistry, ApplicationListener<SessionDestroyedEvent> {

	protected static final Logger logger = LoggerFactory.getLogger(MemSessionRegistry.class);

	private SessionController controller;

	// <principal:Object,SessionIdSet>
	protected Map<Object, Set<String>> principals = new ConcurrentHashMap<Object, Set<String>>();

	// <sessionId:Object,OnlineActvities>
	protected Map<String, SessionInfo> sessionIds = new ConcurrentHashMap<String, SessionInfo>();

	public void afterPropertiesSet() throws Exception {
		Validate.notNull(controller, "controller must set");
		// 每两分钟执行一次
		new Timer("Beangle Session Cleaner", true).schedule(new SessionCleaner(this), new Date(), 1000 * 120);
	}

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

	public void register(Authentication auth, String sessionId) throws SessionException {
		SessionInfo existed = getSessionInfo(sessionId);
		Object principal = auth.getPrincipal();
		// 是否为重复注册
		if (null != existed && ObjectUtils.equals(existed.getAuthentication().getPrincipal(), principal)) return;
		// 争取名额
		if (!controller.onRegister(auth, sessionId, this)) throw new SessionException(
				"over max session limit");
		// 注销同会话的其它账户
		if (null != existed) {
			existed.appendRemark(" expired with replacement.");
			remove(sessionId);
		}
		// 新生
		SessionInfo newActivity = new SessionInfo(auth, sessionId);
		sessionIds.put(sessionId, newActivity);
		Set<String> sessionsUsedByPrincipal = principals.get(principal);
		if (sessionsUsedByPrincipal == null) {
			sessionsUsedByPrincipal = Collections.synchronizedSet(new HashSet<String>(4));
			principals.put(principal, sessionsUsedByPrincipal);
		}
		sessionsUsedByPrincipal.add(sessionId);
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
		controller.onLogout(info);
		return info;
	}

	// 当会话消失时，退出用户
	public void onApplicationEvent(SessionDestroyedEvent event) {
		remove(event.getId());
	}

	public void expire(String sessionId) {
		SessionInfo info = getSessionInfo(sessionId);
		if (null != info) info.expireNow();
	}

	public int count() {
		return sessionIds.size();
	}

	public void setController(SessionController controller) {
		this.controller = controller;
	}

	public SessionController getController() {
		return controller;
	}

}
