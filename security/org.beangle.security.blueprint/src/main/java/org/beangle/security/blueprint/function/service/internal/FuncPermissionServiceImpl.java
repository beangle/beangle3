/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.function.service.internal;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.Operation;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.event.RoleAuthorityEvent;
import org.beangle.security.blueprint.function.FuncPermission;
import org.beangle.security.blueprint.function.FuncResource;
import org.beangle.security.blueprint.function.model.FuncPermissionBean;
import org.beangle.security.blueprint.function.model.FuncResourceBean;
import org.beangle.security.blueprint.function.service.FuncPermissionService;
import org.beangle.security.blueprint.service.UserService;

/**
 * 授权信息的服务实现类
 * 
 * @author dell,chaostone 2005-9-26
 */
public class FuncPermissionServiceImpl extends BaseServiceImpl implements FuncPermissionService {

  protected UserService userService;

  public FuncResource getResource(String name) {
    OqlBuilder<FuncResource> query = OqlBuilder.from(FuncResource.class, "r");
    query.where("r.name=:name", name).cacheable();
    return entityDao.uniqueResult(query);
  }

  public List<FuncPermission> getPermissions(User user) {
    if (null == user) return Collections.emptyList();
    List<FuncPermission> permissions = CollectUtils.newArrayList();
    for (final Role role : user.getRoles())
      permissions.addAll(getPermissions(role));
    return permissions;
  }

  public List<FuncResource> getResources(User user) {
    Set<FuncResource> resources = CollectUtils.newHashSet();
    Map<String, Object> params = CollectUtils.newHashMap();
    String hql = "select distinct fp.resource from " + FuncPermission.class.getName()
        + " fp where fp.role.id = :roleId";
    params.clear();
    for (final Role role : user.getRoles()) {
      params.put("roleId", role.getId());
      List<FuncResource> roleResources = entityDao.searchHQLQuery(hql, params);
      resources.addAll(roleResources);
    }
    return CollectUtils.newArrayList(resources);
  }

  /** 找到该组内激活的资源id */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public Set<String> getResourceNamesByRole(Long roleId) {
    String hql = "select a.resource.name from " + FuncPermission.class.getName()
        + " as a where a.role.id= :roleId and a.resource.enabled = true";
    OqlBuilder query = OqlBuilder.hql(hql).param("roleId", roleId).cacheable();
    return (Set<String>) new HashSet(entityDao.search(query));
  }

  /** 找到该组内激活的资源id */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public Set<String> getResourceNamesByScope(FuncResource.Scope scope) {
    OqlBuilder builder = OqlBuilder.from(FuncResource.class, "r").where("r.scope=:scope", scope)
        .select("r.name").cacheable();
    return (Set<String>) new HashSet(entityDao.search(builder));
  }

  public void authorize(Role role, Set<FuncResource> resources) {
    Set<FuncPermission> removed = CollectUtils.newHashSet();
    List<FuncPermission> permissions = getPermissions(role);
    for (final FuncPermission au : permissions) {
      if (!resources.contains(au.getResource())) removed.add(au);
      else resources.remove(au.getResource());
    }
    permissions.removeAll(removed);
    for (FuncResource resource : resources) {
      FuncPermissionBean authority = new FuncPermissionBean(role, resource, null);
      permissions.add(authority);
    }
    entityDao.execute(Operation.remove(removed).saveOrUpdate(permissions).saveOrUpdate(role));
    publish(new RoleAuthorityEvent(role));
  }

  public void updateState(Long[] resourceIds, boolean isEnabled) {
    OqlBuilder<FuncResourceBean> query = OqlBuilder.from(FuncResourceBean.class, "resource");
    query.where("resource.id in (:ids)", resourceIds);
    List<FuncResourceBean> resources = entityDao.search(query);
    for (FuncResourceBean resource : resources) {
      resource.setEnabled(isEnabled);
    }
    entityDao.saveOrUpdate(resources);
  }

  public List<FuncPermission> getPermissions(Role role) {
    return entityDao.search(OqlBuilder.from(FuncPermission.class, "fp").where("fp.role=:role", role));
  }

  /** 查询角色对应的模块 */
  public List<FuncResource> getResources(Role role) {
    String hql = "select distinct m from " + Role.class.getName() + " as r join r.permissions as a"
        + " join a.resource as m where  r.id = :roleId and m.enabled = true";
    OqlBuilder<FuncResource> query = OqlBuilder.hql(hql);
    query.param("roleId", role.getId()).cacheable();
    return entityDao.search(query);
  }

  public String extractResource(String uri) {
    int lastDot = -1;
    for (int i = 0; i < uri.length(); i++) {
      char a = uri.charAt(i);
      if (a == '.' || a == '!') {
        lastDot = i;
        break;
      }
    }
    if (lastDot < 0) {
      lastDot = uri.length();
    }
    return uri.substring(0, lastDot);
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  public UserService getUserService() {
    return userService;
  }
}
