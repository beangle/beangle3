/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.web.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.NoParameters;
import org.beangle.dao.query.builder.OqlBuilder;
import org.beangle.ems.security.Resource;
import org.beangle.ems.security.SecurityUtils;
import org.beangle.ems.security.profile.UserProfile;
import org.beangle.ems.security.restrict.Restriction;
import org.beangle.ems.security.restrict.service.RestrictionService;
import org.beangle.ems.security.service.AuthorityService;
import org.beangle.lang.StrUtils;
import org.beangle.security.access.AccessDeniedException;
import org.beangle.security.core.context.SecurityContextHolder;
import org.beangle.struts2.action.EntityDrivenAction;
import org.beangle.web.util.RequestUtils;

public abstract class SecurityActionSupport extends EntityDrivenAction implements NoParameters {

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
		return authorityService.getUserService().isRoot(getUserId());
	}

	protected List<Restriction> getRestrictions() {
		// final Map<String, Object> session = ActionContext.getContext().getSession();
		// @SuppressWarnings("unchecked")
		// Map<Long, List<Restriction>> restrictionMap = (Map<Long, List<Restriction>>) session
		// .get("security.restriction");
		// if (null == restrictionMap) {
		// restrictionMap = CollectUtils.newHashMap();
		// session.put("security.restriction", restrictionMap);
		// }
		Resource resource = getResource();
		// if (resource.getEntities().isEmpty()) { return Collections.emptyList(); }
		// List<Restriction> realms = restrictionMap.get(resource.getId());
//		User user = entityDao.get(User.class, getUserId());
		// if (null == realms) {
		UserProfile profile = getUserProfile();
		List<Restriction> holders = restrictionService.getRestrictions(profile, resource);
		// restrictionMap.put(resource.getId(), realms);
		// }
		// 没有权限就报错
		if (holders.isEmpty()) { throw new AccessDeniedException(SecurityContextHolder.getContext()
				.getAuthentication(), resource.getName()); }
		return holders;
	}

	protected Object getUserPropertyValue(String name) {
		UserProfile profile = getUserProfile();
		if (null == profile) return null;
		else return restrictionService.getPropertyValue(name, profile);
	}

	protected UserProfile getUserProfile() {
		OqlBuilder<UserProfile> builder=OqlBuilder.from(UserProfile.class,"up").where("up.user.id=:userId",getUserId());
		List<UserProfile> profiles=entityDao.search(builder);
		return profiles.isEmpty()?null:profiles.get(0);
	}

	protected void applyRestriction(OqlBuilder<?> query) {
		restrictionService.apply(query, getRestrictions(), getUserProfile());
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

	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	public void setRestrictionService(RestrictionService restrictionService) {
		this.restrictionService = restrictionService;
	}
}
