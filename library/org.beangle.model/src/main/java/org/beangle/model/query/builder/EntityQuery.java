/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.query.builder;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.Order;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.model.entity.Model;
import org.beangle.model.entity.types.EntityType;
import org.beangle.model.query.Lang;
import org.beangle.model.query.Query;

/**
 * 实体类查询
 * 
 * @author chaostone
 * @deprecated use HqlBuilder
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class EntityQuery extends OqlBuilder {

	protected String countStr;

	public EntityQuery() {
		super();
	}

	public EntityQuery(final String hql) {
		super();
		this.statement = hql;
	}

	public EntityQuery(final String entityName, final String alias) {
		EntityType type = Model.getEntityType(entityName);
		this.entityClass = type.getEntityClass();
		this.alias = alias;
		select = "select " + alias + " ";
		from = "from " + entityName + " " + alias;
	}

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

	public EntityQuery join(final String path, final String alias) {
		from += " join " + path + " " + alias;
		return this;
	}

	public EntityQuery join(final String joinMode, final String path, final String alias) {
		from += " " + joinMode + " join " + path + " " + alias;
		return this;
	}

	/**
	 * 形成计数查询语句，如果不能形成，则返回""
	 */
	public String toCountString() {
		if (StringUtils.isNotEmpty(countStr)) { return countStr; }
		StringBuilder countString = new StringBuilder("select count(*) ");
		// 原始查询语句
		final String genQueryStr = genQueryString(false);
		if (StringUtils.isEmpty(genQueryStr)) { return ""; }
		final String lowerCaseQueryStr = genQueryStr.toLowerCase();

		if (StringUtils.contains(lowerCaseQueryStr, " group ")) { return ""; }
		if (StringUtils.contains(lowerCaseQueryStr, " union ")) { return ""; }

		final int indexOfFrom = lowerCaseQueryStr.indexOf("from");
		final String selectWhat = lowerCaseQueryStr.substring(0, indexOfFrom);
		final int indexOfDistinct = selectWhat.indexOf("distinct");
		// select distinct a from table;
		if (-1 != indexOfDistinct) {
			if (StringUtils.contains(selectWhat, ",")) {
				return null;
			} else {
				countString = new StringBuilder("select count(");
				countString.append(genQueryStr.substring(indexOfDistinct, indexOfFrom)).append(')');
			}
		}
		countString.append(genQueryStr.substring(indexOfFrom));
		return countString.toString();
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(final String alias) {
		this.alias = alias;
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(final Class<?> entityClass) {
		this.entityClass = entityClass;
	}

	protected Lang getLang() {
		return Lang.HQL;
	}

	public PageLimit getLimit() {
		return limit;
	}

	public void setLimit(final PageLimit limit) {
		this.limit = limit;
	}

	public String getCountStr() {
		return countStr;
	}

	public void setCountStr(final String countStr) {
		this.countStr = countStr;
	}

	public String getQueryStr() {
		return statement;
	}

	public void setQueryStr(final String queryStr) {
		this.statement = queryStr;
	}

	public boolean isCacheable() {
		return cacheable;
	}

	public void setCacheable(final boolean cacheable) {
		this.cacheable = cacheable;
	}

	public EntityQuery add(final Condition condition) {
		conditions.add(condition);
		return this;
	}

	/**
	 * 添加一组条件<br>
	 * query中不能添加条件集合作为一个条件,因此这里命名没有采用有区别性的addAll
	 * 
	 * @author
	 * @param cons
	 * @return
	 */
	public EntityQuery add(final Collection<Condition> cons) {
		conditions.addAll(cons);
		return this;
	}

	public EntityQuery addOrder(final Order order) {
		if (null != order) {
			this.orders.add(order);
		}
		return this;
	}

	public EntityQuery addOrder(final List<Order> orders) {
		if (null != orders) {
			this.orders.addAll(orders);
		}
		return this;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(final String select) {
		if (null == select) {
			this.select = null;
		} else {
			if (StringUtils.contains(select.toLowerCase(), "select")) {
				this.select = select;
			} else {
				this.select = "select " + select;
			}
		}
	}

	public List<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(final List<Condition> conditions) {
		this.conditions = conditions;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(final String from) {
		if (null == from) {
			this.from = null;
		} else {
			if (StringUtils.contains(from.toLowerCase(), "from")) {
				this.from = from;
			} else {
				this.from = " from " + from;
			}
		}
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(final List<Order> orders) {
		this.orders = orders;
	}

	public List<String> getGroups() {
		return groups;
	}

	public void setGroups(final List<String> groups) {
		this.groups = groups;
	}

	public EntityQuery groupBy(final String what) {
		if (StringUtils.isNotEmpty(what)) {
			groups.add(what);
		}
		return this;
	}

	/**
	 * 生成查询语句（如果查询语句已经存在则不进行生成）
	 */
	public String toQueryString() {
		if (StringUtils.isNotEmpty(statement)) {
			return statement;
		} else {
			return genQueryString(true);
		}
	}

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

	public EntityQuery limit(PageLimit limit) {
		this.limit = limit;
		return this;
	}

}
