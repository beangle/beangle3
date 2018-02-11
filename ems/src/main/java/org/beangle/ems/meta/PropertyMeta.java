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
package org.beangle.ems.meta;

import org.beangle.commons.entity.Entity;

/**
 * 属性元信息
 * </p>
 * 记录实体的属性元信息描述。
 */
public interface PropertyMeta extends Entity<Integer> {

  /**
   * 获得实体元信息
   *
   * @return 实体元信息
   */
  EntityMeta getMeta();

  /**
   * 设置实体元信息
   *
   * @param meta 实体元信息
   */
  void setMeta(EntityMeta meta);

  /**
   * 获得属性名
   *
   * @return 属性名
   */
  String getName();

  /**
   * 设置属性名
   *
   * @param name 属性名
   */
  void setName(String name);

  /**
   * 获得属性类型
   *
   * @return 属性类型
   */
  String getType();

  /**
   * 设置属性类型
   *
   * @param type 属性类型
   */
  void setType(String type);

  /**
   * 获得备注
   *
   * @return 备注
   */
  String getComments();

  /**
   * 设置备注
   *
   * @param comments 备注
   */
  void setComments(String comments);
}
