/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.access.log;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;


public interface ResourceAccessor {

  AccessLog beginAccess(HttpServletRequest request, Date date);

  void endAccess(AccessLog log, Date date);

  void start();

  void finish();
}
