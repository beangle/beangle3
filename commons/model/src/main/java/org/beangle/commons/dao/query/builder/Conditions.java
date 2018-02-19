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
package org.beangle.commons.dao.query.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.bean.PropertyUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.Component;
import org.beangle.commons.entity.Entity;
import org.beangle.commons.entity.util.ValidEntityKeyPredicate;
import org.beangle.commons.lang.Assert;
import org.beangle.commons.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 条件提取辅助类
 *
 * @author chaostone
 */
public final class Conditions {
  private static final Logger logger = LoggerFactory.getLogger(Conditions.class);

  private Conditions() {
    super();
  }

  public static Condition and(Condition... conditions) {
    return concat(Arrays.asList(conditions), "and");
  }

  public static Condition and(List<Condition> conditions) {
    return concat(conditions, "and");
  }

  public static Condition or(Condition... conditions) {
    return concat(Arrays.asList(conditions), "or");
  }

  public static Condition or(List<Condition> conditions) {
    return concat(conditions, "or");
  }

  static Condition concat(List<Condition> conditions, String andor) {
    Assert.isTrue(!conditions.isEmpty(), "conditions shouldn't be empty!");
    if (conditions.size() == 1) return conditions.get(0);
    StringBuffer sb = new StringBuffer();
    List<Object> params = CollectUtils.newArrayList();
    sb.append("(");
    for (Condition con : conditions) {
      sb.append(" " + andor + " (");
      sb.append(con.getContent());
      sb.append(')');
      params.addAll(con.getParams());
    }
    sb.append(")");
    sb.replace(0, (" " + andor + " ").length() + 1, "(");
    return new Condition(sb.toString()).params(params);
  }

  public static String toQueryString(final List<Condition> conditions) {
    if (null == conditions || conditions.isEmpty()) { return ""; }
    final StringBuilder buf = new StringBuilder("");
    for (final Iterator<Condition> iter = conditions.iterator(); iter.hasNext();) {
      final Condition con = iter.next();
      buf.append('(').append(con.getContent()).append(')');
      if (iter.hasNext()) {
        buf.append(" and ");
      }
    }
    return buf.toString();
  }

  /**
   * 提取对象中的条件<br>
   * 提取的属性仅限"平面"属性(允许包括component)<br>
   * 过滤掉属性:null,或者空Collection
   *
   * @param alias
   * @param entity
   */
  public static List<Condition> extractConditions(String alias, final Entity<?> entity) {
    if (null == entity) { return Collections.emptyList(); }
    final List<Condition> conditions = new ArrayList<Condition>();

    StringBuilder aliasBuilder = new StringBuilder(alias == null ? "" : alias);
    if (aliasBuilder.length() > 0 && !alias.endsWith(".")) aliasBuilder.append(".");
    alias = aliasBuilder.toString();
    String attr = "";
    try {
      final Set<String> props = PropertyUtils.getWritableProperties(entity.getClass());
      for (final Iterator<String> iter = props.iterator(); iter.hasNext();) {
        attr = iter.next();
        final Object value = PropertyUtils.getProperty(entity, attr);
        if (null == value) continue;
        if (!(value instanceof Collection<?>)) addAttrCondition(conditions, alias + attr, value);
      }
    } catch (Exception e) {
      logger.debug("error occur in extractConditions for  bean {} with attr named {}", entity, attr);
    }
    return conditions;
  }

  /**
   * 获得条件的绑定参数映射
   *
   * @param conditions
   */
  public static Map<String, Object> getParamMap(final Collection<Condition> conditions) {
    final Map<String, Object> params = new HashMap<String, Object>();
    for (final Condition con : conditions) {
      params.putAll(getParamMap(con));
    }
    return params;
  }

  /**
   * 获得条件的绑定参数映射
   *
   * @param condition
   */
  public static Map<String, Object> getParamMap(final Condition condition) {
    final Map<String, Object> params = new HashMap<String, Object>();
    if (!Strings.contains(condition.getContent(), "?")) {
      final List<String> paramNames = condition.getParamNames();
      /*
       * Abort check param and names for it will updated by params invocation
       * if (paramNames.size() > condition.getParams().size()) { throw new RuntimeException(
       * "condition params not set [" + condition.getContent() + "] with value:" +
       * condition.getParams()); }
       */
      for (int i = 0; i < paramNames.size(); i++) {
        if (i >= condition.getParams().size()) break;
        params.put(paramNames.get(i), condition.getParams().get(i));
      }
    }
    return params;
  }

  /**
   * 为extractConditions使用的私有方法<br>
   *
   * @param conditions
   * @param name
   * @param value
   * @param mode
   */
  private static void addAttrCondition(final List<Condition> conditions, final String name, Object value) {
    if (value instanceof String) {
      if (Strings.isBlank((String) value)) { return; }
      StringBuilder content = new StringBuilder(name);
      content.append(" like :").append(name.replace('.', '_'));
      conditions.add(new Condition(content.toString(), "%" + value + "%"));
    } else if (value instanceof Component) {
      conditions.addAll(extractComponent(name, (Component) value));
      return;
    } else if (value instanceof Entity<?>) {
      try {
        final String key = "id";
        Object property = PropertyUtils.getProperty(value, key);
        if (ValidEntityKeyPredicate.Instance.apply(property)) {
          StringBuilder content = new StringBuilder(name);
          content.append('.').append(key).append(" = :").append(name.replace('.', '_')).append('_')
              .append(key);
          conditions.add(new Condition(content.toString(), property));
        }
      } catch (Exception e) {
        logger.warn("getProperty " + value + "error", e);
      }
    } else {
      conditions.add(new Condition(name + " = :" + name.replace('.', '_'), value));
    }
  }

  private static List<Condition> extractComponent(final String prefix, final Component component) {
    if (null == component) { return Collections.emptyList(); }
    final List<Condition> conditions = CollectUtils.newArrayList();
    String attr = "";
    try {
      final Set<String> props = PropertyUtils.getWritableProperties(component.getClass());
      for (final Iterator<String> iter = props.iterator(); iter.hasNext();) {
        attr = iter.next();
        final Object value = PropertyUtils.getProperty(component, attr);
        if (value == null) {
          continue;
        } else if (value instanceof Collection<?>) {
          if (((Collection<?>) value).isEmpty()) continue;
        } else {
          addAttrCondition(conditions, prefix + "." + attr, value);
        }
      }
    } catch (Exception e) {
      logger.warn("error occur in extractComponent of component:" + component + "with attr named :" + attr);
    }
    return conditions;
  }

}
