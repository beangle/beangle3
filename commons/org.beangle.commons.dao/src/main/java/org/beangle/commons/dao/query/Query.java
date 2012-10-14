/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.dao.query;

import java.util.Map;

/**
 * 数据查询接口
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface Query<T> {

  /**
   * Returns query statement.
   */
  String getStatement();

  /**
   * <p>
   * getParams.
   * </p>
   * 
   * @return a {@link java.util.Map} object.
   */
  Map<String, Object> getParams();

  /**
   * <p>
   * isCacheable.
   * </p>
   * 
   * @return a boolean.
   */
  boolean isCacheable();

  /**
   * <p>
   * getLang.
   * </p>
   * 
   * @return a {@link org.beangle.commons.dao.query.Lang} object.
   */
  Lang getLang();
}
