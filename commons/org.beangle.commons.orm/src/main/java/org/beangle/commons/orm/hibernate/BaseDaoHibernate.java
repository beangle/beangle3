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
package org.beangle.commons.orm.hibernate;

import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.page.Page;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.collection.page.SinglePage;
import org.beangle.commons.lang.Strings;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;

/**
 * @deprecated use HibernateEntityDao
 * @author chaostone
 */
public class BaseDaoHibernate extends HibernateEntityDao {

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
   * @param params
   * @param limit
   */
  @SuppressWarnings("unchecked")
  public <T> Page<T> paginateQuery(Query query, Map<String, Object> params, PageLimit limit) {
    QuerySupport.setParameter(query, params);
    query.setFirstResult((limit.getPageNo() - 1) * limit.getPageSize()).setMaxResults(limit.getPageSize());
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
        if (Strings.contains(selectWhich, ",")) {
          queryStr = "select count("
              + query.getQueryString().substring(indexOfDistinct, query.getQueryString().indexOf(",")) + ")";

        } else {
          queryStr = "select count(" + query.getQueryString().substring(indexOfDistinct, indexOfFrom) + ")";
        }
      }
      queryStr += query.getQueryString().substring(indexOfFrom);
    }
    return queryStr;
  }
}
