/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.Validate;
import org.beangle.collection.CollectUtils;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.context.SecurityContextHolder;
import org.beangle.security.core.session.AccessLogger;
import org.beangle.security.core.session.SessionController;
import org.beangle.security.core.session.SessionDestroyedEvent;
import org.beangle.security.core.session.SessionException;
import org.beangle.security.core.session.SessionRegistry;
import org.beangle.security.core.session.SessionStatus;
import org.beangle.security.core.session.Sessioninfo;
import org.beangle.security.core.session.SessioninfoBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationListener;

public class DbSessionRegistry extends BaseServiceImpl implements SessionRegistry,
		ApplicationListener<SessionDestroyedEvent>, InitializingBean {

	protected static final Logger logger = LoggerFactory.getLogger(DbSessionRegistry.class);

	private SessionController controller;

	private SessioninfoBuilder sessioninfoBuilder = new SimpleSessioninfoBuilder();

	Map<String, AccessEntry> entries = CollectUtils.newConcurrentHashMap();

	private boolean enableLog;

	private AccessLogger accessLogger;

	long updatedAt = System.currentTimeMillis();

	private int updatedInterval = 5 * 60 * 1000;// 5分钟

	public void afterPropertiesSet() throws Exception {
		Validate.notNull(controller, "controller must set");
		Validate.notNull(sessioninfoBuilder, "sessioninfoBuilder must set");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean isRegisted(String principal) {
		OqlBuilder builder = OqlBuilder.from(sessioninfoBuilder.getSessioninfoClass(), "info");
		builder.where("info.username=:username and info.expiredAt is null", principal).select("info.id")
				.cacheable();
		return !entityDao.search(builder).isEmpty();
	}

	public List<Sessioninfo> getSessioninfos(String principal, boolean includeExpiredSessions) {
		@SuppressWarnings("unchecked")
		OqlBuilder<Sessioninfo> builder = OqlBuilder.from(sessioninfoBuilder.getSessioninfoClass(), "info");
		builder.where("info.username=:username", principal);
		if (!includeExpiredSessions) {
			builder.where("info.expiredAt is null");
		}
		return entityDao.search(builder);
	}

	public Sessioninfo getSessioninfo(String sessionId) {
		@SuppressWarnings("unchecked")
		OqlBuilder<Sessioninfo> builder = OqlBuilder.from(sessioninfoBuilder.getSessioninfoClass(), "info");
		builder.where("info.id=:sessionid", sessionId);
		List<Sessioninfo> infos = entityDao.search(builder);
		if (infos.isEmpty()) return null;
		else return infos.get(0);
	}

	public SessionStatus getSessionStatus(String sessionid) {
		@SuppressWarnings("unchecked")
		OqlBuilder<SessionStatus> builder = OqlBuilder.from(sessioninfoBuilder.getSessioninfoClass(), "info");
		builder.where("info.id=:sessionid", sessionid)
				.select("new org.beangle.security.core.session.SessionStatus(info.username,info.expiredAt)")
				.cacheable();
		List<SessionStatus> infos = entityDao.search(builder);
		if (infos.isEmpty()) return null;
		else return infos.get(0);
	}

	public void register(Authentication auth, String sessionId) throws SessionException {
		SessionStatus existed = getSessionStatus(sessionId);
		String principal = auth.getName();
		// 是否为重复注册
		if (null != existed && ObjectUtils.equals(existed.getUsername(), principal)) return;
		// 争取名额
		boolean success = controller.onRegister(auth, sessionId, this);
		if (!success) throw new SessionException("security.OvermaxSession");
		// 注销同会话的其它账户
		if (null != existed) {
			remove(sessionId, " expired with replacement.");
		}
		// 新生
		entityDao.save(sessioninfoBuilder.build(auth, controller.getServerName(), sessionId));
	}

	public Sessioninfo remove(String sessionId) {
		return remove(sessionId, null);
	}

	private Sessioninfo remove(String sessionId, String reason) {
		Sessioninfo info = getSessioninfo(sessionId);
		if (null == info) {
			return null;
		} else {
			// FIXME not in a transcation
			if (null != reason) info.addRemark(reason);
			entityDao.remove(info);
			controller.onLogout(info);
			entries.remove(info.getId());
			Object sessioninfoLog = sessioninfoBuilder.buildLog(info);
			if (null != sessioninfoLog) {
				entityDao.save(sessioninfoLog);
			}
			logger.debug("Remove session {} for {}", sessionId, info.getUsername());
			return info;
		}
	}

	public void expire(String sessionId) {
		Sessioninfo info = getSessioninfo(sessionId);
		if (null != info) {
			controller.onLogout(info);
			info.expireNow();
			entityDao.saveOrUpdate(info);
		}
	}

	// 当会话消失时，退出用户
	public void onApplicationEvent(SessionDestroyedEvent event) {
		remove(event.getId());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int count() {
		OqlBuilder builder = OqlBuilder.from(Sessioninfo.class, "info");
		builder.select("count(id)");
		List<Number> numbers = entityDao.search(builder);
		if (numbers.isEmpty()) return 0;
		else return (numbers.get(0)).intValue();
	}

	public void setController(SessionController controller) {
		this.controller = controller;
	}

	public SessionController getController() {
		return controller;
	}

	public void setSessioninfoBuilder(SessioninfoBuilder sessioninfoBuilder) {
		this.sessioninfoBuilder = sessioninfoBuilder;
	}

	public SessioninfoBuilder getSessioninfoBuilder() {
		return sessioninfoBuilder;
	}

	public void access(String sessionid, String resource, long accessAt) {
		if (accessAt - updatedAt > updatedInterval) {
			new Thread(new AccessUpdaterTask(this)).start();
		}
		AccessEntry entry = entries.get(sessionid);
		if (null == entry) entries.put(sessionid, new AccessEntry(resource, accessAt));
		else entry.access(resource, accessAt);

	}

	public void endAccess(String sessionid, String resource, long endAt) {
		if (enableLog) {
			AccessEntry entry = entries.get(sessionid);
			accessLogger.log(sessionid, SecurityContextHolder.getContext().getAuthentication().getName(),
					resource, entry.accessAt, endAt);
		}
	}

	public void setAccessLogger(AccessLogger accessLogger) {
		this.accessLogger = accessLogger;
	}

	public boolean isEnableLog() {
		return enableLog;
	}

	public void setEnableLog(boolean enableLog) {
		this.enableLog = enableLog;
	}

	public String getResource(String sessionid) {
		AccessEntry entry = entries.get(sessionid);
		if (null == entry) return null;
		else return entry.resource;
	}

}

class AccessUpdaterTask implements Runnable {

	DbSessionRegistry registry;

	public AccessUpdaterTask(DbSessionRegistry registry) {
		super();
		this.registry = registry;
	}

	public void run() {
		EntityDao entityDao = registry.getEntityDao();
		long updatedAt = registry.updatedAt;
		List<Object[]> arguments = CollectUtils.newArrayList();
		for (Map.Entry<String, AccessEntry> entry : registry.entries.entrySet()) {
			AccessEntry accessEntry = entry.getValue();
			if (accessEntry.accessAt > updatedAt) {
				arguments.add(new Object[] { new Date(entry.getValue().accessAt), entry.getKey() });
			}
		}
		if (!arguments.isEmpty()) {
			entityDao.executeUpdateHqlRepeatly("update "
					+ registry.getSessioninfoBuilder().getSessioninfoClass().getName()
					+ " info set info.lastAccessAt=? where info.id=?", arguments);
		}
		registry.updatedAt = System.currentTimeMillis();
	}

}

class AccessEntry {
	String resource;
	long accessAt;

	public AccessEntry(String resource, long accessMillis) {
		super();
		this.resource = resource;
		this.accessAt = accessMillis;
	}

	public void access(String resource, long accessMillis) {
		this.resource = resource;
		this.accessAt = accessMillis;
	}
}
