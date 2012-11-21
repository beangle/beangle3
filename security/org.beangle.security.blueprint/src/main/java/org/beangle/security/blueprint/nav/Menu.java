/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.nav;

import java.util.Set;

import org.beangle.commons.entity.pojo.HierarchyEntity;
import org.beangle.commons.entity.pojo.LongIdEntity;
import org.beangle.security.blueprint.function.FuncResource;

/**
 * 系统菜单
 * 
 * @author chaostone
 */
public interface Menu extends LongIdEntity, HierarchyEntity<Menu, Long>, Comparable<Menu> {

  /**
   * 同级菜单索引号
   */
  String getCode();

  /**
   * 菜单的层级，从1开始
   */
  int getDepth();

  /**
   * 菜单名称
   */
  String getName();

  /**
   * 菜单标题
   */
  String getTitle();

  String getEntry();

  String getRemark();

  Set<FuncResource> getResources();

  boolean isEnabled();

  MenuProfile getProfile();

}
