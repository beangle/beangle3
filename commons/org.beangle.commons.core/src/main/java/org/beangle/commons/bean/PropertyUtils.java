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

import org.beangle.commons.lang.Throwables;
import org.beangle.commons.lang.asm.ClassInfo;
import org.beangle.commons.lang.conversion.Conversion;
import org.beangle.commons.lang.conversion.impl.ConvertUtils;

/**
 * @author chaostone
 */
public class PropertyUtils {

  /**
   * @throws NoSuchMethodException
   * @param bean
   * @param name
   * @param value
   */
  public static void setProperty(Object bean, String name, Object value) {
    try {
      ClassInfo.get(bean.getClass()).getWriteMethod(name).invoke(bean, value);
    } catch (Exception e) {
      Throwables.propagate(e);
    }
  }

  public static Object getProperty(Object bean, String name) {
    try {
      return ClassInfo.get(bean.getClass()).getReadMethod(name).invoke(bean);
    } catch (Exception e) {
      throw Throwables.propagate(e);
    }
  }

  public static void copyProperty(Object bean, String name, Object value, Conversion conversion) {
    ClassInfo classInfo = ClassInfo.get(bean.getClass());
    try {
      classInfo.getWriteMethod(name).invoke(bean, conversion.convert(value, classInfo.getPropertyType(name)));
    } catch (Exception e) {
      Throwables.propagate(e);
    }
  }

  public static void copyProperty(Object bean, String name, Object value) {
    ClassInfo classInfo = ClassInfo.get(bean.getClass());
    try {
      classInfo.getWriteMethod(name).invoke(bean,
          ConvertUtils.convert(value, classInfo.getPropertyType(name)));
    } catch (Exception e) {
      Throwables.propagate(e);
    }
  }
}
