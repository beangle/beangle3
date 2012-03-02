/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.dao.query.builder;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.beangle.collection.CollectUtils;
import org.beangle.collection.Order;
import org.beangle.collection.page.PageLimit;
import org.beangle.dao.query.Lang;
import org.beangle.dao.query.Query;

/**
 * sql查询
 * 
 * @author chaostone
 */
public class SqlQuery extends AbstractQuery<Object> {

	public static final String INNER_JOIN = " left join ";

	public static final String OUTER_JOIN = " outer join ";

	public static final String LEFT_OUTER_JOIN = " left outer join ";

	public static final String RIGHT_OUTER_JOIN = " right outer join ";

	protected String select;

	protected String from;

	protected List<Condition> conditions = CollectUtils.newArrayList();

	protected List<Order> orders = CollectUtils.newArrayList();

	protected List<String> groups = CollectUtils.newArrayList();

	public SqlQuery() {
		super();
	}

	public SqlQuery(final String queryStr) {
		super();
		this.queryStr = queryStr;
	}

	public SqlQuery add(final Condition condition) {
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
	public SqlQuery add(final Collection<Condition> cons) {
		conditions.addAll(cons);
		return this;
	}

	public SqlQuery addOrder(final Order order) {
		if (null != order) {
			this.orders.add(order);
		}
		return this;
	}

	public SqlQuery addOrder(final List<Order> orders) {
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

	public SqlQuery groupBy(final String what) {
		if (StringUtils.isNotEmpty(what)) {
			groups.add(what);
		}
		return this;
	}

	/**
	 * 生成查询语句（如果查询语句已经存在则不进行生成）
	 */
	public String toQueryString() {
		if (StringUtils.isNotEmpty(queryStr)) {
			return queryStr;
		} else {
			return genQueryString(true);
		}
	}

	public String toCountString() {
		if (StringUtils.isNotEmpty(countStr)) {
			return countStr;
		} else {
			return "select count(*) from (" + genQueryString(false) + ")";
		}
	}

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

	public Map<String, Object> getParams() {
		return (null == params) ? ConditionUtils.getParamMap(conditions) : CollectUtils.newHashMap(params);
	}

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

	protected Lang getLang() {
		return Lang.SQL;
	}

	public SqlQuery limit(PageLimit limit) {
		this.limit = limit;
		return this;
	}

	public SqlQuery params(Map<String, Object> newParams) {
		this.params = CollectUtils.newHashMap(newParams);
		return this;
	}

}
