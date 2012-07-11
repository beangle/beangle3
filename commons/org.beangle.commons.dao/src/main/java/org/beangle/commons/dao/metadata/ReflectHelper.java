/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.dao.metadata;

import java.lang.reflect.Method;

import org.apache.commons.beanutils.MethodUtils;
import org.beangle.commons.lang.Strings;

/**
 * <p>
 * ReflectHelper class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public final class ReflectHelper {

  private ReflectHelper() {
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
}
