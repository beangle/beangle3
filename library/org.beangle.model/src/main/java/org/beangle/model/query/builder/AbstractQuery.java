/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.query.builder;

import java.util.Map;

import org.beangle.commons.collection.page.PageLimit;
import org.beangle.model.query.QueryBuilder;

/**
 * 抽象查询
 * 
 * @author chaostone
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

	public PageLimit getLimit() {
		return limit;
	}

	public void setLimit(final PageLimit limit) {
		this.limit = limit;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public String getCountStr() {
		return countStr;
	}

	public void setCountStr(final String countStr) {
		this.countStr = countStr;
	}

	public String getQueryStr() {
		return queryStr;
	}

	public void setQueryStr(final String queryStr) {
		this.queryStr = queryStr;
	}

	public void setParams(final Map<String, Object> params) {
		this.params = params;
	}

	public abstract String toQueryString();

	public String toCountString() {
		return countStr;
	}

	public boolean isCacheable() {
		return cacheable;
	}

	public void setCacheable(final boolean cacheable) {
		this.cacheable = cacheable;
	}

}
