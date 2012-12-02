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
package org.beangle.security.blueprint.function;

import org.beangle.commons.bean.converters.Converters;
import org.beangle.security.blueprint.Resource;

/**
 * 系统功能资源
 * 
 * @author chaostone
 * @since 3.0.0
 */
public interface FuncResource extends Resource {

  /**
   * 资源范围
   */
  Scope getScope();

  /**
   * 资源可见范围
   * 
   * @author chaostone
   */
  static enum Scope {

    /** 不受保护的公共资源 */
    Public(0),
    /** 受保护的公有资源 */
    Protected(1),
    /** 受保护的私有资源 */
    Private(2);

    int value;

    static {
      Converters.registerEnum(Scope.class);
    }

    private Scope(int value) {
      this.value = value;
    }
  }
}
