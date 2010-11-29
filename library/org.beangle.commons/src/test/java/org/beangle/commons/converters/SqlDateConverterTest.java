/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.converters;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.testng.annotations.Test;

public class SqlDateConverterTest {
	@Test
	public void testConvertoDate() throws Exception {
		String date1 = "19800909";
		converToDate(date1, 1980, 8, 9);
		date1 = "1980-09-09";
		converToDate(date1, 1980, 8, 9);
	}

	@Test
	public void testConvertBasic() {
		assertEquals(0L, ConvertUtils.convert("", Long.class));
		ConvertUtils.register(new LongConverter(null), Long.class);
		assertNull(ConvertUtils.convert("", Long.class));
	}

	private void converToDate(String dateStr, int year, int month, int day) {
		Converter c = new SqlDateConverter();
		Date date = (Date) c.convert(Date.class, dateStr);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		assertEquals(calendar.get(Calendar.YEAR), year);
		assertEquals(calendar.get(Calendar.MONTH), month);
		assertEquals(calendar.get(Calendar.DAY_OF_MONTH), day);
	}
}
