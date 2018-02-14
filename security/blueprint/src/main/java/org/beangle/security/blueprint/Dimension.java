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
package org.beangle.security.blueprint;

import org.beangle.commons.entity.Entity;

/**
 * 自定义属性
 *
 * @author chaostone
 * @since 2011-09-22
 */
public interface Dimension extends Entity<Integer> {

  /**
   * 名称
   */
  String getName();

  /**
   * 标题
   */
  String getTitle();

  /**
   * 数据源提供者
   */
  String getSource();

  /**
   * 是否为集合类型
   */
  boolean isMultiple();

  /**
   * 是否必填
   *
   * @return
   */
  boolean isRequired();

  /**
   * 类型
   *
   * @return
   */
  String getTypeName();

  /**
   * 主键
   *
   * @return
   */
  String getKeyName();

  /**
   * 其他属性
   *
   * @return
   */
  String getProperties();

}
