/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.web.access;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.beangle.commons.lang.Strings;
import org.beangle.commons.web.util.RequestUtils;

/**
 * Default access request builder
 * 
 * @author chaostone
 * @since 3.0.1
 */
public class DefaultAccessRequestBuilder implements AccessRequestBuilder {

  public AccessRequest build(HttpServletRequest request) {
    AccessRequest ar = null;
    HttpSession session = request.getSession(false);
    if (null != session) {
      String sessionid = session.getId();
      String username = abtainUsername(request);
      if (Strings.isNotEmpty(username)) {
        ar = new AccessRequest(sessionid, username, RequestUtils.getServletPath(request));
        ar.setParams(request.getQueryString());
      }
    }
    return ar;
  }

  /**
   * Return remote user name
   */
  protected String abtainUsername(HttpServletRequest request) {
    return request.getRemoteUser();
  }

}
