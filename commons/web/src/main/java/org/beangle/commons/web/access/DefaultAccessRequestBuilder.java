/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
