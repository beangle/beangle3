/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.web.access;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chaostone
 * @since 3.0.1
 */
public interface AccessMonitor {

  /**
   * Return access request content;
   * 
   * @param request the servlet request
   */
  AccessRequest begin(HttpServletRequest request);

  /**
   * finish this access
   */
  void end(AccessRequest request,HttpServletResponse response);

  /**
   * Return current request list.
   */
  List<AccessRequest> getRequests();

}
