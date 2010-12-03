/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.session.category;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.session.SessionDestroyedEvent;
import org.beangle.security.core.session.SessionException;
import org.beangle.security.core.session.SessionInfo;
import org.beangle.security.core.session.SessionRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationListener;

/**
 * @author chaostone
 * @version $Id: CategorySessionRegistryImpl.java Nov 21, 2010 3:20:36 PM
 *          chaostone $
 */
public class CategorySessionRegistry implements SessionRegistry, InitializingBean,
		ApplicationListener<SessionDestroyedEvent> {

	private final Logger logger = LoggerFactory.getLogger(CategorySessionRegistry.class);

	private SessionRegistry innerRegistry;

	private CategorySessionController sessionController;

	public void afterPropertiesSet() throws Exception {
		Validate.notNull(sessionController, "sessionController must set");
		Validate.notNull(innerRegistry, "innerRegistry must set");
	}

	// 当会话消失时，退出用户
	public void onApplicationEvent(SessionDestroyedEvent event) {
		remove(event.getId());
	}

	private CategoryPrincipal categoryPrincipal(SessionInfo info) {
		Object principal = info.getAuthentication().getPrincipal();
		Validate.isTrue(principal instanceof CategoryPrincipal,
				"principal should be instance of CategoryPrincipal");
		return (CategoryPrincipal) principal;
	}

	/**
	 * 注册用户
	 */
	public void register(String sessionId, Authentication auth) {
		CategoryPrincipal principal = (CategoryPrincipal) auth.getPrincipal();
		SessionInfo existed = innerRegistry.getSessionInfo(sessionId);
		// 原先没有的要占座
		if (null == existed && !sessionController.reserve(principal.getCategory(), sessionId)) {
			throw new SessionException("over max " + sessionController.getMax());
		} else {
			innerRegistry.register(sessionId, auth);
		}
	}

	public SessionInfo remove(String sessionId) {
		SessionInfo info = innerRegistry.remove(sessionId);
		if (null != info) {
			sessionController.left(categoryPrincipal(info).getCategory());
		}
		return info;
	}

	/**
	 * stat sessioninfo
	 * 
	 * @return
	 */
	public List<LimitProfile> statSessionInfo() {
		int all = sessionController.getSessionCount();
		if (all == innerRegistry.count()) {
			return sessionController.getProfiles();
		} else {
			logger.info("start calculate...registry {} profile {}", innerRegistry.count(), all);
			Map<Object, LimitProfile> newProfileMap = CollectUtils.newHashMap();
			for (LimitProfile profile : sessionController.getProfiles()) {
				LimitProfile newProfile = new LimitProfile();
				newProfile.setCategory(profile.getCategory());
				newProfileMap.put(profile.getCategory(), newProfile);
			}
			List<SessionInfo> infos = innerRegistry.getAll();
			for (final SessionInfo info : infos) {
				LimitProfile profile = newProfileMap.get(categoryPrincipal(info).getCategory());
				profile.reserve();
			}
			return CollectUtils.newArrayList(newProfileMap.values());
		}
	}

	public void setInnerRegistry(SessionRegistry sessionRegistry) {
		this.innerRegistry = sessionRegistry;
	}

	public List<SessionInfo> getAll() {
		return innerRegistry.getAll();
	}

	public void setSessionController(CategorySessionController sessionController) {
		this.sessionController = sessionController;
	}

	public List<SessionInfo> getSessionInfos(Object principal, boolean includeExpiredSessions) {
		return innerRegistry.getSessionInfos(principal, includeExpiredSessions);
	}

	public SessionInfo getSessionInfo(String sessionId) {
		return innerRegistry.getSessionInfo(sessionId);
	}

	public boolean isRegisted(Object principal) {
		return innerRegistry.isRegisted(principal);
	}

	public void refreshLastRequest(String sessionId) {
		innerRegistry.refreshLastRequest(sessionId);
	}

	public int count() {
		return innerRegistry.count();
	}

	public int getMax() {
		return sessionController.getMax();
	}

	public List<LimitProfile> getProfiles() {
		return sessionController.getProfiles();
	}

	public LimitProfile getProfile(Object category) {
		return sessionController.getProfile(category);
	}

}
