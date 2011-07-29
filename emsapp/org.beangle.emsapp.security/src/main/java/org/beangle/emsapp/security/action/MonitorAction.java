/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.beangle.commons.collection.Order;
import org.beangle.ems.security.session.model.GroupProfileBean;
import org.beangle.ems.security.session.service.GroupProfileService;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.query.builder.OqlBuilder;
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

	private GroupProfileService categoryProfileService;

	public String profiles() {
		put("categoryProfiles", entityDao.getAll(GroupProfileBean.class));
		return forward();
	}

	public String index() {
		String orderBy = get("orderBy");
		if (StringUtils.isEmpty(orderBy)) {
			orderBy = "sessioninfo.loginAt desc";
		}
		OqlBuilder<SessioninfoBean> builder = OqlBuilder.from(SessioninfoBean.class, "sessioninfo");
		populateConditions(builder);
		builder.where("sessioninfo.serverName=:server", sessionRegistry.getController().getServerName());
		builder.orderBy(get(Order.ORDER_STR)).limit(getPageLimit());
		put("sessioninfos", entityDao.search(builder));
		OqlBuilder<SessionStat> statBuilder = OqlBuilder.from(SessionStat.class, "stat");
		statBuilder.where("stat.serverName=:server", sessionRegistry.getController().getServerName());
		put("sessionStats", entityDao.search(statBuilder));
		return forward();
	}

	/**
	 * 保存设置
	 */
	public String saveProfile() {
		OqlBuilder<GroupProfileBean> builder = OqlBuilder.from(GroupProfileBean.class, "p");
		builder.where("p.sessionProfile.name=:serverName", sessionRegistry.getController().getServerName());
		List<GroupProfileBean> categories = entityDao.getAll(GroupProfileBean.class);
		for (final GroupProfileBean profile : categories) {
			Long groupId = profile.getGroup().getId();
			Integer max = getInteger("max_" + groupId);
			Integer maxSessions = getInteger("maxSessions_" + groupId);
			Integer inactiveInterval = getInteger("inactiveInterval_" + groupId);
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
		String[] sessionIds = getEntityIds("sessioninfo", String.class);
		String mySessionId = ServletActionContext.getRequest().getSession().getId();
		boolean killed = getBool("kill");
		int success = 0;
		if (null != sessionIds) {
			for (String sessionId : sessionIds) {
				if (mySessionId.equals(sessionId)) continue;
				sessionRegistry.expire(sessionId);
				success++;
			}
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

	public void setCategoryProfileService(GroupProfileService categoryProfileService) {
		this.categoryProfileService = categoryProfileService;
	}

}
