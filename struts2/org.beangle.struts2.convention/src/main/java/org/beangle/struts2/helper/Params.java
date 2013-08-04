/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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
package org.beangle.struts2.helper;

import java.sql.Date;
import java.util.Map;

import org.beangle.commons.collection.MapConverter;
import org.beangle.commons.lang.conversion.impl.DefaultConversion;

import com.opensymphony.xwork2.ActionContext;

public class Params {

  public static final MapConverter converter = new MapConverter(DefaultConversion.Instance);

  public static Map<String, Object> getParams() {
    return ActionContext.getContext().getParameters();
  }

  public static String get(String attr) {
    return converter.getString(getParams(), attr);
  }

  public static <T> T get(String name, Class<T> clazz) {
    return converter.get(getParams(), name, clazz);
  }

  public static Object[] getAll(String attr) {
    return converter.getAll(getParams(), attr);
  }

  public static <T> T[] getAll(String attr, Class<T> clazz) {
    return converter.getAll(getParams(), attr, clazz);
  }

  public static boolean getBool(String name) {
    return converter.getBool(getParams(), name);
  }

  public static Boolean getBoolean(String name) {
    return converter.getBoolean(getParams(), name);
  }

  public static Date getDate(String name) {
    return converter.getDate(getParams(), name);
  }

  public static java.util.Date getDateTime(String name) {
    return converter.getDateTime(getParams(), name);
  }

  public static Float getFloat(String name) {
    return converter.getFloat(getParams(), name);
  }

  public static Short getShort(String name) {
    return converter.getShort(getParams(), name);
  }

  public static Integer getInt(String name) {
    return converter.getInteger(getParams(), name);
  }

  public static Long getLong(String name) {
    return converter.getLong(getParams(), name);
  }

  public static Map<String, Object> sub(String prefix) {
    return converter.sub(getParams(), prefix);
  }

  public static Map<String, Object> sub(String prefix, String exclusiveAttrNames) {
    return converter.sub(getParams(), prefix, exclusiveAttrNames);
  }

}
