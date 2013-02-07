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
package org.beangle.commons.lang.asm;

import org.beangle.commons.lang.conversion.Conversion;
import org.beangle.commons.lang.conversion.impl.ConvertUtils;
import org.beangle.commons.lang.reflect.ClassInfo;

public class ProxyUtils {

  public static boolean hasProperty(Object bean, String name) {
    return null == ClassInfo.get(bean.getClass()).getPropertyType(name);
  }

  /**
   * @throws NoSuchMethodException
   * @param bean
   * @param name
   * @param value
   */
  public static void setProperty(Object bean, String name, Object value) {
    AccessProxy.get(bean.getClass()).setProperty(bean, name, value);
  }

  public static Object getProperty(Object bean, String name) {
    return AccessProxy.get(bean.getClass()).getProperty(bean, name);
  }

  public static void copyProperty(Object bean, String name, Object value, Conversion conversion) {
    AccessProxy.get(bean.getClass()).setProperty(bean, name,
        conversion.convert(value, ClassInfo.get(bean.getClass()).getPropertyType(name)));
  }

  public static void copyProperty(Object bean, String name, Object value) {
    AccessProxy.get(bean.getClass()).setProperty(bean, name,
        ConvertUtils.convert(value, ClassInfo.get(bean.getClass()).getPropertyType(name)));
  }
  
}
