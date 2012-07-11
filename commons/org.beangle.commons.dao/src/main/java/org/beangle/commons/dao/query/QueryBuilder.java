/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.dao.query;

import java.util.Map;

import org.beangle.commons.collection.page.PageLimit;

/**
 * <p>
 * QueryBuilder interface.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface QueryBuilder<T>{

  /**
   * <p>
   * build.
   * </p>
   * 
   * @param <T> a T object.
   * @return a {@link org.beangle.commons.dao.query.Query} object.
   */
  public Query<T> build();

  /**
   * <p>
   * limit.
   * </p>
   * 
   * @param limit a {@link org.beangle.commons.collection.page.PageLimit} object.
   * @return a {@link org.beangle.commons.dao.query.QueryBuilder} object.
   */
  public QueryBuilder<T> limit(PageLimit limit);

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
   * params.
   * </p>
   * 
   * @param newParams a {@link java.util.Map} object.
   * @return a {@link org.beangle.commons.dao.query.QueryBuilder} object.
   */
  public QueryBuilder<T> params(Map<String, Object> newParams);
}
