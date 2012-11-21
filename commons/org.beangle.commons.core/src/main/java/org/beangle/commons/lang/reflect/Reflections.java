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
package org.beangle.commons.lang.reflect;

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
   * <p>
   * getProperty.
   * </p>
   * 
   * @param clazz a {@link java.lang.Class} object.
   * @param property a {@link java.lang.String} object.
   * @return a {@link java.lang.Class} object.
   */
  public static Class<?> getPropertyType(Class<?> clazz, String property) {
    // MethodUtils.getAccessibleMethod(clazz, "get" + Strings.capitalize(property),(Class[]) null);
    Method method = null;
    try {
      method = clazz.getMethod("get" + Strings.capitalize(property), (Class[]) null);
    } catch (Exception e) {
    }
    try {
      if (null == method) method = clazz.getMethod("is" + Strings.capitalize(property), (Class[]) null);
    } catch (Exception e) {
    }
    return null == method ? null : method.getReturnType();
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
