/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.service;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.security.access.AuthorityManager;
import org.beangle.security.auth.AnonymousAuthentication;
import org.beangle.security.blueprint.Resource;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.GrantedAuthority;
import org.beangle.security.web.AuthenticationEntryPoint;
import org.beangle.security.web.FilterInvocation;
import org.beangle.security.web.access.DefaultResourceExtractor;
import org.beangle.security.web.access.ResourceExtractor;
import org.beangle.security.web.auth.UrlEntryPoint;
import org.springframework.beans.factory.InitializingBean;

public class CacheableAuthorityManager extends BaseServiceImpl implements AuthorityManager,
		InitializingBean {

	// 登录入口点
	protected AuthenticationEntryPoint authenticationEntryPoint;
	/** 用户组权限 */
	protected Map<GrantedAuthority, Set<?>> authorities = CollectUtils.newHashMap();

	/** 公开资源name */
	protected Set<String> publicResources;

	/** 公有资源names */
	protected Set<?> protectedResources;

	protected AuthorityService authorityService;

	protected ResourceExtractor resourceExtractor = new DefaultResourceExtractor();

	private boolean expired = true;

	/**
	 * 资源是否被授权<br>
	 * 1)检查是否是属于公有资源<br>
	 * 2)检查用户组权限<br>
	 */
	public boolean isAuthorized(Authentication auth, Object resource) {
		loadResourceNecessary();
		FilterInvocation fi = (FilterInvocation) resource;
		String resourceName = resourceExtractor.extract(fi.getHttpRequest());
		if (publicResources.contains(resourceName)) { return true; }
		if (AnonymousAuthentication.class.isAssignableFrom(auth.getClass())) { return false; }
		if (protectedResources.contains(resourceName)) { return true; }
		Collection<? extends GrantedAuthority> authories = auth.getAuthorities();
		if (authories.isEmpty()) { return false; }
		for (GrantedAuthority authorty : authories) {
			if (isAuthorizedByGroup(authorty, resourceName)) { return true; }
		}
		return false;
	}

	/**
	 * 判断组内是否含有该资源
	 * 
	 * @param authority
	 * @param resource
	 * @return
	 */
	private boolean isAuthorizedByGroup(GrantedAuthority authority, Object resource) {
		Set<?> actions = authorities.get(authority);
		if (null == actions) {
			actions = refreshGroupAuthorities(authority);
		}
		return actions.contains(resource);
	}

	public Set<?> refreshGroupAuthorities(GrantedAuthority group) {
		Set<?> actions = authorityService.getResourceNamesByGroup(group.toString());
		authorities.put(group, actions);
		logger.debug("add authorities for group:{} resource:{}", group, actions);
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

	/**
	 * 加载三类资源
	 */
	public void refreshCache() {
		publicResources = authorityService.getResourceNames(Resource.Scope.PUBLIC);
		if (null != authenticationEntryPoint && authenticationEntryPoint instanceof UrlEntryPoint) {
			UrlEntryPoint fep = (UrlEntryPoint) authenticationEntryPoint;
			String loginResource = resourceExtractor.extract(fep.getLoginUrl());
			if (null != loginResource) {
				publicResources.add(loginResource);
			}
		}
		protectedResources = authorityService.getResourceNames(Resource.Scope.PROTECTED);
		expired = false;
	}

	public void afterPropertiesSet() throws Exception {
		Validate.notNull(authorityService, "authorityService cannot be null");
	}

	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

}
