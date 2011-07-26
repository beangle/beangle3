/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.lang;

import java.util.Calendar;
import java.util.Date;

import org.testng.annotations.Test;
import static org.testng.Assert.*;
/**
 *
 * @author chaostone
 * @version $Id: CalendarTest.java Jul 26, 2011 4:12:17 PM chaostone $
 */
@Test
public class CalendarTest {

	public void  testRoll(){
		Calendar calendar= Calendar.getInstance();
		Date ajusted= DateUtils.rollMinutes(calendar.getTime(),-30);
		assertTrue(ajusted.before(calendar.getTime()));
	}
}
