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
package org.beangle.security.blueprint;

import java.security.Principal;
import java.util.Set;

import org.beangle.commons.entity.pojo.HierarchyEntity;
import org.beangle.commons.entity.pojo.LongIdTimeEntity;

/**
 * 系统角色的基本信息
 * 
 * @author chaostone 2005-9-26
 */
public interface Role extends LongIdTimeEntity, HierarchyEntity<Role, Long>, Comparable<Role>, Principal {

  /** 匿名角色id */
  static final long ANONYMOUS_ID = 1;

  /** 所有用户所在的公共角色id */
  static final long ANYONE_ID = 2;

  /**
   * 代码
   */
  String getCode();

  /**
   * 名称
   */
  String getName();

  /**
   * 关联的系统用户
   */
  Set<Member> getMembers();

  /**
   * Owner
   */
  User getOwner();

  /**
   * 状态
   */
  boolean isEnabled();

  /**
   * 备注
   */
  String getRemark();

  /**
   * 角色所在的层次
   */
  int getDepth();

  /**
   * Return true is the role is dynamic
   */
  boolean isDynamic();

}
