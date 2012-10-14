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
   * 设置代码
   * 
   * @param code
   */
  void setCode(String code);

  /**
   * 菜单的层级，从1开始
   */
  int getDepth();

  /**
   * 菜单名称
   */
  String getName();

  /**
   * 设置菜单名称
   * 
   * @param name
   */
  void setName(String name);

  /**
   * 菜单标题
   */
  String getTitle();

  /**
   * 设置菜单标题
   * 
   * @param title
   */
  void setTitle(String title);

  String getEntry();

  void setEntry(String entry);

  String getRemark();

  void setRemark(String remark);

  Set<FuncResource> getResources();

  void setResources(Set<FuncResource> resources);

  boolean isEnabled();

  void setEnabled(boolean enabled);

  MenuProfile getProfile();

  void setProfile(MenuProfile profile);
}
