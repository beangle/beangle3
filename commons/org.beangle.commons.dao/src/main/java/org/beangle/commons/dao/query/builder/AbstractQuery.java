/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.dao.query.builder;

import java.util.Map;

import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.dao.query.QueryBuilder;

/**
 * 抽象查询
 * 
 * @author chaostone
 * @version $Id: $
 */
public abstract class AbstractQuery<T> implements QueryBuilder<T> {
  /** query 查询语句 */
  protected String queryStr;

  /** count 计数语句 */
  protected String countStr;

  /** 分页 */
  protected PageLimit limit;

  /** 参数 */
  protected Map<String, Object> params;

  /** 缓存查询结果 */
  protected boolean cacheable = false;

  /**
   * <p>
   * Getter for the field <code>limit</code>.
   * </p>
   * 
   * @param <T> a T object.
   * @return a {@link org.beangle.commons.collection.page.PageLimit} object.
   */
  public PageLimit getLimit() {
    return limit;
  }

  /**
   * <p>
   * Setter for the field <code>limit</code>.
   * </p>
   * 
   * @param limit a {@link org.beangle.commons.collection.page.PageLimit} object.
   */
  public void setLimit(final PageLimit limit) {
    this.limit = limit;
  }

  /**
   * <p>
   * Getter for the field <code>params</code>.
   * </p>
   * 
   * @return a {@link java.util.Map} object.
   */
  public Map<String, Object> getParams() {
    return params;
  }

  /**
   * <p>
   * Getter for the field <code>countStr</code>.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getCountStr() {
    return countStr;
  }

  /**
   * <p>
   * Setter for the field <code>countStr</code>.
   * </p>
   * 
   * @param countStr a {@link java.lang.String} object.
   */
  public void setCountStr(final String countStr) {
    this.countStr = countStr;
  }

  /**
   * <p>
   * Getter for the field <code>queryStr</code>.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getQueryStr() {
    return queryStr;
  }

  /**
   * <p>
   * Setter for the field <code>queryStr</code>.
   * </p>
   * 
   * @param queryStr a {@link java.lang.String} object.
   */
  public void setQueryStr(final String queryStr) {
    this.queryStr = queryStr;
  }

  /**
   * <p>
   * Setter for the field <code>params</code>.
   * </p>
   * 
   * @param params a {@link java.util.Map} object.
   */
  public void setParams(final Map<String, Object> params) {
    this.params = params;
  }

  /**
   * <p>
   * toQueryString.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public abstract String toQueryString();

  /**
   * <p>
   * toCountString.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String toCountString() {
    return countStr;
  }

  /**
   * <p>
   * isCacheable.
   * </p>
   * 
   * @return a boolean.
   */
  public boolean isCacheable() {
    return cacheable;
  }

  /**
   * <p>
   * Setter for the field <code>cacheable</code>.
   * </p>
   * 
   * @param cacheable a boolean.
   */
  public void setCacheable(final boolean cacheable) {
    this.cacheable = cacheable;
  }

}
