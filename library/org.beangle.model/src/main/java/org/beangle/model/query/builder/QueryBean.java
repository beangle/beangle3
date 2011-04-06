/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.query.builder;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.model.query.Lang;
import org.beangle.model.query.LimitQuery;
import org.beangle.model.query.Query;

public class QueryBean<T> implements LimitQuery<T> {

	private Lang lang;

	private String statement;

	private String countStatement;

	private PageLimit limit;

	private boolean cacheable;

	private Map<String, Object> params;

	public Query<T> getCountQuery() {
		if (StringUtils.isEmpty(countStatement)) { return null; }
		QueryBean<T> bean = new QueryBean<T>();
		bean.setStatement(countStatement);
		bean.setLang(lang);
		bean.setParams(params);
		bean.setCacheable(cacheable);
		return bean;
	}

	public String getStatement() {
		return statement;
	}

	public String getCountStatement() {
		return countStatement;
	}

	public PageLimit getLimit() {
		return limit;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public void setCountStatement(String countStatement) {
		this.countStatement = countStatement;
	}

	public void setLimit(PageLimit limit) {
		this.limit = limit;
	}

	public LimitQuery<T> limit(PageLimit limit) {
		this.limit = new PageLimit(limit.getPageNo(), limit.getPageSize());
		return this;
	}

	public void setCacheable(boolean cacheable) {
		this.cacheable = cacheable;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public boolean isCacheable() {
		return cacheable;
	}

	public Lang getLang() {
		return lang;
	}

	public void setLang(Lang lang) {
		this.lang = lang;
	}

}
