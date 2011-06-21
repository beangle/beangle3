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
import org.beangle.commons.collection.page.PagedList;
import org.beangle.commons.comparators.PropertyComparator;
import org.beangle.ems.security.session.CategoryProfile;
import org.beangle.ems.security.session.model.CategoryProfileBean;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.security.core.session.SessionInfo;
import org.beangle.security.core.session.SessionRegistry;
import org.beangle.security.core.session.SessionStat;
import org.beangle.security.core.session.category.CategorySessionStat;
import org.beangle.security.web.access.log.Accesslog;
import org.beangle.security.web.access.log.CachedResourceAccessor;

/**
 * 系统在线用户管理
 * 
 * @author chaostone
 */
public class MonitorAction extends SecurityActionSupport {

	private SessionRegistry sessionRegistry;

	private CachedResourceAccessor resourceAccessor;

	public String profiles() {
		put("categoryProfiles", entityDao.getAll(CategoryProfile.class));
		return forward();
	}

	public String sessionStats() {
		put("categoryProfiles", entityDao.getAll(CategoryProfile.class));
		List<CategorySessionStat> stats = entityDao.getAll(CategorySessionStat.class);
		List<SessionStat> sessionStats = buildStats(stats);
		put("sessionStats", sessionStats);
		put("serverName",ManagementFactory.getRuntimeMXBean().getName());
		return forward();
	}

	private List<SessionStat> buildStats(List<CategorySessionStat> stats) {
		Map<String, Map<String, Integer>> capacityMap = CollectUtils.newHashMap();
		Map<String, Map<String, Integer>> onlineMap = CollectUtils.newHashMap();
		Map<String, Date> dateMap = CollectUtils.newHashMap();
		for (CategorySessionStat stat : stats) {
			Map<String, Integer> capacities = capacityMap.get(stat.getServerName());
			if (null == capacities) {
				capacities = CollectUtils.newHashMap();
				capacityMap.put(stat.getServerName(), capacities);
			}
			capacities.put(stat.getCategory(), stat.getCapacity());

			Map<String, Integer> onlines = onlineMap.get(stat.getServerName());
			if (null == onlines) {
				onlines = CollectUtils.newHashMap();
				onlineMap.put(stat.getServerName(), onlines);
			}
			onlines.put(stat.getCategory(), stat.getOnline());
			dateMap.put(stat.getServerName(), stat.getStatAt());
		}

		Map<String, Integer> serverCapacity = CollectUtils.newHashMap();
		for (String serverName : capacityMap.keySet()) {
			Map<String, Integer> myCapacities = capacityMap.get(serverName);
			int capacity = 0;
			for (Integer c : myCapacities.values()) {
				capacity += c;
			}
			serverCapacity.put(serverName, new Integer(capacity));
		}

		Map<String, Integer> serverOnline = CollectUtils.newHashMap();
		for (String serverName : onlineMap.keySet()) {
			Map<String, Integer> myOnlines = onlineMap.get(serverName);
			int online = 0;
			for (Integer c : myOnlines.values()) {
				online += c;
			}
			serverOnline.put(serverName, new Integer(online));
		}
		Set<String> servers = CollectUtils.newHashSet(serverCapacity.keySet());
		servers.addAll(serverOnline.keySet());
		List<SessionStat> sessionStats = CollectUtils.newArrayList();
		for (String server : servers) {
			Integer capacity = serverCapacity.get(server);
			Integer online = serverOnline.get(server);
			sessionStats.add(new SessionStat(server, dateMap.get(server), (null == capacity) ? 0 : capacity,
					(null == online) ? 0 : online, onlineMap.get(server)));
		}
		return sessionStats;
	}

	public String activities() {
		String orderBy = get("orderBy");
		if (StringUtils.isEmpty(orderBy)) {
			orderBy = "authentication.principal asc";
		}
		List<SessionInfo> onlineActivities = sessionRegistry.getAll();
		Collections.sort(onlineActivities, new PropertyComparator<Object>(orderBy));
		put("onlineActivities", new PagedList<SessionInfo>(onlineActivities, getPageLimit()));
		put("sessionStat", sessionRegistry.getController().getSessionStat());
		return forward();
	}

	/**
	 * 保存设置
	 */
	public String saveProfile() {
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
		entityDao.saveOrUpdate(categories);
		return redirect("profiles", "info.save.success");
	}

	public String invalidate() {
		String[] sessionIds = (String[]) getAll("sessionId");
		String mySessionId = ServletActionContext.getRequest().getSession().getId();
		boolean killed = getBool("kill");
		if (null != sessionIds) {
			for (String sessionId : sessionIds) {
				if (mySessionId.equals(sessionId)) continue;
				if (killed) sessionRegistry.remove(sessionId);
				else sessionRegistry.expire(sessionId);
			}
		}
		return redirect("activities", "info.action.success");
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

}
