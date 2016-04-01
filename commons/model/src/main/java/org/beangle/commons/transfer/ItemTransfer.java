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
package org.beangle.commons.transfer;

import java.util.Map;

/**
 * 基于行的转换器
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface ItemTransfer extends Transfer {

  /**
   * 返回要转换的各个属性的名称
   * 
   * @return an array of {@link java.lang.String} objects.
   */
  String[] getAttrs();

  /**
   * 将当前正在转换的数据转换成map[attr,value]
   * 
   * @return a {@link java.util.Map} object.
   */
  Map<String, Object> getCurData();
}
