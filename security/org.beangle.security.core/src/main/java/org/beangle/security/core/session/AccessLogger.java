/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session;

/**
 * 访问记录监控器
 * 
 * @author chaostone
 * @version $Id: AccessMonitor.java Jul 8, 2011 7:53:04 PM chaostone $
 */
public interface AccessLogger {

  /**
   * 更新对应sessionId的最后访问时间
   * 
   * @param sessionid
   */
  void log(String sessionid, String username, String resource, long beginAt, long endAt);

}
