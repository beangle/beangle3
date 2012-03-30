/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.beangle.collection.Order;
import org.beangle.dao.query.builder.OqlBuilder;
import org.beangle.ems.security.Role;
import org.beangle.ems.security.session.model.SessionProfileBean;
import org.beangle.ems.security.session.service.SessionProfileService;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.security.core.session.SessionRegistry;
import org.beangle.security.core.session.category.SessionStat;
import org.beangle.security.core.session.impl.AccessLog;
import org.beangle.security.web.session.model.SessioninfoBean;

/**
 * 系统在线用户管理
 * 
 * @author chaostone
 */
public class MonitorAction extends SecurityActionSupport {

	private SessionRegistry sessionRegistry;

	private SessionProfileService categoryProfileService;

	public String profiles() {
		List<SessionProfileBean> profiles = entityDao.getAll(SessionProfileBean.class);
		put("profiles", profiles);
		put("roles", entityDao.search(OqlBuilder.from(Role.class, "g").where("g.parent is null")
				.orderBy("g.code")));
		return forward();
	}

	public String index() {
		String orderBy = get("orderBy");
		if (StringUtils.isEmpty(orderBy)) {
			orderBy = "sessioninfo.loginAt desc";
		}
		OqlBuilder<SessioninfoBean> builder = OqlBuilder.from(SessioninfoBean.class, "sessioninfo");
		populateConditions(builder);
		builder.orderBy(get(Order.ORDER_STR)).limit(getPageLimit());
		put("sessioninfos", entityDao.search(builder));
		OqlBuilder<SessionStat> statBuilder = OqlBuilder.from(SessionStat.class, "stat");
		put("sessionStats", entityDao.search(statBuilder));
		return forward();
	}

	/**
	 * 保存设置
	 */
	public String saveProfile() {
		List<SessionProfileBean> categories = entityDao.getAll(SessionProfileBean.class);
		for (final SessionProfileBean profile : categories) {
			Long roleId = profile.getRole().getId();
			Integer max = getInteger("max_" + roleId);
			Integer maxSessions = getInteger("maxSessions_" + roleId);
			Integer inactiveInterval = getInteger("inactiveInterval_" + roleId);
			if (null != max && null != maxSessions && null != inactiveInterval) {
				profile.setCapacity(max);
				profile.setUserMaxSessions(maxSessions);
				profile.setInactiveInterval(inactiveInterval);
			}
		}
		categoryProfileService.saveOrUpdate(categories);
		return redirect("profiles", "info.save.success");
	}

	public String invalidate() {
		String[] sessionIds = getIds(String.class, "sessioninfo");
		String mySessionId = ServletActionContext.getRequest().getSession().getId();
		boolean killed = getBool("kill");
		int success = 0;
		for (String sessionId : sessionIds) {
			if (mySessionId.equals(sessionId)) continue;
			sessionRegistry.expire(sessionId);
			success++;
		}
		addFlashMessage(killed ? "security.info.session.kill" : "security.info.session.expire", success);
		return redirect("index");
	}

	/**
	 * 访问记录
	 */
	public String accesslogs() {
		OqlBuilder<AccessLog> builder = OqlBuilder.from(AccessLog.class, "accessLog");
		populateConditions(builder);
		String orderBy = get("orderBy");
		if (StringUtils.isEmpty(orderBy)) {
			orderBy = "accessLog.endAt-accessLog.beginAt desc";
		}
		builder.orderBy(orderBy).limit(getPageLimit());
		put("accessLogs", entityDao.search(builder));
		return forward();
	}

	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}

	public void setCategoryProfileService(SessionProfileService categoryProfileService) {
		this.categoryProfileService = categoryProfileService;
	}

}
