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
package org.beangle.struts2.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.beangle.commons.bean.PropertyUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.page.Page;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.dao.query.builder.Condition;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.entity.metadata.EntityType;
import org.beangle.commons.entity.metadata.Model;
import org.beangle.commons.lang.Numbers;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.web.util.CookieUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryHelper {

  protected static final Logger logger = LoggerFactory.getLogger(QueryHelper.class);

  public static final String PAGENO = "pageIndex";

  public static final String PAGESIZE = "pageSize";

  public static boolean RESERVED_NULL = true;

  public static void populateConditions(OqlBuilder<?> builder) {
    builder.where(extractConditions(builder.getEntityClass(), builder.getAlias(), null));
  }

  /**
   * 把entity alias的别名的参数转换成条件.<br>
   *
   * @param entityQuery
   * @param exclusiveAttrNames
   *          以entityQuery中alias开头的属性串
   */
  public static void populateConditions(OqlBuilder<?> entityQuery, String exclusiveAttrNames) {
    entityQuery.where(extractConditions(entityQuery.getEntityClass(), entityQuery.getAlias(),
        exclusiveAttrNames));
  }

  /**
   * 提取中的条件
   *
   * @param clazz
   * @param prefix
   * @param exclusiveAttrNames
   */
  public static List<Condition> extractConditions(Class<?> clazz, String prefix, String exclusiveAttrNames) {
    Object entity = null;
    try {
      if (clazz.isInterface()) {
        EntityType entityType = Model.getType(clazz.getName());
        clazz = entityType.getEntityClass();
      }
      entity = clazz.newInstance();
    } catch (Exception e) {
      throw new RuntimeException("[RequestUtil.extractConditions]: error in in initialize " + clazz);
    }
    List<Condition> conditions = CollectUtils.newArrayList();
    Map<String, Object> params = Params.sub(prefix, exclusiveAttrNames);
    for (Map.Entry<String, Object> entry : params.entrySet()) {
      String attr = entry.getKey();
      String strValue = entry.getValue().toString().trim();
      // 过滤空属性
      if (Strings.isNotEmpty(strValue)) {
        try {
          if (RESERVED_NULL && "null".equals(strValue)) {
            conditions.add(new Condition(prefix + "." + attr + " is null"));
          } else {
            Model.getPopulator().populateValue(entity, Model.getType(entity.getClass()), attr, strValue);
            Object setValue = PropertyUtils.getProperty(entity, attr);
            if (null == setValue) continue;
            if (setValue instanceof String) {
              conditions.add(new Condition(prefix + "." + attr + " like :" + attr.replace('.', '_'), "%"
                  + (String) setValue + "%"));
            } else {
              conditions.add(new Condition(prefix + "." + attr + "=:" + attr.replace('.', '_'), setValue));
            }
          }
        } catch (Exception e) {
          logger.error("Error populate entity " + prefix + "'s attribute " + attr, e);
        }
      }
    }
    return conditions;
  }

  /**
   * 从的参数或者cookie中(参数优先)取得分页信息
   */
  public static PageLimit getPageLimit() {
    return new PageLimit(getPageIndex(), getPageSize());
  }

  /**
   * 获得请求中的页码
   */
  public static int getPageIndex() {
    String pageIndex = Params.get(PAGENO);
    int resultno = 1;
    if (Strings.isNotBlank(pageIndex)) resultno = Numbers.toInt(pageIndex.trim());
    if (resultno < 1) resultno = Page.DEFAULT_PAGE_NUM;
    return resultno;
  }

  /**
   * 获得请求中的页长
   */
  public static int getPageSize() {
    String pageSize = Params.get(PAGESIZE);
    int pagesize = Page.DEFAULT_PAGE_SIZE;
    if (Strings.isNotBlank(pageSize)) {
      pagesize = Numbers.toInt(pageSize.trim());
    } else {
      HttpServletRequest request = ServletActionContext.getRequest();
      pageSize = CookieUtils.getCookieValue(request, PAGESIZE);
      if (Strings.isNotEmpty(pageSize)) pagesize = Numbers.toInt(pageSize);
    }
    if (pagesize < 1) pagesize = Page.DEFAULT_PAGE_SIZE;
    return pagesize;
  }

  public static void addDateIntervalCondition(OqlBuilder<?> query, String attr, String beginOn, String endOn) {
    addDateIntervalCondition(query, query.getAlias(), attr, beginOn, endOn);
  }

  /**
   * 增加日期区间查询条件
   *
   * @param query
   * @param alias
   * @param attr 时间限制属性
   * @param beginOn 开始的属性名字(全名)
   * @param endOn 结束的属性名字(全名)
   * @throws ParseException
   */
  public static void addDateIntervalCondition(OqlBuilder<?> query, String alias, String attr, String beginOn,
      String endOn) {
    String stime = Params.get(beginOn);
    String etime = Params.get(endOn);
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    Date sdate = null, edate = null;
    if (Strings.isNotBlank(stime)) {
      try {
        sdate = df.parse(stime);
      } catch (Exception e) {
        logger.debug("wrong date format:" + stime);
      }

    }
    // 截至日期增加一天
    if (Strings.isNotBlank(etime)) {
      try {
        edate = df.parse(etime);
      } catch (Exception e) {
        logger.debug("wrong date format:" + etime);
      }
      if (null != edate) {
        Calendar gc = new GregorianCalendar();
        gc.setTime(edate);
        gc.set(Calendar.DAY_OF_YEAR, gc.get(Calendar.DAY_OF_YEAR) + 1);
        edate = gc.getTime();
      }
    }
    String objAttr = ((null == alias) ? query.getAlias() : alias) + "." + attr;
    if (null != sdate && null == edate) {
      query.where(objAttr + " >=:sdate", sdate);
    } else if (null != sdate && null != edate) {
      query.where(objAttr + " >=:sdate and " + objAttr + " <:edate", sdate, edate);
    } else if (null == sdate && null != edate) {
      query.where(objAttr + " <:edate", edate);
    }
  }
}
