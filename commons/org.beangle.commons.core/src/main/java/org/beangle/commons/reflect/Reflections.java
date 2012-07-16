/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import org.apache.commons.beanutils.MethodUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;

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
  public static Class<?> getProperty(Class<?> clazz, String property) {
    Method getMethod = MethodUtils.getAccessibleMethod(clazz, "get" + Strings.capitalize(property),
        (Class[]) null);
    if (null == getMethod) {
      return null;
    } else {
      return getMethod.getReturnType();
    }
  }

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
}
