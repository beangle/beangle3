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

import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.NumberIdHierarchyObject;
import org.beangle.commons.lang.Strings;

/**
 * 系统菜单
 *
 * @author chaostone
 */
public class Menu extends NumberIdHierarchyObject<Menu, Integer> implements Comparable<Menu> {

  private static final long serialVersionUID = 3864556621041443066L;

  /** 菜单名称 */
  private String name;

  /** 菜单标题 */
  private String title;

  /** 菜单入口 */
  private FuncResource entry;

  /** 菜单备注 */
  private String remark;

  /** 引用资源集合 */
  private Set<FuncResource> resources = CollectUtils.newHashSet();

  /** 是否启用 */
  private boolean enabled = true;

  /** 菜单配置 */
  private MenuProfile profile;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public FuncResource getEntry() {
    return entry;
  }

  public void setEntry(FuncResource entry) {
    this.entry = entry;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Set<FuncResource> getResources() {
    return resources;
  }

  public void setResources(Set<FuncResource> resources) {
    this.resources = resources;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public MenuProfile getProfile() {
    return profile;
  }

  public void setProfile(MenuProfile profile) {
    this.profile = profile;
  }

  public String getDescription() {
    return Strings.concat("[", indexno, "]", title);
  }

  @Override
  public String toString() {
    return getDescription();
  }

}
