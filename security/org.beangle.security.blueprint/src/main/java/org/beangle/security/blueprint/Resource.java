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

import org.beangle.commons.entity.Entity;

/**
 * 系统资源.<br>
 * 
 * @author chaostone 2008-7-28
 */
public interface Resource extends Entity<Integer> {

  /** 资源的所有部分 */
  static final String AllParts = "*";

  /** 允许所有操作 */
  static final String AllActions = "*";

  /**
   * 资源名称
   */
  String getName();

  /**
   * 资源标题
   */
  String getTitle();

  /**
   * 允许的操作
   */
  String getActions();

  /**
   * 资源状态
   */
  boolean isEnabled();

  /**
   * 返回资源描述
   */
  String getRemark();

}
