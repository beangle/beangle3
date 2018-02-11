/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.blueprint;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import org.beangle.commons.entity.EnabledEntity;
import org.beangle.commons.entity.Entity;
import org.beangle.commons.entity.TemporalOn;
import org.beangle.commons.entity.TimeEntity;

/**
 * 系统中所有用户的账号、权限、状态信息.
 * 
 * @author dell,chaostone 2005-9-26
 */
public interface User extends Entity<Long>, TimeEntity, TemporalOn, EnabledEntity, Principal {

  // 新建用户的缺省密码
  static final String DEFAULT_PASSWORD = "1";

  /**
   * 名称
   * 
   * @return user's name
   */
  String getCode();

  /**
   * 用户真实姓名
   */
  String getName();

  /**
   * 用户密码(不限制是明码还是密文)
   */
  String getPassword();

  /**
   * 对应角色成员
   */
  Set<RoleMember> getMembers();

  /**
   * 对应角色
   * 
   * @return 按照角色代码排序的role列表
   */
  List<Role> getRoles();

  /**
   * 查找符合相应profiles的角色
   * 
   * @param profiles
   * @return
   */
  List<Role> getRoles(List<Profile> profiles);

  /**
   * 用户对应的配置
   * 
   * @return
   */
  List<Profile> getProfiles();

  /**
   * 是否启用
   */
  boolean isEnabled();

  /**
   * 备注
   */
  String getRemark();

  /**
   * 账户是否过期
   */
  boolean isAccountExpired();

  /**
   * 是否密码过期
   */
  boolean isPasswordExpired();

}
