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
package org.beangle.security.blueprint.data;

import org.beangle.security.blueprint.Resource;

/**
 * 数据资源
 *
 * @author chaostone
 * @since 3.0.0
 */
public interface DataResource extends Resource {

  /** 允许对数据的CRUD操作 */
  /** 创建 */
  static final String CreateAction = "insert";

  /** 读取 */
  static final String ReadAction = "select";

  /** 删除 */
  static final String DeleteAction = "delete";

  /** 更新 */
  static final String UpdateAction = "update";

  /** 写操作(包括读以外的其他操作) */
  static final String WriteAction = CreateAction + "," + UpdateAction + "," + DeleteAction;

}
