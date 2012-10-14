/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.function;

import org.beangle.security.blueprint.Permission;

/**
 * 功能权限
 * 
 * @author chaostone
 * @since 3.0.0
 */
public interface FuncPermission extends Permission {

  /**
   * 系统资源
   */
  FuncResource getResource();

}
