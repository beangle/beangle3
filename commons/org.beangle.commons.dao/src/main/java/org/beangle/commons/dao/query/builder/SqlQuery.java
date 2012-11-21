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

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.Order;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.dao.query.Lang;
import org.beangle.commons.dao.query.Query;
import org.beangle.commons.lang.Strings;

/**
 * sql查询
 * 
 * @author chaostone
 * @version $Id: $
 */
public class SqlQuery extends AbstractQuery<Object> {

  /** Constant <code>INNER_JOIN=" left join "</code> */
  public static final String INNER_JOIN = " left join ";

  /** Constant <code>OUTER_JOIN=" outer join "</code> */
  public static final String OUTER_JOIN = " outer join ";

  /** Constant <code>LEFT_OUTER_JOIN=" left outer join "</code> */
  public static final String LEFT_OUTER_JOIN = " left outer join ";

  /** Constant <code>RIGHT_OUTER_JOIN=" right outer join "</code> */
  public static final String RIGHT_OUTER_JOIN = " right outer join ";

  protected String select;

  protected String from;

  protected List<Condition> conditions = CollectUtils.newArrayList();

  protected List<Order> orders = CollectUtils.newArrayList();

  protected List<String> groups = CollectUtils.newArrayList();

  /**
   * <p>
   * Constructor for SqlQuery.
   * </p>
   */
  public SqlQuery() {
    super();
  }

  /**
   * <p>
   * Constructor for SqlQuery.
   * </p>
   * 
   * @param queryStr a {@link java.lang.String} object.
   */
  public SqlQuery(final String queryStr) {
    super();
    this.queryStr = queryStr;
  }

  /**
   * <p>
   * add.
   * </p>
   * 
   * @param condition a {@link org.beangle.commons.dao.query.builder.Condition} object.
   * @return a {@link org.beangle.commons.dao.query.builder.SqlQuery} object.
   */
  public SqlQuery add(final Condition condition) {
    conditions.add(condition);
    return this;
  }

  /**
   * 添加一组条件<br>
   * query中不能添加条件集合作为一个条件,因此这里命名没有采用有区别性的addAll
   * 
   * @param cons a {@link java.util.Collection} object.
   * @return a {@link org.beangle.commons.dao.query.builder.SqlQuery} object.
   */
  public SqlQuery add(final Collection<Condition> cons) {
    conditions.addAll(cons);
    return this;
  }

  /**
   * <p>
   * addOrder.
   * </p>
   * 
   * @param order a {@link org.beangle.commons.collection.Order} object.
   * @return a {@link org.beangle.commons.dao.query.builder.SqlQuery} object.
   */
  public SqlQuery addOrder(final Order order) {
    if (null != order) {
      this.orders.add(order);
    }
    return this;
  }

  /**
   * <p>
   * addOrder.
   * </p>
   * 
   * @param orders a {@link java.util.List} object.
   * @return a {@link org.beangle.commons.dao.query.builder.SqlQuery} object.
   */
  public SqlQuery addOrder(final List<Order> orders) {
    if (null != orders) {
      this.orders.addAll(orders);
    }
    return this;
  }

  /**
   * <p>
   * Getter for the field <code>select</code>.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getSelect() {
    return select;
  }

  /**
   * <p>
   * Setter for the field <code>select</code>.
   * </p>
   * 
   * @param select a {@link java.lang.String} object.
   */
  public void setSelect(final String select) {
    if (null == select) {
      this.select = null;
    } else {
      if (Strings.contains(select.toLowerCase(), "select")) {
        this.select = select;
      } else {
        this.select = "select " + select;
      }
    }
  }

  /**
   * <p>
   * Getter for the field <code>conditions</code>.
   * </p>
   * 
   * @return a {@link java.util.List} object.
   */
  public List<Condition> getConditions() {
    return conditions;
  }

  /**
   * <p>
   * Setter for the field <code>conditions</code>.
   * </p>
   * 
   * @param conditions a {@link java.util.List} object.
   */
  public void setConditions(final List<Condition> conditions) {
    this.conditions = conditions;
  }

  /**
   * <p>
   * Getter for the field <code>from</code>.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getFrom() {
    return from;
  }

  /**
   * <p>
   * Setter for the field <code>from</code>.
   * </p>
   * 
   * @param from a {@link java.lang.String} object.
   */
  public void setFrom(final String from) {
    if (null == from) {
      this.from = null;
    } else {
      if (Strings.contains(from.toLowerCase(), "from")) {
        this.from = from;
      } else {
        this.from = " from " + from;
      }
    }
  }

