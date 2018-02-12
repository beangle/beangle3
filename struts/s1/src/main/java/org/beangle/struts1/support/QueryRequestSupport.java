/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.struts1.support;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.beangle.commons.collection.page.Page;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.dao.query.builder.Condition;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.entity.metadata.EntityType;
import org.beangle.commons.entity.metadata.Model;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.web.util.CookieUtils;
import org.beangle.commons.web.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryRequestSupport {

  public QueryRequestSupport() {
  }

  public static void populateConditions(HttpServletRequest request, OqlBuilder entityQuery) {
    entityQuery.where(extractConditions(request, entityQuery.getEntityClass(), entityQuery.getAlias(), null));
  }

  public static void populateConditions(HttpServletRequest request, OqlBuilder entityQuery,
      String exclusiveAttrNames) {
    entityQuery.where(
        extractConditions(request, entityQuery.getEntityClass(), entityQuery.getAlias(), exclusiveAttrNames));
  }

  public static List extractConditions(HttpServletRequest request, Class clazz, String prefix,
      String exclusiveAttrNames) {
    Object entity = null;
    EntityType entityType = null;
    try {
      entityType = Model.getType(clazz.getName());
      if (clazz.isInterface()) {
        clazz = entityType.getEntityClass();
      }
      entity = clazz.newInstance();
    } catch (Exception e) {
      throw new RuntimeException("[RequestUtil.extractConditions]: error in in initialize " + clazz);
    }
    List conditions = new ArrayList();
    Map params = RequestUtils.getParams(request, prefix, exclusiveAttrNames);
    for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
      String attr = (String) iter.next();
      String strValue = ((String) params.get(attr)).trim();
      // 过滤空属性
      if (Strings.isNotEmpty(strValue)) {
        try {
          if (RESERVED_NULL && "null".equals(strValue)) {
            conditions.add(new Condition(prefix + "." + attr + " is null"));
          } else {
            Model.getPopulator().populateValue(entity, entityType, attr, strValue);
            Object settedValue = PropertyUtils.getProperty(entity, attr);
            if (null == settedValue) continue;
            if (settedValue instanceof String) {
              conditions.add(new Condition(prefix + "." + attr + " like :" + attr.replace('.', '_'),
                  "%" + (String) settedValue + "%"));
            } else {
              conditions.add(new Condition(prefix + "." + attr + "=:" + attr.replace('.', '_'), settedValue));
            }
          }
        } catch (Exception e) {
          logger.debug("[populateFromParams]:error in populate entity " + prefix + "'s attribute " + attr);
        }
      }
    }
    return conditions;
  }

  public static PageLimit getPageLimit(HttpServletRequest request) {
    PageLimit limit = new PageLimit();
    limit.setPageNo(getPageNo(request));
    limit.setPageSize(getPageSize(request));
    return limit;
  }

  public static int getPageNo(HttpServletRequest request) {
    String pageNo = request.getParameter("pageNo");
    if (Strings.isNotEmpty(pageNo)) return Integer.valueOf(pageNo).intValue();
    else return 1;
  }

  public static int getPageSize(HttpServletRequest request) {
    String pageSize = request.getParameter("pageSize");
    if (Strings.isNotEmpty(pageSize)) return Integer.valueOf(pageSize).intValue();
    pageSize = CookieUtils.getCookieValue(request, "pageSize");
    if (Strings.isNotEmpty(pageSize)) return Integer.valueOf(pageSize).intValue();
    else return 20;
  }

  public static void addPage(HttpServletRequest request, Collection objs) {
    if (objs instanceof Page) {
      Page page = (Page) objs;
      request.setAttribute("pageNo", new Integer(page.getPageNo()));
      request.setAttribute("pageSize", new Integer(page.getPageSize()));
      request.setAttribute("previousPageNo", new Integer(page.getPreviousPageNo()));
      request.setAttribute("nextPageNo", new Integer(page.getNextPageNo()));
      request.setAttribute("maxPageNo", new Integer(page.getMaxPageNo()));
      request.setAttribute("thisPageSize", new Integer(page.size()));
      request.setAttribute("totalSize", new Integer(page.getTotal()));
    }
  }

  public static void addDateIntervalCondition(HttpServletRequest request, OqlBuilder query, String attr,
      String beginOn, String endOn) {
    addDateIntervalCondition(request, query, query.getAlias(), attr, beginOn, endOn);
  }

  public static void addDateIntervalCondition(HttpServletRequest request, OqlBuilder query, String alias,
      String attr, String beginOn, String endOn) {
    String stime = request.getParameter(beginOn);
    String etime = request.getParameter(endOn);
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date sdate = null;
    java.util.Date edate = null;
    if (Strings.isNotBlank(stime)) try {
      sdate = df.parse(stime);
    } catch (Exception e) {
      logger.debug("wrong date format:" + stime);
    }
    if (Strings.isNotBlank(etime)) {
      try {
        edate = df.parse(etime);
      } catch (Exception e) {
        logger.debug("wrong date format:" + etime);
      }
      Calendar gc = new GregorianCalendar();
      gc.setTime(edate);
      gc.set(6, gc.get(6) + 1);
      edate = gc.getTime();
    }
    String objAttr = (null != alias ? alias : query.getAlias()) + "." + attr;
    if (null != sdate && null == edate) query.where(new Condition(objAttr + " >=:sdate", sdate));
    else if (null != sdate && null != edate)
      query.where(new Condition(objAttr + " >=:sdate and " + objAttr + " <:edate", sdate, edate));
    else if (null == sdate && null != edate) query.where(new Condition(objAttr + " <:edate", edate));
  }

  protected static final Logger logger;
  public static final String PAGENO = "pageNo";
  public static final String PAGESIZE = "pageSize";
  public static boolean RESERVED_NULL = true;

  static {
    logger = LoggerFactory.getLogger(org.beangle.struts1.support.QueryRequestSupport.class);
  }
}
