/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.web.access;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chaostone
 * @since 3.0.1
 */
public interface AccessRequestBuilder {

  /**
   * Build a access request
   * @param request servlet request
   */
  public AccessRequest build(HttpServletRequest request);
}
