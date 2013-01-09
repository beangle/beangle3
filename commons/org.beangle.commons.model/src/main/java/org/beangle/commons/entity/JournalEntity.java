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
package org.beangle.commons.entity;

import java.util.Date;

/**
 * Jounal Entity
 * 
 * @author chaostone
 * @since 3.1.0
 */
public interface JournalEntity {
  /**
   * 获得起始时间
   * 
   * @return 起始日期
   */
  Date getBeginOn();

  /**
   * 设置起始日期
   * 
   * @param beginAt
   *          起始日期
   */
  void setBeginOn(Date beginOn);

  /**
   * 获得结束日期
   * 
   * @return 结束日期
   */
  Date getEndOn();

  /**
   * 设置结束日期
   * 
   * @param endAt
   *          结束日期
   */
  void setEndOn(Date endOn);

  /**
   * 返回备注
   */
  String getRemark();
}
