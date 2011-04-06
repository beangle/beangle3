/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.query.builder;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.Order;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.model.query.Lang;
import org.beangle.model.query.Query;
import org.beangle.model.query.QueryBuilder;

public abstract class AbstractQueryBuilder<T> implements QueryBuilder<T> {

	public static final String INNER_JOIN = " left join ";

	public static final String OUTER_JOIN = " outer join ";

	public static final String LEFT_OUTER_JOIN = " left outer join ";

	public static final String RIGHT_OUTER_JOIN = " right outer join ";

	/** query 查询语句 */
	protected String statement;

	/** 分页 */
	protected PageLimit limit;

	/** 参数 */
	protected Map<String, Object> params;

	protected String select;

	protected String from;

	/** 别名 */
	protected String alias;

	protected List<Condition> conditions = CollectUtils.newArrayList();

	protected List<Order> orders = CollectUtils.newArrayList();

	protected List<String> groups = CollectUtils.newArrayList();

	/** 缓存查询结果 */
	protected boolean cacheable = false;

	public Map<String, Object> getParams() {
		return (null == params) ? ConditionUtils.getParamMap(conditions) : CollectUtils.newHashMap(params);
	}

	/**
	 * @return
	 */
	public String getAlias() {
		return alias;
	}

	public boolean isCacheable() {
		return cacheable;
	}

	public PageLimit getLimit() {
		return limit;
	}

	public Query<T> build() {
		QueryBean<T> queryBean = new QueryBean<T>();
		queryBean.setStatement(genStatement());
		queryBean.setParams(getParams());
		if (null != limit) {
			queryBean.setLimit(new PageLimit(limit.getPageNo(), limit.getPageSize()));
		}
		queryBean.setCountStatement(genCountStatement());
		queryBean.setCacheable(cacheable);
		queryBean.setLang(getLang());
		return queryBean;
	}

	abstract protected Lang getLang();

	/**
	 * {@inheritDoc}
	 * <p>
	 * 生成查询语句（如果查询语句已经存在则不进行生成）
	 */
	protected String genStatement() {
		if (StringUtils.isNotEmpty(statement)) {
			return statement;
		} else {
			return genQueryStatement(true);
		}
	}

	abstract protected String genCountStatement();

	protected String genQueryStatement(final boolean hasOrder) {
		if (null == from) { return statement; }
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

}
