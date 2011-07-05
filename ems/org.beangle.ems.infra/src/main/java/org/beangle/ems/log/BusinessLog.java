/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.log;

import java.util.Date;

/**
 * 业务日志
 * 
 * @author chaostone
 * @version $Id: BusinessLog.java Jun 27, 2011 7:28:23 PM chaostone $
 */
public interface BusinessLog {

	public String getOperater();

	public void setOperater(String operater);

	public String getOperation();

	public void setOperation(String operation);

	public String getResource();

	public void setResource(String resource);

	public Date getOperateAt();

	public void setOperateAt(Date operateAt);

	public String getIp();

	public void setIp(String ip);

	public String getParams();

	public void setParams(String params);

	public String getEntry();

	public void setEntry(String entry);

}
