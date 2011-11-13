/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.web.action;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.NoParameters;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.StrUtils;
import org.beangle.ems.security.Resource;
import org.beangle.ems.security.SecurityUtils;
import org.beangle.ems.security.User;
import org.beangle.ems.security.restrict.Restriction;
import org.beangle.ems.security.restrict.service.RestrictionService;
import org.beangle.ems.security.service.AuthorityService;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.security.access.AccessDeniedException;
import org.beangle.security.core.context.SecurityContextHolder;
import org.beangle.struts2.action.EntityDrivenAction;
import org.beangle.web.util.RequestUtils;

import com.opensymphony.xwork2.ActionContext;

public abstract class SecurityActionSupport extends EntityDrivenAction implements NoParameters {

	private static final long serialVersionUID = 8321873197609852795L;

	protected AuthorityService authorityService;

	protected RestrictionService restrictionService;

	protected Resource getResource() {
		String resourceName = SecurityUtils.getResource();
		if (null == resourceName) {
			resourceName = authorityService.extractResource(RequestUtils.getServletPath(ServletActionContext
					.getRequest()));
		}
		return authorityService.getResource(resourceName);
	}

	protected boolean isAdmin() {
		return authorityService.getUserService().isAdmin(getUserId());
	}

	protected List<Restriction> getRestrictions() {
		final Map<String, Object> session = ActionContext.getContext().getSession();
		@SuppressWarnings("unchecked")
		Map<Long, List<Restriction>> restrictionMap = (Map<Long, List<Restriction>>) session
				.get("security.restriction");
		if (null == restrictionMap) {
			restrictionMap = CollectUtils.newHashMap();
			session.put("security.restriction", restrictionMap);
		}
		Resource resource = getResource();
		if (resource.getEntities().isEmpty()) { return Collections.emptyList(); }
		List<Restriction> realms = restrictionMap.get(resource.getId());
		User user = entityDao.get(User.class, getUserId());
		if (null == realms) {
			realms = restrictionService.getRestrictions(user, resource);
			restrictionMap.put(resource.getId(), realms);
		}
		// 没有权限就报错
		if (realms.isEmpty()) { throw new AccessDeniedException(SecurityContextHolder.getContext()
				.getAuthentication(), resource.getName()); }
		return realms;
	}

	protected List<?> getRestricitonValues(String name) {
		return restrictionService.getFieldValues(name, getRestrictions());
	}

	protected void applyRestriction(OqlBuilder<?> query) {
		// Resource resource = getResource();
		restrictionService.apply(query, getRestrictions());
	}

	protected Long getUserId() {
		return SecurityUtils.getUserId();
	}

	protected String getUsername() {
		return SecurityUtils.getUsername();
	}

	protected String getUser() {
		return StrUtils.concat(SecurityUtils.getUsername(), "(", SecurityUtils.getFullname(), ")");
	}

	protected Long getUserCategoryId() {
		Long categoryId = getLong("security.categoryId");
		if (null == categoryId) {
			categoryId = (Long) ActionContext.getContext().getSession().get("security.categoryId");
			if (null == categoryId) {
				User user = entityDao.get(User.class, getUserId());
				categoryId = user.getDefaultCategory().getId();
				ActionContext.getContext().getSession().put("security.categoryId", categoryId);
			}
		}
		return categoryId;
	}

	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	public void setRestrictionService(RestrictionService restrictionService) {
		this.restrictionService = restrictionService;
	}
}
