/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.service.internal;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.metadata.Model;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.security.blueprint.Permission;
import org.beangle.security.blueprint.Resource;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.event.RoleAuthorityEvent;
import org.beangle.security.blueprint.service.AuthorityService;
import org.beangle.security.blueprint.service.UserService;

/**
 * 授权信息的服务实现类
 * 
 * @author dell,chaostone 2005-9-26
 */
public class AuthorityServiceImpl extends BaseServiceImpl implements AuthorityService {

  protected UserService userService;

  public Resource getResource(String name) {
    OqlBuilder<Resource> query = OqlBuilder.from(Resource.class, "r");
    query.where("r.name=:name", name).cacheable();
    return entityDao.uniqueResult(query);
  }

  public List<Permission> getPermissions(User user) {
    if (null == user) return Collections.emptyList();
    Map<Resource, Permission> permissions = CollectUtils.newHashMap();
    for (final Role role : user.getRoles()) {
      List<Permission> rolePerms = getPermissions(role);
      for (final Permission rolePerm : rolePerms) {
        if (permissions.containsKey(rolePerm.getResource())) {
          Permission existed = permissions.get(rolePerm.getResource());
          existed.merge(rolePerm);
        } else {
          permissions.put(rolePerm.getResource(), rolePerm);
        }
      }
    }
    return CollectUtils.newArrayList(permissions.values());
  }

  public List<Resource> getResources(User user) {
    Set<Resource> resources = CollectUtils.newHashSet();
    Map<String, Object> params = CollectUtils.newHashMap();
    String hql = "select distinct m from " + Role.class.getName() + " as r join r.permissions as a"
        + " join a.resource as m where  r.id = :roleId";
    params.clear();
    for (final Role role : user.getRoles()) {
      params.put("roleId", role.getId());
      List<Resource> roleResources = entityDao.searchHQLQuery(hql, params);
      resources.addAll(roleResources);
    }
    return CollectUtils.newArrayList(resources);
  }

  /** 找到该组内激活的资源id */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public Set<String> getResourceNamesByRole(Long roleId) {
    String hql = "select a.resource.name from " + Permission.class.getName()
        + " as a where a.role.id= :roleId and a.resource.enabled = true";
    OqlBuilder query = OqlBuilder.hql(hql).param("roleId", roleId).cacheable();
    return (Set<String>) new HashSet(entityDao.search(query));
  }

  /** 找到该组内激活的资源id */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public Set<String> getResourceNamesByScope(Resource.Scope scope) {
    OqlBuilder builder = OqlBuilder.from(Resource.class, "r").where("r.scope=:scope", scope).select("r.name")
        .cacheable();
    return (Set<String>) new HashSet(entityDao.search(builder));
  }

  public void authorize(Role role, Set<Resource> resources) {
    Set<Permission> removed = CollectUtils.newHashSet();
    for (final Permission au : role.getPermissions()) {
      if (!resources.contains(au.getResource())) {
        removed.add(au);
      } else {
        resources.remove(au.getResource());
      }
    }
    role.getPermissions().removeAll(removed);

    for (Resource resource : resources) {
      Permission authority = Model.newInstance(Permission.class);
      authority.setRole(role);
      authority.setResource(resource);
      role.getPermissions().add(authority);
    }
    entityDao.remove(removed);
    entityDao.saveOrUpdate(role);
    publish(new RoleAuthorityEvent(role));
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public void updateState(Long[] resourceIds, boolean isEnabled) {
    OqlBuilder query = OqlBuilder.from(Resource.class, "resource");
    query.where("resource.id in (:ids)", resourceIds);
    List<Resource> resources = entityDao.search(query);
    for (Resource resource : resources) {
      resource.setEnabled(isEnabled);
    }
    entityDao.saveOrUpdate(resources);
  }

  public List<Permission> getPermissions(Role role) {
    OqlBuilder<Permission> builder = OqlBuilder.hql("select distinct a from  " + Role.class.getName()
        + " as r join r.permissions as a join a.resource as m  where r = :role");
    builder.param("role", role);
    return entityDao.search(builder);
  }

  /** 查询角色对应的模块 */
  public List<Resource> getResources(Role role) {
    String hql = "select distinct m from " + Role.class.getName() + " as r join r.permissions as a"
        + " join a.resource as m where  r.id = :roleId and m.enabled = true";
    OqlBuilder<Resource> query = OqlBuilder.hql(hql);
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
