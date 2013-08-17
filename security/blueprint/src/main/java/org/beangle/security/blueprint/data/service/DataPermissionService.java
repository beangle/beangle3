/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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
package org.beangle.security.blueprint.data.service;

import java.util.List;

import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.data.DataPermission;
import org.beangle.security.blueprint.data.Profile;
import org.beangle.security.blueprint.data.ProfileField;
import org.beangle.security.blueprint.data.RoleProfile;
import org.beangle.security.blueprint.data.UserProfile;

/**
 * 资源访问约束服务
 * 
 * @author chaostone
 */
public interface DataPermissionService {
  /**
   * 获得该权限范围适用的数据权限
   * 
   * @param userId 访问用户Id 不能为空
   * @param dataResource 数据资源名 不能为空
   * @param functionResource 数据资源名 不能为空
   */
  DataPermission getPermission(Long userId, String dataResource, String functionResource);

  /**
   * 应用数据权限
   * 
   * @param builder
   * @param permission
   * @param profile
   */
  void apply(OqlBuilder<?> builder, DataPermission permission, UserProfile profile);

  /**
   * Get field enumerated values.
   * 
   * @param field
   * @param profile
   */
  Object getPropertyValue(ProfileField field, Profile profile);

  /**
   * 查找用户对应的数据配置
   * 
   * @param user
   */
  List<UserProfile> getUserProfiles(User user);

  /**
   * Search Role profile
   * 
   * @param role
   */
  RoleProfile getRoleProfile(Role role);

  /**
   * Search field values
   * 
   * @param field
   * @param keys
   */
  List<?> getFieldValues(ProfileField field, Object... keys);

  /**
   * Search field
   * 
   * @param fieldName
   */
  ProfileField getProfileField(String fieldName);

}
