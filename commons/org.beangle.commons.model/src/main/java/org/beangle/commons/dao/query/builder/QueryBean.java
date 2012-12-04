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
package org.beangle.commons.dao.query.builder;

import java.util.Map;

import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.dao.query.Lang;
import org.beangle.commons.dao.query.LimitQuery;
import org.beangle.commons.dao.query.Query;
import org.beangle.commons.lang.Strings;

/**
 * <p>
 * QueryBean class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class QueryBean<T> implements LimitQuery<T> {

  private Lang lang;

  private String statement;

  private String countStatement;

  private PageLimit limit;

  private boolean cacheable;

  private Map<String, Object> params;

  /**
   * Returns count query {@link org.beangle.commons.dao.query.Query}.
   */
  public Query<T> getCountQuery() {
    if (Strings.isEmpty(countStatement)) { return null; }
    QueryBean<T> bean = new QueryBean<T>();
    bean.setStatement(countStatement);
    bean.setLang(lang);
    bean.setParams(params);
    bean.setCacheable(cacheable);
    return bean;
  }

  /**
   * Returns statement.
   */
  public String getStatement() {
    return statement;
  }

  /**
   * <p>
   * Getter for the field <code>countStatement</code>.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getCountStatement() {
    return countStatement;
  }

  /**
   * <p>
   * Getter for the field <code>limit</code>.
   * </p>
   * 
   * @return a {@link org.beangle.commons.collection.page.PageLimit} object.
   */
  public PageLimit getLimit() {
    return limit;
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
   * Setter for the field <code>statement</code>.
   * </p>
   * 
   * @param statement a {@link java.lang.String} object.
   */
  public void setStatement(String statement) {
    this.statement = statement;
  }

  /**
   * <p>
   * Setter for the field <code>countStatement</code>.
   * </p>
   * 
   * @param countStatement a {@link java.lang.String} object.
   */
  public void setCountStatement(String countStatement) {
    this.countStatement = countStatement;
  }

  /**
   * <p>
   * Setter for the field <code>limit</code>.
   * </p>
   * 
   * @param limit a {@link org.beangle.commons.collection.page.PageLimit} object.
   */
  public void setLimit(PageLimit limit) {
    this.limit = limit;
  }

  /** {@inheritDoc} */
  public LimitQuery<T> limit(PageLimit limit) {
    this.limit = new PageLimit(limit.getPageNo(), limit.getPageSize());
    return this;
  }

  /**
   * <p>
   * Setter for the field <code>cacheable</code>.
   * </p>
   * 
   * @param cacheable a boolean.
   */
  public void setCacheable(boolean cacheable) {
    this.cacheable = cacheable;
  }

  /**
   * <p>
   * Setter for the field <code>params</code>.
   * </p>
   * 
   * @param params a {@link java.util.Map} object.
   */
  public void setParams(Map<String, Object> params) {
    this.params = params;
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
   * Getter for the field <code>lang</code>.
   * </p>
   * 
   * @return a {@link org.beangle.commons.dao.query.Lang} object.
   */
  public Lang getLang() {
    return lang;
  }

  /**
   * <p>
   * Setter for the field <code>lang</code>.
   * </p>
   * 
   * @param lang a {@link org.beangle.commons.dao.query.Lang} object.
   */
  public void setLang(Lang lang) {
    this.lang = lang;
  }

}
