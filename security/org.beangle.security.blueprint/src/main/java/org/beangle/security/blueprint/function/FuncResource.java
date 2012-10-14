/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
    PUBLIC(0),
    /** 受保护的公有资源 */
    PROTECTED(1),
    /** 受保护的私有资源 */
    PRIVATE(2);
    int value;

    static {
      Converters.registerEnum(Scope.class);
    }

    private Scope(int value) {
      this.value = value;
    }
  }
}
