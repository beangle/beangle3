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
package org.beangle.commons.bean.converters;

import java.sql.Date;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.beangle.commons.lang.Strings;

/**
 * This class is converts a java.util.Date to a String and a String to a
 * java.util.Date.
 * 
 * @author chaostone
 * @version $Id: $
 */
public class SqlDateConverter implements Converter {

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  public Object convert(final Class type, final Object value) {
    if (value == null) {
      return null;
    } else if (type == Date.class) {
      return convertToDate(value);
    } else if (type == String.class) { return convertToString(value); }

    throw new ConversionException("Could not convert " + value.getClass().getName() + " to " + type.getName());
  }

  /**
   * 将日期字符串转换成日期<br>
   * format 1: yyyy-MM-dd<br>
   * format 2: yyyyMMdd
   * 
   * @param value a {@link java.lang.Object} object.
   * @return a {@link java.lang.Object} object.
   */
  protected Object convertToDate(final Object value) {
    if (value instanceof String) {
      if (Strings.isEmpty((String) value)) {
        return null;
      } else {
        String dateStr = (String) value;
        // 修复了jdk 1.6_u26 的错误
        return Date.valueOf(normalize(dateStr));
      }
    } else if (value instanceof java.util.Date) {
      return new Date(((java.util.Date) value).getTime());
    } else {
      return null;
    }
  }

  /**
   * <p>
   * convertToString.
   * </p>
   * 
   * @param value a {@link java.lang.Object} object.
   * @return a {@link java.lang.Object} object.
   */
  protected Object convertToString(Object value) {
    return value.toString();
  }

  /**
   * <p>
   * normalize.
   * </p>
   * 
   * @param dateStr a {@link java.lang.String} object.
   * @return a {@link java.lang.String} object.
   */
  public static String normalize(String dateStr) {
    if (!dateStr.contains("-")) {
      StringBuilder dateBuf = new StringBuilder(dateStr);
      dateBuf.insert("yyyyMM".length(), '-');
      dateBuf.insert("yyyy".length(), '-');
      return dateBuf.toString();
    } else {
      if (dateStr.length() >= 10) return dateStr;
      else if (dateStr.length() < 8) throw new IllegalArgumentException();
      else {
        // try 2009-9-1
        char[] value = dateStr.toCharArray();
        int dayIndex = -1;
        if (value[6] == '-') dayIndex = 7;
        if (value[7] == '-') dayIndex = 8;
        if (dayIndex < 0) throw new IllegalArgumentException();
        StringBuilder sb = new StringBuilder(10);

        // append year-
        sb.append(value, 0, 5);

        // append month-
        if (dayIndex - 5 < 3) sb.append('0').append(value, 5, 2);
        else sb.append(value, 5, 3);

        // append day
        if (value.length - dayIndex < 2) sb.append('0').append(value, dayIndex, 1);
        else sb.append(value, dayIndex, 2);

        return sb.toString();
      }
    }
  }
}
