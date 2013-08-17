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
package org.beangle.commons.conversion.converter;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.beangle.commons.lang.Numbers;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.conversion.Converter;

/**
 * <p>
 * DateConverter class.
 * </p>
 * 
 * @author chaostone
 * @since 3.2.0
 * @version $Id: $
 */
public class String2DateConverter extends StringConverterFactory<String, Date> {

  public String2DateConverter() {
    register(Date.class, new DateConverter());
    register(java.sql.Date.class, new SqlDateConverter());
  }

  private static class DateConverter implements Converter<String, Date> {
    @Override
    public Date apply(String value) {
      if (Strings.isEmpty((String) value)) { return null; }
      String dateStr = (String) value;
      String[] times = Strings.split(dateStr, " ");
      String[] dateElems = null;
      if (Strings.contains(times[0], "-")) {
        dateElems = Strings.split(times[0], "-");
      } else {
        dateElems = new String[3];
        int yearIndex = "yyyy".length();
        dateElems[0] = Strings.substring(times[0], 0, yearIndex);
        dateElems[1] = Strings.substring(times[0], yearIndex, yearIndex + 2);
        dateElems[2] = Strings.substring(times[0], yearIndex + 2, yearIndex + 4);
      }
      Calendar gc = GregorianCalendar.getInstance();
      gc.set(Calendar.YEAR, Numbers.toInt(dateElems[0]));
      gc.set(Calendar.MONTH, Numbers.toInt(dateElems[1]) - 1);
      gc.set(Calendar.DAY_OF_MONTH, Numbers.toInt(dateElems[2]));
      if (times.length > 1 && Strings.isNotBlank(times[1])) {
        String[] timeElems = Strings.split(times[1], ":");
        if (timeElems.length > 0) gc.set(Calendar.HOUR_OF_DAY, Numbers.toInt(timeElems[0]));
        if (timeElems.length > 1) gc.set(Calendar.MINUTE, Numbers.toInt(timeElems[1]));
        if (timeElems.length > 2) gc.set(Calendar.SECOND, Numbers.toInt(timeElems[2]));
      }
      return gc.getTime();

    }

  }

  private static class SqlDateConverter implements Converter<String, java.sql.Date> {
    @Override
    public java.sql.Date apply(String input) {
      // 修复了jdk 1.6_u26 的错误
      return java.sql.Date.valueOf(normalize(input));
    }

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
