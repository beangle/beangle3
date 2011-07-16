/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.action;

import java.lang.management.ManagementFactory;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.Order;
import org.beangle.commons.collection.page.PagedList;
import org.beangle.commons.comparators.PropertyComparator;
import org.beangle.ems.security.session.model.CategoryProfileBean;
import org.beangle.ems.security.session.service.CategoryProfileService;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.security.core.session.SessionRegistry;
import org.beangle.security.core.session.SessionStat;
import org.beangle.security.core.session.category.CategorySessionStat;
import org.beangle.security.web.access.log.Accesslog;
import org.beangle.security.web.access.log.CachedResourceAccessor;
import org.beangle.security.web.session.model.SessioninfoBean;

/**
 * 系统在线用户管理
 * 
 * @author chaostone
 */
public class MonitorAction extends SecurityActionSupport {

	private SessionRegistry sessionRegistry;

	private CategoryProfileService categoryProfileService;

	private CachedResourceAccessor resourceAccessor;

	public String profiles() {
		put("categoryProfiles", entityDao.getAll(CategoryProfileBean.class));
		return forward();
	}

	public String sessionStats() {
		put("categoryProfiles", entityDao.getAll(CategoryProfileBean.class));
		List<CategorySessionStat> stats = entityDao.getAll(CategorySessionStat.class);
		List<SessionStat> sessionStats = buildStats(stats);
		put("sessionStats", sessionStats);
		put("serverName", ManagementFactory.getRuntimeMXBean().getName());
		return forward();
	}

	private List<SessionStat> buildStats(List<CategorySessionStat> stats) {
		Map<String, Map<String, CategorySessionStat>> categoryMaps = CollectUtils.newHashMap();
		Map<String, Date> dateMap = CollectUtils.newHashMap();
		for (CategorySessionStat stat : stats) {
			Map<String, CategorySessionStat> categoryMap = categoryMaps.get(stat.getServerName());
			if (null == categoryMap) {
				categoryMap = CollectUtils.newHashMap();
				categoryMaps.put(stat.getServerName(), categoryMap);
			}
			categoryMap.put(stat.getCategory(), stat);
			dateMap.put(stat.getServerName(), stat.getStatAt());
		}

		Map<String, Integer> serverCapacity = CollectUtils.newHashMap();
		Map<String, Integer> serverOnline = CollectUtils.newHashMap();

		for (String serverName : categoryMaps.keySet()) {
			Map<String, CategorySessionStat> myOnlines = categoryMaps.get(serverName);
			int online = 0;
			int capacity = 0;
			for (CategorySessionStat one : myOnlines.values()) {
				online += one.getOnline();
				capacity += one.getCapacity();
			}
			serverOnline.put(serverName, new Integer(online));
			serverCapacity.put(serverName, new Integer(capacity));
		}

		Set<String> servers = CollectUtils.newHashSet(serverCapacity.keySet());
		servers.addAll(serverOnline.keySet());
		List<SessionStat> sessionStats = CollectUtils.newArrayList();
		for (String server : servers) {
			Integer capacity = serverCapacity.get(server);
			Integer online = serverOnline.get(server);
			sessionStats.add(new SessionStat(server, dateMap.get(server), (null == capacity) ? 0 : capacity,
					(null == online) ? 0 : online, categoryMaps.get(server)));
		}
		return sessionStats;
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
		put("sessionStat", sessionRegistry.getController().getSessionStat());
		return forward();
	}

	/**
	 * 保存设置
	 */
	public String saveProfile() {
		OqlBuilder<CategoryProfileBean> builder = OqlBuilder.from(CategoryProfileBean.class, "p");
		builder.where("p.sessionProfile.name=:serverName", sessionRegistry.getController().getServerName());
		List<CategoryProfileBean> categories = entityDao.getAll(CategoryProfileBean.class);
		for (final CategoryProfileBean profile : categories) {
			Long categoryId = profile.getCategory().getId();
			Integer max = getInteger("max_" + categoryId);
			Integer maxSessions = getInteger("maxSessions_" + categoryId);
			Integer inactiveInterval = getInteger("inactiveInterval_" + categoryId);
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
				if (killed) sessionRegistry.remove(sessionId);
				else sessionRegistry.expire(sessionId);
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
		List<Accesslog> accessLogs = null;
		if (null == resourceAccessor) {
			accessLogs = Collections.emptyList();
		} else {
			accessLogs = CollectUtils.newArrayList(resourceAccessor.getAccessLogs());
		}
		String orderBy = get("orderBy");
		if (StringUtils.isEmpty(orderBy)) {
			orderBy = "duration desc";
		}
		if (orderBy.startsWith("accesslog.")) {
			orderBy = StringUtils.substringAfter(orderBy, "accesslog.");
		}
		Collections.sort(accessLogs, new PropertyComparator<Object>(orderBy));
		put("accesslogs", new PagedList<Accesslog>(accessLogs, getPageLimit()));
		return forward();
	}

	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}

	public void setResourceAccessor(CachedResourceAccessor resourceAccessor) {
		this.resourceAccessor = resourceAccessor;
	}

	public void setCategoryProfileService(CategoryProfileService categoryProfileService) {
		this.categoryProfileService = categoryProfileService;
	}

}
