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
package org.beangle.commons.lang.asm;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.conversion.Conversion;
import org.beangle.commons.lang.conversion.impl.ConvertUtils;
import org.beangle.commons.lang.reflect.ClassInfo;
import org.beangle.commons.lang.reflect.MethodInfo;

/**
 * Mirror utility methods
 * 
 * @author chaostone
 * @since 3.2.0
 */
public class Mirrors {

  public static boolean isReadable(Object bean, String name) {
    return null != ClassInfo.get(bean.getClass()).getReader(name);
  }

  public static boolean isWriteable(Object bean, String name) {
    return null != ClassInfo.get(bean.getClass()).getWriter(name);
  }

  /**
   * @throws NoSuchMethodException
   * @param bean
   * @param name
   * @param value
   */
  public static void setProperty(Object bean, String name, Object value) {
    Mirror mirror = Mirror.get(bean.getClass());
    mirror.invoke(bean, mirror.classInfo.getWriteIndex(name), value);
  }

  @SuppressWarnings("unchecked")
  public static <T> T getProperty(Object bean, String name) {
    Mirror mirror = Mirror.get(bean.getClass());
    return (T) mirror.invoke(bean, mirror.classInfo.getReadIndex(name));
  }

  public static Object copyProperty(Object bean, String name, Object value, Conversion conversion) {
    ClassInfo info = ClassInfo.get(bean.getClass());
    Object converted = conversion.convert(value, info.getPropertyType(name));
    Mirror.get(bean.getClass()).invoke(bean, info.getWriteIndex(name), converted);
    return converted;
  }

  public static Object copyProperty(Object bean, String name, Object value) {
    ClassInfo info = ClassInfo.get(bean.getClass());
    Object converted = ConvertUtils.convert(value, info.getPropertyType(name));
    Mirror.get(bean.getClass()).invoke(bean, info.getWriteIndex(name), converted);
    return converted;
  }

  public static Mirror none() {
    return new NoneMirror();
  }

  public static class NoneMirror extends Mirror {

    public NoneMirror() {
      super();
      this.classInfo = new ClassInfo(CollectUtils.<MethodInfo> newArrayList());
    }

    @Override
    public Object invoke(Object object, int methodIndex, Object... args) {
      return null;
    }

  }
}
