/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.data;

import org.beangle.security.blueprint.Permission;

/**
 * 数据访问授权
 * 
 * @author chaostone
 * @since 3.0.0
 */
public interface DataPermission extends Permission {

  /**
   * 对应的数据资源
   */
  DataResource getResource();

  /**
   * 允许访问的部分
   * 
   */
  String getAttrs();

  /**
   * 资源过滤器
   * 
   */
  String getFilters();

}
