/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.service;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.beangle.bean.Initializing;
import org.beangle.collection.CollectUtils;
import org.beangle.dao.impl.BaseServiceImpl;
import org.beangle.ems.security.Resource;
import org.beangle.ems.security.SecurityUtils;
import org.beangle.security.access.AuthorityManager;
import org.beangle.security.auth.AnonymousAuthentication;
import org.beangle.security.auth.Principals;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.GrantedAuthority;
import org.beangle.security.core.session.category.CategoryPrincipal;
import org.beangle.security.web.AuthenticationEntryPoint;
import org.beangle.security.web.FilterInvocation;
import org.beangle.security.web.auth.UrlEntryPoint;
import org.beangle.web.util.RequestUtils;

public class CacheableAuthorityManager extends BaseServiceImpl implements AuthorityManager, Initializing {

	// 登录入口点
	protected AuthenticationEntryPoint authenticationEntryPoint;
	/** 角色权限 */
	protected Map<GrantedAuthority, Set<?>> authorities = CollectUtils.newHashMap();

	/** 公开资源name */
	protected Set<String> publicResources;

	/** 公有资源names */
	protected Set<?> protectedResources;

	protected AuthorityService authorityService;

	private boolean expired = true;

	/** 资源是否被授权<br>
	 * 1)检查是否是属于公有资源<br>
	 * 2)检查角色权限<br> */
	public boolean isAuthorized(Authentication auth, Object resource) {
		loadResourceNecessary();
		String resourceName = null;
		if (resource instanceof FilterInvocation) {
			FilterInvocation fi = (FilterInvocation) resource;
			resourceName = authorityService.extractResource(RequestUtils.getServletPath(fi.getHttpRequest()));
		} else {
			resourceName = resource.toString();
		}
		// registe resourceName
		SecurityUtils.setResource(resourceName);
		if (publicResources.contains(resourceName)) { return true; }
		if (AnonymousAuthentication.class.isAssignableFrom(auth.getClass())) { return false; }
		if (protectedResources.contains(resourceName)) { return true; }
		Collection<? extends GrantedAuthority> authories = auth.getAuthorities();
		for (GrantedAuthority authorty : authories) {
			if (isAuthorizedByRole(authorty, resourceName)) { return true; }
		}
		// final check root user
		Object principal = auth.getPrincipal();
		if (principal instanceof CategoryPrincipal) {
			if (Principals.ROOT.equals(((CategoryPrincipal) principal).getId())) { return true; }
		}
		return false;
	}

	/** 判断组内是否含有该资源
	 * 
	 * @param authority
	 * @param resource
	 * @return */
	private boolean isAuthorizedByRole(GrantedAuthority authority, Object resource) {
		Set<?> actions = authorities.get(authority);
		if (null == actions) {
			actions = refreshRolePermissions(authority);
		}
		return actions.contains(resource);
	}

	public Set<?> refreshRolePermissions(GrantedAuthority authority) {
		Set<?> actions = authorityService.getResourceNamesByRole((Long)authority.getAuthority());
		authorities.put(authority, actions);
		logger.debug("Refresh role:{}'s permissions:{}", authority, actions);
		return actions;
	}

	private void loadResourceNecessary() {
		if (expired) {
			synchronized (this) {
				if (!expired) return;
				refreshCache();
			}
		}
	}

	/** 加载三类资源 */
	public void refreshCache() {
		publicResources = authorityService.getResourceNamesByScope(Resource.Scope.PUBLIC);
		if (null != authenticationEntryPoint && authenticationEntryPoint instanceof UrlEntryPoint) {
			UrlEntryPoint fep = (UrlEntryPoint) authenticationEntryPoint;
			String loginResource = authorityService.extractResource(fep.getLoginUrl());
			if (null != loginResource) {
				publicResources.add(loginResource);
			}
		}
		protectedResources = authorityService.getResourceNamesByScope(Resource.Scope.PROTECTED);
		expired = false;
	}

	public void init() throws Exception {
		Validate.notNull(authorityService, "authorityService cannot be null");
	}

	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

}
