/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.log;

/**
 * 业务日志明细
 * 
 * @author chaostone
 * @version $Id: BusinessLogDetail.java Aug 1, 2011 3:20:46 PM chaostone $
 */
public interface BusinessLogDetail {

	public String getConent();

	public BusinessLog getLog();

}
