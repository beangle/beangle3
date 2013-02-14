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
package org.beangle.commons.bean;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
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

  private static final PropertyNameResolver resolver = new PropertyNameResolver();

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

  @SuppressWarnings("unchecked")
  public static <T> T getProperty(Object bean, String name) {
    // Resolve nested references
    while (resolver.hasNested(name)) {
      String next = resolver.next(name);
      Object nestedBean = null;
      if (bean instanceof Map) {
        nestedBean = getPropertyOfMapBean((Map<?, ?>) bean, next);
      } else if (resolver.isMapped(next)) {
        nestedBean = getMappedProperty(bean, next);
      } else if (resolver.isIndexed(next)) {
        nestedBean = getIndexedProperty(bean, next);
      } else {
        nestedBean = getSimpleProperty(bean, next);
      }
      if (nestedBean == null) return null;
      bean = nestedBean;
      name = resolver.remove(name);
    }

    if (bean instanceof Map) {
      bean = getPropertyOfMapBean((Map<?, ?>) bean, name);
    } else if (resolver.isMapped(name)) {
      bean = getMappedProperty(bean, name);
    } else if (resolver.isIndexed(name)) {
      bean = getIndexedProperty(bean, name);
    } else {
      bean = getSimpleProperty(bean, name);
    }
    return (T) bean;
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
      logger.warn("Cannot find {} set method in ", name, bean.getClass());
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

  @SuppressWarnings("unchecked")
  public static <T> T getSimpleProperty(Object bean, String name) {
    MethodInfo info = ClassInfo.get(bean.getClass()).getReader(name);
    if (null == info) {
      logger.warn("Cannot find get" + Strings.capitalize(name) + " in " + bean.getClass());
      return null;
    }
    try {
      return (T) info.method.invoke(bean);
    } catch (Exception e) {
      throw Throwables.propagate(e);
    }
  }

  private static Object getPropertyOfMapBean(Map<?, ?> bean, String propertyName) {
    String name = resolver.getProperty(propertyName);
    if (name == null || name.length() == 0) propertyName = resolver.getKey(propertyName);
    return bean.get(propertyName);
  }

  private static Object getMappedProperty(Object bean, String name) {
    String key = resolver.getKey(name);
    if (key == null) { throw new IllegalArgumentException("Invalid mapped property '" + name + "'"); }
    Object value = getSimpleProperty(bean, resolver.getProperty(name));
    if (null == value) return null;
    return ((Map<?, ?>) value).get(key);
  }

  private static Object getIndexedProperty(Object bean, String name) {
    int index = resolver.getIndex(name);
    if (index < 0) { throw new IllegalArgumentException("Invalid indexed property '" + name + "'"); }
    Object value = getSimpleProperty(bean, resolver.getProperty(name));
    if (null == value) return null;

    if (!value.getClass().isArray()) return (Array.get(value, index));
    else return ((List<?>) value).get(index);
  }
}
