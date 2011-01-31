/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.security.action;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.NoParameters;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.security.access.AccessDeniedException;
import org.beangle.security.blueprint.Resource;
import org.beangle.security.blueprint.SecurityUtils;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.restrict.Restriction;
import org.beangle.security.blueprint.restrict.service.RestrictionService;
import org.beangle.security.blueprint.service.AuthorityService;
import org.beangle.security.core.context.SecurityContextHolder;
import org.beangle.security.web.access.DefaultResourceExtractor;
import org.beangle.struts2.action.EntityDrivenAction;

import com.opensymphony.xwork2.ActionContext;

public abstract class SecurityActionSupport extends EntityDrivenAction implements NoParameters {

	private static final long serialVersionUID = 8321873197609852795L;

	protected AuthorityService authorityService;

	protected RestrictionService restrictionService;

	// FIXME new DefaultResourceExtractor
	protected Resource getResource() {
		return authorityService.getResource(new DefaultResourceExtractor()
				.extract(ServletActionContext.getRequest()));
	}

	protected boolean isAdmin(User user) {
		return authorityService.getUserService().isAdmin(user);
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
		User user = getUser();
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

	protected String getFullname() {
		return SecurityUtils.getFullname();
	}

	protected User getUser() {
		return (User) entityDao.get(User.class, getUserId());
	}

	protected Long getUserCategoryId() {
		return SecurityUtils.getUserCategoryId();
	}

	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	public void setRestrictionService(RestrictionService restrictionService) {
		this.restrictionService = restrictionService;
	}
}
