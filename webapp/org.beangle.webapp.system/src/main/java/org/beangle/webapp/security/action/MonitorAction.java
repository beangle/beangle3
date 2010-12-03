/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.security.action;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.page.PagedList;
import org.beangle.commons.comparators.PropertyComparator;
import org.beangle.security.blueprint.session.model.CategoryProfileBean;
import org.beangle.security.core.session.SessionInfo;
import org.beangle.security.web.access.log.Accesslog;
import org.beangle.security.web.access.log.CachedResourceAccessor;
import org.beangle.security.web.session.category.CategorySessionRegistry;

/**
 * 系统在线用户管理
 * 
 * @author chaostone
 */
public class MonitorAction extends SecurityActionSupport {

	private CategorySessionRegistry sessionRegistry;

	private CachedResourceAccessor resourceAccessor;
	
	public String profiles() {
		put("onlineProfiles", sessionRegistry.getProfiles());
		return forward();
	}

	public String activities() {
		String orderBy = get("orderBy");
		if (StringUtils.isEmpty(orderBy)) {
			orderBy = "principal asc";
		}
		List<SessionInfo> onlineActivities = sessionRegistry.getAll();
		Collections.sort(onlineActivities, new PropertyComparator<Object>(orderBy));
		put("onlineActivities", new PagedList<SessionInfo>(onlineActivities, getPageLimit()));
		put("onlineProfiles", sessionRegistry.getProfiles());
		put("now", new Date());
		return forward();
	}

	/**
	 * 保存设置
	 */
	public String saveProfile() {
		List<CategoryProfileBean> categories = entityDao.getAll(CategoryProfileBean.class);
		for (final CategoryProfileBean profile : categories) {
			Long categoryId = profile.getCategory().getId();
			int max = getInteger("max_" + categoryId).intValue();
			int maxSessions = getInteger("maxSessions_" + categoryId).intValue();
			int inactiveInterval = getInteger("inactiveInterval_" + categoryId).intValue();
			profile.setCapacity(max);
			profile.setUserMaxSessions(maxSessions);
			profile.setInactiveInterval(inactiveInterval);
		}
		entityDao.saveOrUpdate(categories);
		// FIXME
		// sessionController.loadProfiles();
		return redirect("profiles", "info.save.success");
	}

	public String invalidate() {
		String[] sessionIds = (String[]) getAll("sessionId");
		String mySessionId = ServletActionContext.getRequest().getSession().getId();
		if (null != sessionIds) {
			for (int i = 0; i < sessionIds.length; i++) {
				if (mySessionId.equals(sessionIds[i])) continue;
				sessionRegistry.remove(sessionIds[i]);
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
		Collections.sort(accessLogs, new PropertyComparator<Object>(orderBy));
		put("accesslogs", new PagedList<Accesslog>(accessLogs, getPageLimit()));
		return forward();
	}

	public void setSessionRegistry(CategorySessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}

	public void setResourceAccessor(CachedResourceAccessor resourceAccessor) {
		this.resourceAccessor = resourceAccessor;
	}

}
