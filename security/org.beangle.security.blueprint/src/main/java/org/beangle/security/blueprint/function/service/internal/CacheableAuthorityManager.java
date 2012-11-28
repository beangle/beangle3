/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.blueprint.function.service.internal;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.bean.Initializing;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.lang.Assert;
import org.beangle.commons.web.util.RequestUtils;
import org.beangle.security.access.AuthorityManager;
import org.beangle.security.auth.AnonymousAuthentication;
import org.beangle.security.auth.Principals;
import org.beangle.security.blueprint.SecurityUtils;
import org.beangle.security.blueprint.function.FuncResource;
import org.beangle.security.blueprint.function.service.FuncPermissionService;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.GrantedAuthority;
import org.beangle.security.core.session.category.CategoryPrincipal;
import org.beangle.security.web.AuthenticationEntryPoint;
import org.beangle.security.web.FilterInvocation;
import org.beangle.security.web.auth.UrlEntryPoint;

public class CacheableAuthorityManager extends BaseServiceImpl implements AuthorityManager, Initializing {

  // 登录入口点
  protected AuthenticationEntryPoint authenticationEntryPoint;
  /** 角色权限 */
  protected Map<GrantedAuthority, Set<?>> authorities = CollectUtils.newHashMap();

  /** 公开资源name */
  protected Set<String> publicResources;

  /** 公有资源names */
  protected Set<?> protectedResources;

  protected FuncPermissionService permissionService;

  private boolean expired = true;

  /**
   * 资源是否被授权<br>
   * 1)检查是否是属于公有资源<br>
   * 2)检查角色权限<br>
   */
  public boolean isAuthorized(Authentication auth, Object resource) {
    if (null == auth) return false;
    loadResourceNecessary();
    String resourceName = null;
    if (resource instanceof FilterInvocation) {
      FilterInvocation fi = (FilterInvocation) resource;
      resourceName = permissionService.extractResource(RequestUtils.getServletPath(fi.getHttpRequest()));
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

  /**
   * 判断组内是否含有该资源
   * 
   * @param authority
   * @param resource
   */
  private boolean isAuthorizedByRole(GrantedAuthority authority, Object resource) {
    Set<?> actions = authorities.get(authority);
    if (null == actions) {
      actions = refreshRolePermissions(authority);
    }
    return actions.contains(resource);
  }

  public Set<?> refreshRolePermissions(GrantedAuthority authority) {
    Set<?> actions = permissionService.getResourceNamesByRole((Integer) authority.getAuthority());
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
    publicResources = permissionService.getResourceNamesByScope(FuncResource.Scope.PUBLIC);
    if (null != authenticationEntryPoint && authenticationEntryPoint instanceof UrlEntryPoint) {
      UrlEntryPoint fep = (UrlEntryPoint) authenticationEntryPoint;
      String loginResource = permissionService.extractResource(fep.getLoginUrl());
      if (null != loginResource) {
        publicResources.add(loginResource);
      }
    }
    protectedResources = permissionService.getResourceNamesByScope(FuncResource.Scope.PROTECTED);
    expired = false;
  }

  public void init() throws Exception {
    Assert.notNull(permissionService, "authorityService cannot be null");
  }

  public void setPermissionService(FuncPermissionService funcPermissionService) {
    this.permissionService = funcPermissionService;
  }

  public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
    this.authenticationEntryPoint = authenticationEntryPoint;
  }

}
