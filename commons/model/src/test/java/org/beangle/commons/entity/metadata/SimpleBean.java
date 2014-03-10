/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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
package org.beangle.commons.entity.metadata;

import org.beangle.commons.entity.pojo.NumberIdObject;

/**
 * @comment 测试bean
 * @author chaostone
 * @version $Id: SimpleBean.java Jul 30, 2011 12:53:47 AM chaostone $
 */
@SuppressWarnings("serial")
public class SimpleBean extends NumberIdObject<Long> {

  /**
   * @comment 名称
   * @author chaostone
   */
  String name;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

}
