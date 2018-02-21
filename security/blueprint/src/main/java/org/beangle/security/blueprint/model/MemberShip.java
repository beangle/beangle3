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
package org.beangle.security.blueprint.model;

public enum MemberShip {
  /**
   * just role member
   * 成员关系可以等价于读权限
   */
  MEMBER,
  /**
   * Can grant/revoke role to/from member
   */
  GRANTER,
  /**
   * manage role perperties and permissions
   * 对组可管理意为<br>
   * <ul>
   * <li>建立下级组
   * <li>移动下级组顺序
   * <li>功能权限
   * <li>直接成员
   * </ul>
   * 不能改变组的
   * <ul>
   * <li>删除组
   * <li>重命名.
   * </ul>
   * 只要拥有上级组的管理权限，才能变更这些，这些称之为写权限(admin)。
   * 拥有某组的管理权限，不意味拥有下级组的管理权限。新建组情况自动授予该组的其他管理者管理权限。
   */
  MANAGER;
}
