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
 * 实体元信息
 * </p>
 * 记录实体信息的元信息描述。
 *
 * @author chaostone
 * @since 2.3.0
 */
public interface EntityMeta extends Entity<Integer> {
  /**
   * 获得实体名称
   *
   * @return 实体名称
   */
  String getName();

  /**
   * 设置实体名称
   *
   * @param name 实体名称
   */
  void setName(String name);

  /**
   * 获得实体说明
   *
   * @return 实体说明
   */
  String getComments();

  /**
   * 设置实体说明
   *
   * @param comments 实体说明
   */
  void setComments(String comments);

  /**
   * 获得实体备注
   *
   * @return 实体备注
   */
  String getRemark();

  /**
   * 设置实体备注
   *
   * @param remark 实体备注
   */
  void setRemark(String remark);
}
