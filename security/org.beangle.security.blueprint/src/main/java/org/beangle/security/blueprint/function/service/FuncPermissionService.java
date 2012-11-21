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
package org.beangle.security.blueprint.function.service;

import java.util.List;
import java.util.Set;

import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.function.FuncPermission;
import org.beangle.security.blueprint.function.FuncResource;
import org.beangle.security.blueprint.service.UserService;

/**
 * 用户角色权限管理服务接口. 权限实体
 * 
 * @see org.beangle.security.blueprint.Permission
 * @see org.beangle.security.blueprint.Resource
 * @author dell,chaostone 2005-9-27
 */
public interface FuncPermissionService {

  /**
   * 按照资源名称查询单独的资源
   * 
   * @param name
   */
  FuncResource getResource(String name);

  /**
   * 查询用户的访问资源范围
   * 
   * @param user
   */
  List<FuncResource> getResources(User user);

  /**
   * 按照角色查找资源
   * 
   * @param roleId
   */
  Set<String> getResourceNamesByRole(Long roleId);

  /**
   * 按照角色查找资源
   * 
   * @param scope
   */
  Set<String> getResourceNamesByScope(FuncResource.Scope scope);

  /**
   * 角色内对应的资源
   * 
   * @param role
   */
  List<FuncResource> getResources(Role role);

  /**
   * 更新资源状态
   * 
   * @param resourceIds
   * @param isEnabled
   */
  void updateState(Long[] resourceIds, boolean isEnabled);

  /**
   * 依据默认深度（小于或等于）得到用户的所有权限
   * 
   * @param user
   */
  List<FuncPermission> getPermissions(User user);

  /**
   * 依据默认深度得到角色拥有的权限
   * 
   * @param role
   */
  List<FuncPermission> getPermissions(Role role);

  /**
   * 授权
   * 
   * @param role
   * @param resources
   */
  void authorize(Role role, Set<FuncResource> resources);

  /**
   * @param userService
   */
  void setUserService(UserService userService);

  /**
   */
  UserService getUserService();

  /**
   * Extract Resource from uri
   * 
   * @param uri
   *          with out context path
   */
  String extractResource(String uri);
}
