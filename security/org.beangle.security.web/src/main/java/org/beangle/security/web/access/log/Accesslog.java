/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.access.log;

import java.util.Date;

public interface Accesslog {

	public long getBeginAt();

	public void setBeginAt(long start);

	public long getEndAt();

	public void setEndAt(long end);

	public long getDuration();

	public Date getBeginTime();

	public Date getEndTime();
}
