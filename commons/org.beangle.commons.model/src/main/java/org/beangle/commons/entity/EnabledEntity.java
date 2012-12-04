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

/**
 * 能够启用和禁用的实体
 * 
 * @author chaostone
 * @version $Id: StatusEntity.java Jun 25, 2011 5:05:07 PM chaostone $
 */
public interface EnabledEntity {

  /**
   * 查询是否启用
   * 
   * @return 是否启用
   */
  boolean isEnabled();

  /**
   * 设置是否启用
   * 
   * @param enabled
   *          是否启用
   */
  void setEnabled(boolean enabled);

}
