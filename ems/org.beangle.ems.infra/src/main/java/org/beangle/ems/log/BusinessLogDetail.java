/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.log;

import org.beangle.model.pojo.LongIdEntity;

/**
 * 业务日志明细
 * 
 * @author chaostone
 * @version $Id: BusinessLogDetail.java Aug 1, 2011 4:25:24 PM chaostone $
 */
public interface BusinessLogDetail extends LongIdEntity {

	public String getContent();
}
