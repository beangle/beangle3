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
   * <p>
   * getStatement.
   * </p>
   * 
   * @param <T> a T object.
   * @return a {@link java.lang.String} object.
   */
  public String getStatement();

  /**
   * <p>
   * getParams.
   * </p>
   * 
   * @return a {@link java.util.Map} object.
   */
  public Map<String, Object> getParams();

  /**
   * <p>
   * isCacheable.
   * </p>
   * 
   * @return a boolean.
   */
  public boolean isCacheable();

  /**
   * <p>
   * getLang.
   * </p>
   * 
   * @return a {@link org.beangle.commons.dao.query.Lang} object.
   */
  public Lang getLang();
}
