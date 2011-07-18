/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session;

import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.Validate;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.session.category.SimpleSessioninfoBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationListener;

public class DbSessionRegistry extends BaseServiceImpl implements SessionRegistry,
		ApplicationListener<SessionDestroyedEvent>, InitializingBean {

	protected static final Logger logger = LoggerFactory.getLogger(DbSessionRegistry.class);

	private SessionController controller;

	private SessioninfoBuilder sessioninfoBuilder = new SimpleSessioninfoBuilder();

	public void afterPropertiesSet() throws Exception {
		Validate.notNull(controller, "controller must set");
		Validate.notNull(sessioninfoBuilder, "sessioninfoBuilder must set");
	}

	public List<Sessioninfo> getAll() {
		@SuppressWarnings("unchecked")
		OqlBuilder<Sessioninfo> builder = OqlBuilder.from(sessioninfoBuilder.getSessioninfoClass(), "info");
		builder.where("info.serverName=:server", controller.getServerName());
		return entityDao.search(builder);
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

}
