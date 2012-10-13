/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.dao.query.builder;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.Order;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.dao.query.Lang;
import org.beangle.commons.lang.Assert;
import org.beangle.commons.lang.Strings;

/**
 * sql查询
 * 
 * @author chaostone
 * @version $Id: $
 */
public class SqlBuilder extends AbstractQueryBuilder<Object[]> {
  /**
   * <p>
   * sql.
   * </p>
   * 
   * @param queryStr a {@link java.lang.String} object.
   * @return a {@link org.beangle.commons.dao.query.builder.SqlBuilder} object.
   */
  public static SqlBuilder sql(final String queryStr) {
    SqlBuilder sqlQuery = new SqlBuilder();
    sqlQuery.statement = queryStr;
    return sqlQuery;
  }

  /**
   * <p>
   * genCountStatement.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  protected String genCountStatement() {
    return "select count(*) from (" + genQueryStatement(false) + ")";
  }

  /** {@inheritDoc} */
  @Override
  protected Lang getLang() {
    return Lang.SQL;
  }

  /**
   * <p>
   * alias.
   * </p>
   * 
   * @param alias a {@link java.lang.String} object.
   * @return a {@link org.beangle.commons.dao.query.builder.SqlBuilder} object.
   */
  public SqlBuilder alias(final String alias) {
    this.alias = alias;
    return this;
  }

  /**
   * <p>
   * join.
   * </p>
   * 
   * @param path a {@link java.lang.String} object.
   * @param alias a {@link java.lang.String} object.
   * @return a {@link org.beangle.commons.dao.query.builder.SqlBuilder} object.
   */
  public SqlBuilder join(final String path, final String alias) {
    from += " join " + path + " " + alias;
    return this;
  }

  /**
   * <p>
   * join.
   * </p>
   * 
   * @param joinMode a {@link java.lang.String} object.
   * @param path a {@link java.lang.String} object.
   * @param alias a {@link java.lang.String} object.
   * @return a {@link org.beangle.commons.dao.query.builder.SqlBuilder} object.
   */
  public SqlBuilder join(final String joinMode, final String path, final String alias) {
    from += " " + joinMode + " join " + path + " " + alias;
    return this;
  }

  /** {@inheritDoc} */
  public SqlBuilder params(final Map<String, Object> params) {
    this.params = CollectUtils.newHashMap(params);
    ;
    return this;
  }

  /**
   * <p>
   * param.
   * </p>
   * 
   * @param name a {@link java.lang.String} object.
   * @param value a {@link java.lang.Object} object.
   * @return a {@link org.beangle.commons.dao.query.builder.SqlBuilder} object.
   */
  public SqlBuilder param(String name, Object value) {
    if (null == this.params) {
      params = new HashMap<String, Object>();
    }
    params.put(name, value);
    return this;
  }

  /** {@inheritDoc} */
  public SqlBuilder limit(final PageLimit limit) {
    this.limit = limit;
    return this;
  }

  /**
   * <p>
   * limit.
   * </p>
   * 
   * @param pageNo a int.
   * @param pageSize a int.
   * @return a {@link org.beangle.commons.dao.query.builder.SqlBuilder} object.
   */
  public SqlBuilder limit(final int pageNo, final int pageSize) {
    this.limit = new PageLimit(pageNo, pageSize);
    return this;
  }

  /**
   * <p>
   * cacheable.
   * </p>
   * 
   * @return a {@link org.beangle.commons.dao.query.builder.SqlBuilder} object.
   */
  public SqlBuilder cacheable() {
    this.cacheable = true;
    return this;
  }

  /**
   * <p>
   * cacheable.
   * </p>
   * 
   * @param cacheable a boolean.
   * @return a {@link org.beangle.commons.dao.query.builder.SqlBuilder} object.
   */
  public SqlBuilder cacheable(final boolean cacheable) {
    this.cacheable = cacheable;
    return this;
  }

  /**
   * <p>
   * where.
   * </p>
   * 
   * @param condition a {@link org.beangle.commons.dao.query.builder.Condition} object.
   * @return a {@link org.beangle.commons.dao.query.builder.SqlBuilder} object.
   */
  public SqlBuilder where(final Condition condition) {
    conditions.add(condition);
    return this;
  }

  /**
   * <p>
   * where.
   * </p>
   * 
   * @param content a {@link java.lang.String} object.
   * @return a {@link org.beangle.commons.dao.query.builder.SqlBuilder} object.
   */
  public SqlBuilder where(final String content) {
    return where(new Condition(content));
  }

