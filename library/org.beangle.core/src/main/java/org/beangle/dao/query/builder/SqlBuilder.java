/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.dao.query.builder;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.beangle.collection.CollectUtils;
import org.beangle.collection.Order;
import org.beangle.collection.page.PageLimit;
import org.beangle.dao.query.Lang;

/**
 * sql查询
 * 
 * @author chaostone
 */
public class SqlBuilder extends AbstractQueryBuilder<Object[]> {
	public static SqlBuilder sql(final String queryStr) {
		SqlBuilder sqlQuery = new SqlBuilder();
		sqlQuery.statement = queryStr;
		return sqlQuery;
	}

	protected String genCountStatement() {
		return "select count(*) from (" + genQueryStatement(false) + ")";
	}

	@Override
	protected Lang getLang() {
		return Lang.SQL;
	}

	public SqlBuilder alias(final String alias) {
		this.alias = alias;
		return this;
	}

	public SqlBuilder join(final String path, final String alias) {
		from += " join " + path + " " + alias;
		return this;
	}

	public SqlBuilder join(final String joinMode, final String path, final String alias) {
		from += " " + joinMode + " join " + path + " " + alias;
		return this;
	}

	public SqlBuilder params(final Map<String, Object> params) {
		this.params = CollectUtils.newHashMap(params);
		;
		return this;
	}

	public SqlBuilder param(String name, Object value) {
		if (null == this.params) {
			params = new HashMap<String, Object>();
		}
		params.put(name, value);
		return this;
	}

	public SqlBuilder limit(final PageLimit limit) {
		this.limit = limit;
		return this;
	}

	public SqlBuilder limit(final int pageNo, final int pageSize) {
		this.limit = new PageLimit(pageNo, pageSize);
		return this;
	}

	public SqlBuilder cacheable() {
		this.cacheable = true;
		return this;
	}

	public SqlBuilder cacheable(final boolean cacheable) {
		this.cacheable = cacheable;
		return this;
	}

	public SqlBuilder where(final Condition condition) {
		conditions.add(condition);
		return this;
	}

	public SqlBuilder where(final String content) {
		return where(new Condition(content));
	}

	public SqlBuilder where(final String content, Object param1) {
		return where(new Condition(content, param1));
	}

	public SqlBuilder where(final String content, Object param1, Object param2) {
		return where(new Condition(content, param1, param2, null));
	}

	public SqlBuilder where(final String content, Object param1, Object param2, Object param3) {
		return where(new Condition(content, param1, param2, param3));
	}

	/**
	 * 添加一组条件<br>
	 * query中不能添加条件集合作为一个条件,因此这里命名没有采用有区别性的addAll
	 * 
	 * @author
	 * @param cons
	 * @return
	 */

	public SqlBuilder where(final Collection<Condition> cons) {
		conditions.addAll(cons);
		return this;
	}

	public SqlBuilder orderBy(final String orderBy) {
		this.orders.addAll(Order.parse(orderBy));
		return this;
	}

	public SqlBuilder orderBy(final Order order) {
		if (null != order) {
			this.orders.add(order);
		}
		return this;
	}

	public SqlBuilder cleanOrders() {
		this.orders.clear();
		return this;
	}

	public SqlBuilder orderBy(final List<Order> orders) {
		if (null != orders) {
			this.orders.addAll(orders);
		}
		return this;
	}

	public SqlBuilder select(final String what) {
		if (null == what) {
			this.select = null;
		} else {
			if (StringUtils.contains(what.toLowerCase(), "select")) {
				this.select = what;
			} else {
				this.select = "select " + what;
			}
		}
		return this;
	}

	public SqlBuilder newFrom(final String from) {
		if (null == from) {
			this.from = null;
		} else {
			if (StringUtils.contains(from.toLowerCase(), "from")) {
				this.from = from;
			} else {
				this.from = " from " + from;
			}
		}
		return this;
	}

	public SqlBuilder groupBy(final String what) {
		if (StringUtils.isNotEmpty(what)) {
			groups.add(what);
		}
		return this;
	}

}
