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
   * 
   */
  public String getCode();

  /**
   * 设置代码
   * 
   * @param code
   */
  public void setCode(String code);

  /**
   * 菜单的层级，从1开始
   * 
   */
  public int getDepth();

  /**
   * 菜单名称
   * 
   */
  public String getName();

  /**
   * 设置菜单名称
   * 
   * @param name
   */
  public void setName(String name);

  /**
   * 菜单标题
   * 
   */
  public String getTitle();

  /**
   * 设置菜单标题
   * 
   * @param title
   */
  public void setTitle(String title);

  public String getEntry();

  public void setEntry(String entry);

  public String getRemark();

  public void setRemark(String remark);

  public Set<FuncResource> getResources();

  public void setResources(Set<FuncResource> resources);

  public boolean isEnabled();

  public void setEnabled(boolean enabled);

  public MenuProfile getProfile();

  public void setProfile(MenuProfile profile);
}
