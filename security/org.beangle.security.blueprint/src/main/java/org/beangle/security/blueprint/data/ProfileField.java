/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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
package org.beangle.security.blueprint.data;

import org.beangle.commons.entity.Entity;

/**
 * 用户自定义属性
 * 
 * @author chaostone
 * @since 2011-09-22
 */
public interface ProfileField extends Entity<Integer> {

  /**
   * 名称
   */
  String getName();

  /**
   * 标题
   */
  String getTitle();

  /**
   * 值类型
   */
  DataType getType();

  /**
   * 数据源提供者
   */
  String getSource();

  /**
   * 是否为集合类型
   */
  boolean isMultiple();

}
