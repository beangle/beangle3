/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.web.access;

/**
 * 访问记录监控器
 * 
 * @author chaostone
 * @version $Id: AccessMonitor.java Jul 8, 2011 7:53:04 PM chaostone $
 */
public interface AccessLogger {

  /**
   * Log the request.
   */
  void log(AccessRequest request);

}
