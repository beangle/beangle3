/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.persist.hibernate;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.page.Page;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.collection.page.SinglePage;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.persist.hibernate.HibernateEntityDao.QuerySupport;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public abstract class BaseDaoHibernate extends HibernateDaoSupport {

	protected EntityDao entityDao;

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	@SuppressWarnings("unchecked")
	public <T> Page<T> paginateCriteria(Criteria criteria, PageLimit limit) {
		CriteriaImpl criteriaImpl = (CriteriaImpl) criteria;
		int totalCount = 0;
		List<T> targetList = null;
		if (null == criteriaImpl.getProjection()) {
			criteria.setFirstResult((limit.getPageNo() - 1) * limit.getPageSize()).setMaxResults(
					limit.getPageSize());
			targetList = criteria.list();
			Projection projection = null;
			criteria.setFirstResult(0).setMaxResults(1);
			projection = Projections.rowCount();
			totalCount = ((Number) criteria.setProjection(projection).uniqueResult()).intValue();
		} else {
			List<T> list = criteria.list();
			totalCount = list.size();
			criteria.setFirstResult((limit.getPageNo() - 1) * limit.getPageSize()).setMaxResults(
					limit.getPageSize());
			targetList = criteria.list();
		}
		// 返回结果
		return new SinglePage<T>(limit.getPageNo(), limit.getPageSize(), totalCount, targetList);
	}

	/**
	 * @param query
	 * @param names
	 * @param values
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> Page<T> paginateQuery(Query query, Map<String, Object> params, PageLimit limit) {
		QuerySupport.setParameter(query, params);
		query.setFirstResult((limit.getPageNo() - 1) * limit.getPageSize())
				.setMaxResults(limit.getPageSize());
		List<T> targetList = query.list();

		String queryStr = buildCountQueryStr(query);
		Query countQuery = null;
		if (query instanceof SQLQuery) {
			countQuery = getSession().createSQLQuery(queryStr);
		} else {
			countQuery = getSession().createQuery(queryStr);
		}
		QuerySupport.setParameter(countQuery, params);
		// 返回结果
		return new SinglePage<T>(limit.getPageNo(), limit.getPageSize(),
				((Number) (countQuery.uniqueResult())).intValue(), targetList);
	}

	/**
	 * 构造查询记录数目的查询字符串
	 * 
	 * @param query
	 * @return
	 */
	private String buildCountQueryStr(Query query) {
		String queryStr = "select count(*) ";
		if (query instanceof SQLQuery) {
			queryStr += "from (" + query.getQueryString() + ")";
		} else {
			String lowerCaseQueryStr = query.getQueryString().toLowerCase();
			String selectWhich = lowerCaseQueryStr.substring(0, query.getQueryString().indexOf("from"));
			int indexOfDistinct = selectWhich.indexOf("distinct");
			int indexOfFrom = lowerCaseQueryStr.indexOf("from");
			// 如果含有distinct
			if (-1 != indexOfDistinct) {
				if (StringUtils.contains(selectWhich, ",")) {
					queryStr = "select count("
							+ query.getQueryString().substring(indexOfDistinct,
									query.getQueryString().indexOf(",")) + ")";

				} else {
					queryStr = "select count("
							+ query.getQueryString().substring(indexOfDistinct, indexOfFrom) + ")";
				}
			}
			queryStr += query.getQueryString().substring(indexOfFrom);
		}
		return queryStr;
	}
}
