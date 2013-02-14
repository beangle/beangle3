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
package org.beangle.commons.lang.reflect;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.lang.Throwables;

/**
 * <p>
 * ReflectHelper class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public final class Reflections {

  private Reflections() {
  }

  /**
   * Return the Java Class representing the property type of the specified
   * property, or <code>null</code> if there is no such property for the
   * specified bean.
   * this method using Introspector.getBeanInfo not lying on readmethod.
   * For generic super class read method's return type is more general.
   */
  public static Class<?> getPropertyType(Class<?> clazz, String property) {
    BeanInfo beanInfo = null;
    try {
      beanInfo = Introspector.getBeanInfo(clazz);
    } catch (IntrospectionException e) {
      return null;
    }
    PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
    if (null == descriptors) return null;
    for (PropertyDescriptor pd : descriptors) {
      if (pd.getName().equals(property)) return pd.getPropertyType();
    }
    return null;
  }

  /**
   * Return setter method.
   * 
   * @param clazz
   * @param property
   * @return null when not found.
   */
  public static Method getSetter(Class<?> clazz, String property) {
    String setName = "set" + Strings.capitalize(property);
    for (Method m : clazz.getMethods()) {
      if (m.getName().equals(setName)) {
        if (Modifier.isPublic(m.getModifiers()) && !Modifier.isStatic(m.getModifiers())
            && m.getParameterTypes().length == 1) {
          return m;
        } else {
          return null;
        }
      }
    }
    return null;
  }

  /**
   * Return list of setters
   * 
   * @param clazz
   */
  public static List<Method> getBeanSetters(Class<?> clazz) {
    List<Method> methods = CollectUtils.newArrayList();
    for (Method m : clazz.getMethods()) {
      if (m.getName().startsWith("set") && m.getName().length() > 3) {
        if (Modifier.isPublic(m.getModifiers()) && !Modifier.isStatic(m.getModifiers())
            && m.getParameterTypes().length == 1) {
          methods.add(m);
        }
      }
    }
    return methods;
  }

  public static <T> T newInstance(Class<T> clazz) {
    try {
      return clazz.newInstance();
    } catch (Exception e) {
      Throwables.propagate(e);
    }
    return null;
  }

}
