/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.bean.converters;

import static org.testng.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.beanutils.Converter;
import org.testng.annotations.Test;

public class DateConverterTest {
	@Test
	public void testConvertoDate() throws Exception {
		String date1 = "19800909";
		converToDate(date1, 1980, 8, 9);
		date1 = "1980-09-09";
		converToDate(date1, 1980, 8, 9);
	}

	private void converToDate(String dateStr, int year, int month, int day) {
		Converter c = new DateConverter();
		Date date = (Date) c.convert(Date.class, dateStr);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		assertEquals(calendar.get(Calendar.YEAR), year);
		assertEquals(calendar.get(Calendar.MONTH), month);
		assertEquals(calendar.get(Calendar.DAY_OF_MONTH), day);
	}
}
