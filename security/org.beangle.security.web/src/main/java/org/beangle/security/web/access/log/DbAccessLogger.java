/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.access.log;

import org.beangle.commons.dao.impl.BaseServiceImpl;

/**
 * 缺省的访问监控器
 * 
 * @author chaostone
 * @version $Id: DefaultAccessMonitor.java Jul 8, 2011 7:59:59 PM chaostone $
 */
public class DbAccessLogger extends BaseServiceImpl implements AccessLogger {

  public void log(String sessionid, String username, String resource, long beginAt, long endAt) {
    entityDao.saveOrUpdate(new AccessLog(sessionid, username, resource, beginAt, endAt));
  }

}
