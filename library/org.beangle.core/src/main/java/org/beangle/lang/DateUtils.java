/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.lang;

import java.util.Date;

/**
 * @author chaostone
 * @version $Id: DateUtils.java Jul 26, 2011 4:22:37 PM chaostone $
 */
public final class DateUtils {

	public static Date rollMinutes(Date date, int mount) {
		// Do not user calendar.roll. it will never modify hour
		return new Date(date.getTime() + mount * 60 * 1000);
	}
}
