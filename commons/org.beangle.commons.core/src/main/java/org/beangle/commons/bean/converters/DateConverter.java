/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.bean.converters;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.beangle.commons.lang.Numbers;
import org.beangle.commons.lang.Strings;

/**
 * <p>
 * DateConverter class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class DateConverter implements Converter {

  /** {@inheritDoc} */
  public Object convert(@SuppressWarnings("rawtypes") final Class type, final Object value) {
    if (value == null) {
      return null;
    } else if (type == Date.class) {
      return convertToDate(type, value);
    } else if (type == String.class) { return convertToString(type, value); }

    throw new ConversionException("Could not convert " + value.getClass().getName() + " to " + type.getName());
  }

  /**
   * 将字符串格式化为日期<br>
   * format 1: yyyy-MM-dd hh:mm:ss<br>
   * format 2: yyyyMMdd
   * 
   * @param type a {@link java.lang.Class} object.
   * @param value a {@link java.lang.Object} object.
   * @return a {@link java.lang.Object} object.
   */
  @SuppressWarnings("rawtypes")
  protected Object convertToDate(final Class type, final Object value) {
    if (Strings.isEmpty((String) value)) {
      return null;
    } else {
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
        if (timeElems.length > 0) {
          gc.set(Calendar.HOUR_OF_DAY, Numbers.toInt(timeElems[0]));
        }
        if (timeElems.length > 1) {
          gc.set(Calendar.MINUTE, Numbers.toInt(timeElems[1]));
        }
        if (timeElems.length > 2) {
          gc.set(Calendar.SECOND, Numbers.toInt(timeElems[2]));
        }
      }
      return gc.getTime();
    }
  }

  /**
   * <p>
   * convertToString.
   * </p>
   * 
   * @param type a {@link java.lang.Class} object.
   * @param value a {@link java.lang.Object} object.
   * @return a {@link java.lang.Object} object.
   */
  protected Object convertToString(final Class<?> type, final Object value) {
    return value.toString();
  }

}
