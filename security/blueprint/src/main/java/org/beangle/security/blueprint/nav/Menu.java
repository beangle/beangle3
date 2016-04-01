/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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
package org.beangle.security.blueprint.nav;

import java.util.Set;

import org.beangle.commons.entity.HierarchyEntity;
import org.beangle.security.blueprint.function.FuncResource;

/**
 * 系统菜单
 * 
 * @author chaostone
 */
public interface Menu extends HierarchyEntity<Menu, Integer>, Comparable<Menu> {
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

  FuncResource getEntry();

  String getRemark();

  Set<FuncResource> getResources();

  boolean isEnabled();

  MenuProfile getProfile();

}
