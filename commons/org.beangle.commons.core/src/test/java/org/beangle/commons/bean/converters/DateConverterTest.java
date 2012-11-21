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
