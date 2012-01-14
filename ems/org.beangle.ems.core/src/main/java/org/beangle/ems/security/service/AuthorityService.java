/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.service;

import java.util.List;
import java.util.Set;

import org.beangle.ems.security.Authority;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.Resource;
import org.beangle.ems.security.User;

/**
 * 用户用户组权限管理服务接口. 权限实体@see <code>Authority</code> 系统资源实体@see <code>Resource</code> 系统功能点实体@see
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
	 * 按照用户组查找资源
	 * 
	 * @param groupId
	 * @return
	 */
	public Set<String> getResourceNamesByGroup(Long groupId);
	/**
	 * 按照用户组查找资源
	 * 
	 * @param groupId
	 * @return
	 */
	public Set<String> getResourceNamesByScope(Resource.Scope groupId);
	/**
	 * 用户组内对应的资源
	 * 
	 * @param group
	 * @return
	 */
	public List<Resource> getResources(Group group);

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
	public List<Authority> getAuthorities(User user);

	/**
	 * 依据默认深度得到用户组拥有的权限
	 * 
	 * @param group
	 * @return
	 */
	public List<Authority> getAuthorities(Group group);

	/**
	 * 依据资源和用户取得对应的权限
	 * 
	 * @param user
	 * @param resource
	 * @return
	 */
	public Authority getAuthority(User user, Resource resource);

	/**
	 * 依据资源和用户组取得对应的权限
	 * 
	 * @param userId
	 * @param resource
	 * @return
	 */
	public Authority getAuthority(Group group, Resource resource);

	/**
	 * 授权
	 * 
	 * @param ao
	 * @param resources
	 */
	public void authorize(Group group, Set<Resource> resources);

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
	 *            with out context path
	 * @return
	 */
	public String extractResource(String uri);
}