  /**
   * <p>
   * Getter for the field <code>orders</code>.
   * </p>
   * 
   * @return a {@link java.util.List} object.
   */
  public List<Order> getOrders() {
    return orders;
  }

  /**
   * <p>
   * Setter for the field <code>orders</code>.
   * </p>
   * 
   * @param orders a {@link java.util.List} object.
   */
  public void setOrders(final List<Order> orders) {
    this.orders = orders;
  }

  /**
   * <p>
   * Getter for the field <code>groups</code>.
   * </p>
   * 
   * @return a {@link java.util.List} object.
   */
  public List<String> getGroups() {
    return groups;
  }

  /**
   * <p>
   * Setter for the field <code>groups</code>.
   * </p>
   * 
   * @param groups a {@link java.util.List} object.
   */
  public void setGroups(final List<String> groups) {
    this.groups = groups;
  }

  /**
   * <p>
   * groupBy.
   * </p>
   * 
   * @param what a {@link java.lang.String} object.
   * @return a {@link org.beangle.commons.dao.query.builder.SqlQuery} object.
   */
  public SqlQuery groupBy(final String what) {
    if (Strings.isNotEmpty(what)) {
      groups.add(what);
    }
    return this;
  }

  /**
   * 生成查询语句（如果查询语句已经存在则不进行生成）
   * 
   * @return a {@link java.lang.String} object.
   */
  public String toQueryString() {
    if (Strings.isNotEmpty(queryStr)) {
      return queryStr;
    } else {
      return genQueryString(true);
    }
  }

  /**
   * <p>
   * toCountString.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String toCountString() {
    if (Strings.isNotEmpty(countStr)) {
      return countStr;
    } else {
      return "select count(*) from (" + genQueryString(false) + ")";
    }
  }

  /**
   * <p>
   * genQueryString.
   * </p>
   * 
   * @param hasOrder a boolean.
   * @return a {@link java.lang.String} object.
   */
  protected String genQueryString(final boolean hasOrder) {
    if (null == from) { return queryStr; }
    final StringBuilder buf = new StringBuilder(50);
    buf.append((select == null) ? "" : select).append(' ').append(from);
    if (!conditions.isEmpty()) {
      buf.append(" where ").append(ConditionUtils.toQueryString(conditions));
    }
    if (!groups.isEmpty()) {
      buf.append(" group by ");
      for (final String groupBy : groups) {
        buf.append(groupBy).append(',');
      }
      buf.deleteCharAt(buf.length() - 1);
    }
    if (hasOrder && !CollectionUtils.isEmpty(orders)) {
      buf.append(' ').append(Order.toSortString(orders));
    }
    return buf.toString();
  }

  /**
   * <p>
   * getParams.
   * </p>
   * 
   * @return a {@link java.util.Map} object.
   */
  public Map<String, Object> getParams() {
    return (null == params) ? ConditionUtils.getParamMap(conditions) : CollectUtils.newHashMap(params);
  }

  /**
   * <p>
   * build.
   * </p>
   * 
   * @return a {@link org.beangle.commons.dao.query.Query} object.
   */
  public Query<Object> build() {
    QueryBean<Object> queryBean = new QueryBean<Object>();
    queryBean.setStatement(toQueryString());
    queryBean.setParams(CollectUtils.newHashMap(getParams()));
    if (null != limit) {
      queryBean.setLimit(new PageLimit(limit.getPageNo(), limit.getPageSize()));
    }
    queryBean.setCountStatement(toCountString());
    queryBean.setCacheable(cacheable);
    queryBean.setLang(getLang());
    return queryBean;
  }

  /**
   * <p>
   * getLang.
   * </p>
   * 
   * @return a {@link org.beangle.commons.dao.query.Lang} object.
   */
  protected Lang getLang() {
    return Lang.SQL;
  }

  /** {@inheritDoc} */
  public SqlQuery limit(PageLimit limit) {
    this.limit = limit;
    return this;
  }

  /** {@inheritDoc} */
  public SqlQuery params(Map<String, Object> newParams) {
    this.params = CollectUtils.newHashMap(newParams);
    return this;
  }

}
