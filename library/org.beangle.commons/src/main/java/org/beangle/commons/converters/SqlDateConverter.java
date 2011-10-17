/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.converters;

import java.sql.Date;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;
import org.beangle.commons.lang.StrUtils;

/**
 * This class is converts a java.util.Date to a String and a String to a
 * java.util.Date.
 */
public class SqlDateConverter implements Converter {

	@SuppressWarnings("rawtypes")
	public Object convert(final Class type, final Object value) {
		if (value == null) {
			return null;
		} else if (type == Date.class) {
			return convertToDate(value);
		} else if (type == String.class) { return convertToString(value); }

		throw new ConversionException("Could not convert " + value.getClass().getName() + " to "
				+ type.getName());
	}

	/**
	 * 将日期字符串转换成日期<br>
	 * format 1: yyyy-MM-dd<br>
	 * format 2: yyyyMMdd
	 * 
	 * @param type
	 * @param value
	 * @return
	 */
	protected Object convertToDate(final Object value) {
		if (value instanceof String) {
			if (StringUtils.isEmpty((String) value)) {
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

	protected Object convertToString(Object value) {
		return value.toString();
	}

	private static String normalize_bad(String dateStr) {
		if (!StringUtils.contains(dateStr, "-")) {
			StringBuilder dateBuf = new StringBuilder(dateStr);
			dateBuf.insert("yyyyMM".length(), '-');
			dateBuf.insert("yyyy".length(), '-');
			return dateBuf.toString();
		} else {
			if (dateStr.length() >= 10) return dateStr;
			else {
				int firstDash = dateStr.indexOf('-');
				int secondDash = dateStr.indexOf('-', firstDash + 1);
				String year = dateStr.substring(0, firstDash);
				String month = dateStr.substring(firstDash + 1, secondDash);
				String day = dateStr.substring(secondDash + 1);
				month = StringUtils.leftPad(month, 2, '0');
				day = StringUtils.leftPad(day, 2, '0');
				return StrUtils.concat(year, "-", month, "-", day);
			}
		}
	}

	public static String normalize(String dateStr) {
		if (!StringUtils.contains(dateStr, "-")) {
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
				if(value[6]=='-') dayIndex=7;
				if(value[7]=='-') dayIndex=8;
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
