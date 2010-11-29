/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.converters;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class DateConverter implements Converter {

	public Object convert(@SuppressWarnings("rawtypes") final Class type, final Object value) {
		if (value == null) {
			return null;
		} else if (type == Date.class) {
			return convertToDate(type, value);
		} else if (type == String.class) { return convertToString(type, value); }

		throw new ConversionException("Could not convert " + value.getClass().getName() + " to "
				+ type.getName());
	}

	/**
	 * 将字符串格式化为日期<br>
	 * format 1: yyyy-MM-dd hh:mm:ss<br>
	 * format 2: yyyyMMdd
	 */
	@SuppressWarnings("rawtypes")
	protected Object convertToDate(final Class type, final Object value) {
		if (StringUtils.isEmpty((String) value)) {
			return null;
		} else {
			String dateStr = (String) value;
			String[] times = StringUtils.split(dateStr, " ");
			String[] dateElems = null;
			if (StringUtils.contains(times[0], "-")) {
				dateElems = StringUtils.split(times[0], "-");
			} else {
				dateElems = new String[3];
				int yearIndex = "yyyy".length();
				dateElems[0] = StringUtils.substring(times[0], 0, yearIndex);
				dateElems[1] = StringUtils.substring(times[0], yearIndex, yearIndex + 2);
				dateElems[2] = StringUtils.substring(times[0], yearIndex + 2, yearIndex + 4);
			}
			Calendar gc = GregorianCalendar.getInstance();
			gc.set(Calendar.YEAR, NumberUtils.toInt(dateElems[0]));
			gc.set(Calendar.MONTH, NumberUtils.toInt(dateElems[1]) - 1);
			gc.set(Calendar.DAY_OF_MONTH, NumberUtils.toInt(dateElems[2]));
			if (times.length > 1 && StringUtils.isNotBlank(times[1])) {
				String[] timeElems = StringUtils.split(times[1], ":");
				if (timeElems.length > 0) {
					gc.set(Calendar.HOUR_OF_DAY, NumberUtils.toInt(timeElems[0]));
				}
				if (timeElems.length > 1) {
					gc.set(Calendar.MINUTE, NumberUtils.toInt(timeElems[1]));
				}
				if (timeElems.length > 2) {
					gc.set(Calendar.SECOND, NumberUtils.toInt(timeElems[2]));
				}
			}
			return gc.getTime();
		}
	}

	protected Object convertToString(final Class<?> type, final Object value) {
		return value.toString();
	}

}
