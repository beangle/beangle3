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

import java.sql.Date;
import java.util.Collections;
import java.util.Map;

import org.apache.struts2.dispatcher.Parameter;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.MapConverter;
import org.beangle.commons.conversion.impl.DefaultConversion;

import com.opensymphony.xwork2.ActionContext;

public class Params {

  public static final MapConverter converter = new MapConverter(DefaultConversion.Instance);

  public static Map<String, Object> getParams() {
    Map<String, Object> params = CollectUtils.newHashMap();
    for (Map.Entry<String, Parameter> entry : ActionContext.getContext().getParameters().entrySet()) {
      params.put(entry.getKey(), entry.getValue().getObject());
    }
    return params;
  }

  public static Map<String, Object> getParams(String attr) {
    Parameter param = ActionContext.getContext().getParameters().get(attr);
    if (null == param) {
      return Collections.emptyMap();
    } else {
      Map<String, Object> params = CollectUtils.newHashMap();
      params.put(attr, param.getObject());
      return params;
    }
  }

  public static String get(String attr) {
    return converter.getString(getParams(attr), attr);
  }

  public static <T> T get(String name, Class<T> clazz) {
    return converter.get(getParams(name), name, clazz);
  }

  public static Object[] getAll(String attr) {
    return converter.getAll(getParams(attr), attr);
  }

  public static <T> T[] getAll(String attr, Class<T> clazz) {
    return converter.getAll(getParams(attr), attr, clazz);
  }

  public static boolean getBool(String name) {
    return converter.getBool(getParams(name), name);
  }

  public static Boolean getBoolean(String name) {
    return converter.getBoolean(getParams(name), name);
  }

  public static Date getDate(String name) {
    return converter.getDate(getParams(name), name);
  }

  public static java.util.Date getDateTime(String name) {
    return converter.getDateTime(getParams(name), name);
  }

  public static Float getFloat(String name) {
    return converter.getFloat(getParams(name), name);
  }

  public static Short getShort(String name) {
    return converter.getShort(getParams(name), name);
  }

  public static Integer getInt(String name) {
    return converter.getInteger(getParams(), name);
  }

  public static Long getLong(String name) {
    return converter.getLong(getParams(name), name);
  }

  public static Map<String, Object> sub(String prefix) {
    return converter.sub(getParams(), prefix);
  }

  public static Map<String, Object> sub(String prefix, String exclusiveAttrNames) {
    return converter.sub(getParams(), prefix, exclusiveAttrNames);
  }

}
