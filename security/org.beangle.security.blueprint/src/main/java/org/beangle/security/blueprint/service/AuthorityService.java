/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.service;

import java.util.List;
import java.util.Set;

import org.beangle.security.blueprint.Permission;
import org.beangle.security.blueprint.Resource;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.User;

/**
 * 用户角色权限管理服务接口. 权限实体@see <code>Authority</code> 系统资源实体@see <code>Resource</code> 系统功能点实体@see
 * <code>Action</code> 数据权限实体@see <code>Restriction</code>
 * 
 * @author dell,chaostone 2005-9-27
 */
public interface AuthorityService {

  /**
   * 按照资源名称查询单独的资源
   * 
   * @param name
   * @return
   */
  public Resource getResource(String name);

  /**
   * 查询用户的访问资源范围
   * 
   * @param user
   * @return
   */
  public List<Resource> getResources(User user);

  /**
   * 按照角色查找资源
   * 
   * @param roleId
   * @return
   */
  public Set<String> getResourceNamesByRole(Long roleId);

  /**
   * 按照角色查找资源
   * 
   * @param scope
   * @return
   */
  public Set<String> getResourceNamesByScope(Resource.Scope scope);

  /**
   * 角色内对应的资源
   * 
   * @param role
   * @return
   */
  public List<Resource> getResources(Role role);

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
   * @return
   */
  public List<Permission> getPermissions(User user);

  /**
   * 依据默认深度得到角色拥有的权限
   * 
   * @param role
   * @return
   */
  public List<Permission> getPermissions(Role role);

  /**
   * 授权
   * 
   * @param role
   * @param resources
   */
  public void authorize(Role role, Set<Resource> resources);

  /**
   * @param userService
   */
  public void setUserService(UserService userService);

  /**
   * @return
   */
  public UserService getUserService();

  /**
   * Extract Resource from uri
   * 
   * @param uri
   *          with out context path
   * @return
   */
  public String extractResource(String uri);
}
