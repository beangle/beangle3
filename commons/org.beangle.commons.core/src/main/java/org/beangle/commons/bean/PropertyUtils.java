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
package org.beangle.commons.bean;

import java.util.Set;

import org.beangle.commons.lang.Strings;
import org.beangle.commons.lang.Throwables;
import org.beangle.commons.lang.conversion.Conversion;
import org.beangle.commons.lang.conversion.impl.ConvertUtils;
import org.beangle.commons.lang.reflect.ClassInfo;
import org.beangle.commons.lang.reflect.MethodInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chaostone
 */
public class PropertyUtils {

  private static final Logger logger = LoggerFactory.getLogger(PropertyUtils.class);

  /**
   * @throws NoSuchMethodException
   * @param bean
   * @param name
   * @param value
   */
  public static void setProperty(Object bean, String name, Object value) {
    MethodInfo info = ClassInfo.get(bean.getClass()).getWriter(name);
    if (null == info) {
      logger.warn("Cannot find set" + Strings.capitalize(name) + " in " + bean.getClass());
      return;
    }
    try {
      info.method.invoke(bean, value);
    } catch (Exception e) {
      Throwables.propagate(e);
    }
  }

  public static Object getProperty(Object bean, String name) {
    MethodInfo info = ClassInfo.get(bean.getClass()).getReader(name);
    if (null == info) {
      logger.warn("Cannot find get" + Strings.capitalize(name) + " in " + bean.getClass());
      return null;
    }
    try {
      return info.method.invoke(bean);
    } catch (Exception e) {
      throw Throwables.propagate(e);
    }
  }

  public static void copyProperty(Object bean, String name, Object value, Conversion conversion) {
    ClassInfo classInfo = ClassInfo.get(bean.getClass());
    MethodInfo info = classInfo.getWriter(name);
    if (null == info) {
      logger.warn("Cannot find set" + Strings.capitalize(name) + " in " + bean.getClass());
      return;
    }
    try {
      info.method.invoke(bean, conversion.convert(value, classInfo.getPropertyType(name)));
    } catch (Exception e) {
      Throwables.propagate(e);
    }
  }

  public static void copyProperty(Object bean, String name, Object value) {
    ClassInfo classInfo = ClassInfo.get(bean.getClass());
    MethodInfo info = classInfo.getWriter(name);
    if (null == info) {
      logger.warn("Cannot find set" + Strings.capitalize(name) + " in " + bean.getClass());
      return;
    }
    try {
      info.method.invoke(bean, ConvertUtils.convert(value, classInfo.getPropertyType(name)));
    } catch (Exception e) {
      Throwables.propagate(e);
    }
  }

  public static boolean isWriteable(Object bean, String name) {
    ClassInfo classInfo = ClassInfo.get(bean.getClass());
    return null != classInfo.getWriter(name);
  }

  public static Class<?> getPropertyType(Class<?> clazz, String name) {
    return ClassInfo.get(clazz).getPropertyType(name);
  }

  public static Set<String> getWritableProperties(Class<?> clazz) {
    return ClassInfo.get(clazz).getWritableProperties();
  }

}
