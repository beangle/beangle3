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

import org.apache.commons.collections.CollectionUtils;
import org.beangle.commons.collection.Order;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.dao.query.Lang;
import org.beangle.commons.dao.query.Query;
import org.beangle.commons.entity.metadata.EntityType;
import org.beangle.commons.entity.metadata.Model;
import org.beangle.commons.lang.Strings;

/**
 * 实体类查询
 * 
 * @author chaostone
 * @deprecated use HqlBuilder ,It will be removed in 3.1.0.
 * @version $Id: $
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class EntityQuery extends OqlBuilder {

  protected String countStr;

  /**
   * <p>
   * Constructor for EntityQuery.
   * </p>
   */
  public EntityQuery() {
    super();
  }

  /**
   * <p>
   * Constructor for EntityQuery.
   * </p>
   * 
   * @param hql a {@link java.lang.String} object.
   */
  public EntityQuery(final String hql) {
    super();
    this.statement = hql;
  }

  /**
   * <p>
   * Constructor for EntityQuery.
   * </p>
   * 
   * @param entityName a {@link java.lang.String} object.
   * @param alias a {@link java.lang.String} object.
   */
  public EntityQuery(final String entityName, final String alias) {
    EntityType type = Model.getEntityType(entityName);
    this.entityClass = type.getEntityClass();
    this.alias = alias;
    select = "select " + alias + " ";
    from = "from " + entityName + " " + alias;
  }

  /**
   * <p>
   * Constructor for EntityQuery.
   * </p>
   * 
   * @param entityClass a {@link java.lang.Class} object.
   * @param alias a {@link java.lang.String} object.
   */
  public EntityQuery(final Class<?> entityClass, final String alias) {
    super();
    EntityType type = Model.getEntityType(entityClass.getName());
    if (null == type) {
      type = Model.getEntityType(entityClass);
    }
    this.entityClass = type.getEntityClass();
    this.alias = alias;
    select = "select " + alias + " ";
    from = "from " + type.getEntityName() + " " + alias;
  }

  /** {@inheritDoc} */
  public EntityQuery join(final String path, final String alias) {
    from += " join " + path + " " + alias;
    return this;
  }

  /** {@inheritDoc} */
  public EntityQuery join(final String joinMode, final String path, final String alias) {
    from += " " + joinMode + " join " + path + " " + alias;
    return this;
  }

  /**
   * 形成计数查询语句，如果不能形成，则返回""
   * 
   * @return a {@link java.lang.String} object.
   */
  public String toCountString() {
    if (Strings.isNotEmpty(countStr)) { return countStr; }
    StringBuilder countString = new StringBuilder("select count(*) ");
    // 原始查询语句
    final String genQueryStr = genQueryString(false);
    if (Strings.isEmpty(genQueryStr)) { return ""; }
    final String lowerCaseQueryStr = genQueryStr.toLowerCase();

    if (Strings.contains(lowerCaseQueryStr, " group ")) { return ""; }
    if (Strings.contains(lowerCaseQueryStr, " union ")) { return ""; }

    final int indexOfFrom = lowerCaseQueryStr.indexOf("from");
    final String selectWhat = lowerCaseQueryStr.substring(0, indexOfFrom);
    final int indexOfDistinct = selectWhat.indexOf("distinct");
    // select distinct a from table;
    if (-1 != indexOfDistinct) {
      if (Strings.contains(selectWhat, ",")) {
        return null;
      } else {
        countString = new StringBuilder("select count(");
        countString.append(genQueryStr.substring(indexOfDistinct, indexOfFrom)).append(')');
      }
    }
    countString.append(genQueryStr.substring(indexOfFrom));
    return countString.toString();
  }

  /**
   * <p>
   * getAlias.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getAlias() {
    return alias;
  }

  /**
   * <p>
   * setAlias.
   * </p>
   * 
   * @param alias a {@link java.lang.String} object.
   */
  public void setAlias(final String alias) {
    this.alias = alias;
  }

  /**
   * <p>
   * getEntityClass.
   * </p>
   * 
   * @return a {@link java.lang.Class} object.
   */
  public Class<?> getEntityClass() {
    return entityClass;
  }

  /**
   * <p>
   * setEntityClass.
   * </p>
   * 
   * @param entityClass a {@link java.lang.Class} object.
   */
  public void setEntityClass(final Class<?> entityClass) {
    this.entityClass = entityClass;
  }

  /**
   * <p>
   * getLang.
   * </p>
   * 
   * @return a {@link org.beangle.commons.dao.query.Lang} object.
   */
  protected Lang getLang() {
    return Lang.HQL;
  }

  /**
   * <p>
   * getLimit.
   * </p>
   * 
   * @return a {@link org.beangle.commons.collection.page.PageLimit} object.
   */
  public PageLimit getLimit() {
    return limit;
  }

  /**
   * <p>
   * setLimit.
   * </p>
   * 
   * @param limit a {@link org.beangle.commons.collection.page.PageLimit} object.
   */
  public void setLimit(final PageLimit limit) {
    this.limit = limit;
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
   * getQueryStr.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getQueryStr() {
    return statement;
  }

  /**
   * <p>
   * setQueryStr.
   * </p>
   * 
   * @param queryStr a {@link java.lang.String} object.
   */
  public void setQueryStr(final String queryStr) {
    this.statement = queryStr;
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
   * setCacheable.
   * </p>
   * 
   * @param cacheable a boolean.
   */
  public void setCacheable(final boolean cacheable) {
    this.cacheable = cacheable;
  }

  /**
   * <p>
   * add.
   * </p>
   * 
   * @param condition a {@link org.beangle.commons.dao.query.builder.Condition} object.
   * @return a {@link org.beangle.commons.dao.query.builder.EntityQuery} object.
   */
  public EntityQuery add(final Condition condition) {
    conditions.add(condition);
    return this;
  }

  /**
   * 添加一组条件<br>
   * query中不能添加条件集合作为一个条件,因此这里命名没有采用有区别性的addAll
   * 
   * @param cons a {@link java.util.Collection} object.
   * @return a {@link org.beangle.commons.dao.query.builder.EntityQuery} object.
   */
  public EntityQuery add(final Collection<Condition> cons) {
    conditions.addAll(cons);
    return this;
  }

  /**
   * <p>
   * addOrder.
   * </p>
   * 
   * @param order a {@link org.beangle.commons.collection.Order} object.
   * @return a {@link org.beangle.commons.dao.query.builder.EntityQuery} object.
   */
  public EntityQuery addOrder(final Order order) {
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
   * @return a {@link org.beangle.commons.dao.query.builder.EntityQuery} object.
   */
  public EntityQuery addOrder(final List<Order> orders) {
    if (null != orders) {
      this.orders.addAll(orders);
    }
    return this;
  }

  /**
   * <p>
   * getSelect.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getSelect() {
    return select;
  }

  /**
   * <p>
   * setSelect.
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
   * getConditions.
   * </p>
   * 
   * @return a {@link java.util.List} object.
   */
  public List<Condition> getConditions() {
    return conditions;
  }

  /**
   * <p>
   * setConditions.
   * </p>
   * 
   * @param conditions a {@link java.util.List} object.
   */
  public void setConditions(final List<Condition> conditions) {
    this.conditions = conditions;
  }

  /**
   * <p>
   * getFrom.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getFrom() {
    return from;
  }

  /**
   * <p>
   * setFrom.
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
   * getOrders.
   * </p>
   * 
   * @return a {@link java.util.List} object.
   */
  public List<Order> getOrders() {
    return orders;
  }

  /**
   * <p>
   * setOrders.
   * </p>
   * 
   * @param orders a {@link java.util.List} object.
   */
  public void setOrders(final List<Order> orders) {
    this.orders = orders;
  }

  /**
   * <p>
   * getGroups.
   * </p>
   * 
   * @return a {@link java.util.List} object.
   */
  public List<String> getGroups() {
    return groups;
  }

  /**
   * <p>
   * setGroups.
   * </p>
   * 
   * @param groups a {@link java.util.List} object.
   */
  public void setGroups(final List<String> groups) {
    this.groups = groups;
  }

  /** {@inheritDoc} */
  public EntityQuery groupBy(final String what) {
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
    if (Strings.isNotEmpty(statement)) {
      return statement;
    } else {
      return genQueryString(true);
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
    if (null == from) { return statement; }
    final StringBuilder buf = new StringBuilder(50);
    buf.append((select == null) ? "" : select).append(' ').append(from);
    if (!conditions.isEmpty()) {
      buf.append(" where ").append(ConditionUtils.toQueryString(conditions));
    }
    if (!groups.isEmpty()) {
      buf.append(" group by ");
      for (final String groupBy : getGroups()) {
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
   * build.
   * </p>
   * 
   * @return a {@link org.beangle.commons.dao.query.Query} object.
   */
  public Query<Object> build() {
    QueryBean<Object> queryBean = new QueryBean<Object>();
    queryBean.setStatement(toQueryString());
    queryBean.setParams(getParams());
    if (null != limit) {
      queryBean.setLimit(new PageLimit(limit.getPageNo(), limit.getPageSize()));
    }
    queryBean.setCountStatement(toCountString());
    queryBean.setCacheable(cacheable);
    queryBean.setLang(getLang());
    return queryBean;
  }

  /** {@inheritDoc} */
  public EntityQuery limit(PageLimit limit) {
    this.limit = limit;
    return this;
  }

}
