/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
 * @see <code>Permission</code> 系统资源实体
 * @see <code>Resource</code> 系统功能点实体
 * @see <code>Action</code> 数据权限实体
 * @author dell,chaostone 2005-9-27
 */
public interface FuncPermissionService {

  /**
   * 按照资源名称查询单独的资源
   * 
   * @param name
   */
  public FuncResource getResource(String name);

  /**
   * 查询用户的访问资源范围
   * 
   * @param user
   */
  public List<FuncResource> getResources(User user);

  /**
   * 按照角色查找资源
   * 
   * @param roleId
   */
  public Set<String> getResourceNamesByRole(Long roleId);

  /**
   * 按照角色查找资源
   * 
   * @param scope
   */
  public Set<String> getResourceNamesByScope(FuncResource.Scope scope);

  /**
   * 角色内对应的资源
   * 
   * @param role
   */
  public List<FuncResource> getResources(Role role);

  /**
   * 更新资源状态
   * 
   * @param resourceIds
   * @param isEnabled
   */
  public void updateState(Long[] resourceIds, boolean isEnabled);

  /**
   * 依据默认深度（小于或等于）得到用户的所有权限
   * 
   * @param user
   */
  public List<FuncPermission> getPermissions(User user);

  /**
   * 依据默认深度得到角色拥有的权限
   * 
   * @param role
   */
  public List<FuncPermission> getPermissions(Role role);

  /**
   * 授权
   * 
   * @param role
   * @param resources
   */
  public void authorize(Role role, Set<FuncResource> resources);

  /**
   * @param userService
   */
  public void setUserService(UserService userService);

  /**
   */
  public UserService getUserService();

  /**
   * Extract Resource from uri
   * 
   * @param uri
   *          with out context path
   */
  public String extractResource(String uri);
}
