/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General  License, Version 3.
 * http://www.gnu.org/licenses
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
public interface Role extends LongIdTimeEntity, HierarchyEntity<Role, Long>, Comparable<Role>,Principal {

  /** 匿名角色id */
  static final long ANONYMOUS_ID = 1;

  /** 所有用户所在的公共角色id */
  static final long ANYONE_ID = 2;

  /**
   * 代码
   * 
   */
  String getCode();

  /**
   * 名称
   * 
   */
  String getName();

  /**
   * 关联的系统用户
   * 
   */
  Set<Member> getMembers();

  /**
   * Owner
   * 
   */
  User getOwner();

  /**
   * 状态
   * 
   */
  boolean isEnabled();

  /**
   * 备注
   * 
   */
  String getRemark();

  /**
   * 设置备注
   * 
   * @param remark
   */
  void setRemark(String remark);

  /**
   * 角色所在的层次
   * 
   */
  int getDepth();

  boolean isDynamic();

}