  /**
   * <p>
   * where.
   * </p>
   * 
   * @param content a {@link java.lang.String} object.
   * @param param1 a {@link java.lang.Object} object.
   * @return a {@link org.beangle.commons.dao.query.builder.SqlBuilder} object.
   */
  public SqlBuilder where(final String content, Object param1) {
    return where(new Condition(content, param1));
  }

  /**
   * <p>
   * where.
   * </p>
   * 
   * @param content a {@link java.lang.String} object.
   * @param param1 a {@link java.lang.Object} object.
   * @param param2 a {@link java.lang.Object} object.
   * @return a {@link org.beangle.commons.dao.query.builder.SqlBuilder} object.
   */
  public SqlBuilder where(final String content, Object param1, Object param2) {
    return where(new Condition(content, param1, param2, null));
  }

  /**
   * <p>
   * where.
   * </p>
   * 
   * @param content a {@link java.lang.String} object.
   * @param param1 a {@link java.lang.Object} object.
   * @param param2 a {@link java.lang.Object} object.
   * @param param3 a {@link java.lang.Object} object.
   * @return a {@link org.beangle.commons.dao.query.builder.SqlBuilder} object.
   */
  public SqlBuilder where(final String content, Object param1, Object param2, Object param3) {
    return where(new Condition(content, param1, param2, param3));
  }

  /**
   * 添加一组条件<br>
   * query中不能添加条件集合作为一个条件,因此这里命名没有采用有区别性的addAll
   * 
   * @param cons a {@link java.util.Collection} object.
   * @return a {@link org.beangle.commons.dao.query.builder.SqlBuilder} object.
   */
  public SqlBuilder where(final Collection<Condition> cons) {
    conditions.addAll(cons);
    return this;
  }

  /**
   * <p>
   * orderBy.
   * </p>
   * 
   * @param orderBy a {@link java.lang.String} object.
   * @return a {@link org.beangle.commons.dao.query.builder.SqlBuilder} object.
   */
  public SqlBuilder orderBy(final String orderBy) {
    this.orders.addAll(Order.parse(orderBy));
    return this;
  }

  /**
   * <p>
   * orderBy.
   * </p>
   * 
   * @param order a {@link org.beangle.commons.collection.Order} object.
   * @return a {@link org.beangle.commons.dao.query.builder.SqlBuilder} object.
   */
  public SqlBuilder orderBy(final Order order) {
    if (null != order) {
      this.orders.add(order);
    }
    return this;
  }

  /**
   * <p>
   * cleanOrders.
   * </p>
   * 
   * @return a {@link org.beangle.commons.dao.query.builder.SqlBuilder} object.
   */
  public SqlBuilder clearOrders() {
    this.orders.clear();
    return this;
  }

  /**
   * <p>
   * orderBy.
   * </p>
   * 
   * @param orders a {@link java.util.List} object.
   * @return a {@link org.beangle.commons.dao.query.builder.SqlBuilder} object.
   */
  public SqlBuilder orderBy(final List<Order> orders) {
    if (null != orders) {
      this.orders.addAll(orders);
    }
    return this;
  }

  /**
   * <p>
   * select.
   * </p>
   * 
   * @param what a {@link java.lang.String} object.
   * @return a {@link org.beangle.commons.dao.query.builder.SqlBuilder} object.
   */
  public SqlBuilder select(final String what) {
    if (null == what) {
      this.select = null;
    } else {
      if (Strings.contains(what.toLowerCase(), "select")) {
        this.select = what;
      } else {
        this.select = "select " + what;
      }
    }
    return this;
  }

  /**
   * <p>
   * newFrom.
   * </p>
   * 
   * @param from a {@link java.lang.String} object.
   * @return a {@link org.beangle.commons.dao.query.builder.SqlBuilder} object.
   */
  public SqlBuilder newFrom(final String from) {
    if (null == from) {
      this.from = null;
    } else {
      if (Strings.contains(from.toLowerCase(), "from")) {
        this.from = from;
      } else {
        this.from = " from " + from;
      }
    }
    return this;
  }

  /**
   * <p>
   * groupBy.
   * </p>
   * 
   * @param what a {@link java.lang.String} object.
   * @return a {@link org.beangle.commons.dao.query.builder.SqlBuilder} object.
   */
  public SqlBuilder groupBy(final String what) {
    if (Strings.isNotEmpty(what)) groups.add(what);
    return this;
  }

  /**
   * <p>
   * Having subclause.
   * </p>
   * 
   * @param what having subclause
   * @return a {@link org.beangle.commons.dao.query.builder.OqlBuilder} object.
   */
  public SqlBuilder having(final String what) {
    Assert.isTrue(null != groups && !groups.isEmpty());
    if (Strings.isNotEmpty(what)) having = what;
    return this;
  }
}
