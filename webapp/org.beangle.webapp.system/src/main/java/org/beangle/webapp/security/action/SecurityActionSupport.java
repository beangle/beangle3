/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.security.action;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.NoParameters;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.security.access.AccessDeniedException;
import org.beangle.security.blueprint.Resource;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.restrict.RestrictField;
import org.beangle.security.blueprint.restrict.Restriction;
import org.beangle.security.blueprint.restrict.service.RestrictionService;
import org.beangle.security.blueprint.service.AuthorityService;
import org.beangle.security.blueprint.service.UserToken;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.AuthenticationException;
import org.beangle.security.core.context.SecurityContextHolder;
import org.beangle.security.web.access.DefaultResourceExtractor;
import org.beangle.struts2.action.EntityDrivenAction;

import com.opensymphony.xwork2.ActionContext;

public abstract class SecurityActionSupport extends EntityDrivenAction implements NoParameters {

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
		if (resource.getObjects().isEmpty()) { return Collections.emptyList(); }
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

	protected List<Object> getRestricitonValues(String name) {
		List<Restriction> restrictions = getRestrictions();
		Set<Object> values = CollectUtils.newHashSet();
		boolean gotIt = false;
		for (Restriction restiction : restrictions) {
			RestrictField param = restiction.getPattern().getObject().getField(name);
			if (null != param) {
				String value = restiction.getItem(param);
				if (null != value) {
					gotIt = true;
					values.addAll(restrictionService.select(restrictionService.getValues(param),
							restiction, param));
				}
			}
		}
		if (!gotIt) {
			List<RestrictField> params = entityDao.get(RestrictField.class, "name", name);
			if (params.isEmpty()) { throw new RuntimeException("bad pattern parameter named :"
					+ name); }
			RestrictField param = (RestrictField) params.get(0);
			return restrictionService.getValues(param);
		} else {
			return CollectUtils.newArrayList(values);
		}
	}

	protected void applyRestriction(OqlBuilder<?> query) {
		// Resource resource = getResource();
		restrictionService.apply(query, getRestrictions());
	}

	protected UserToken getAuthentication() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (null == auth) throw new AuthenticationException();
		UserToken user = (UserToken) auth.getPrincipal();
		if (null == user.getId()) throw new AuthenticationException();
		return user;
	}

	protected Long getUserId() {
		return getAuthentication().getId();
	}

	protected String getUsername() {
		return getAuthentication().getUsername();
	}

	protected String getFullName() {
		return getAuthentication().getFullname();
	}

	protected User getUser() {
		return (User) entityDao.get(User.class, getUserId());
	}

	protected Long getUserCategoryId() {
		return getAuthentication().getCategory().getId();
	}

	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	public void setRestrictionService(RestrictionService restrictionService) {
		this.restrictionService = restrictionService;
	}
}
