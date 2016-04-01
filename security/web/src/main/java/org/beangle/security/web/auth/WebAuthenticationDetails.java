/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.web.auth;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.beangle.commons.http.agent.Useragent;
import org.beangle.commons.web.util.RequestUtils;
import org.beangle.security.core.session.SessionIdAware;

public class WebAuthenticationDetails implements SessionIdAware, Serializable {

  private static final long serialVersionUID = -8543528078535952987L;

  private String sessionId;
  private Useragent agent;

  private transient String lastAccessUri;

  private transient String server;

  public WebAuthenticationDetails(HttpServletRequest request) {
    agent = RequestUtils.getUserAgent(request);
    HttpSession session = request.getSession(true);
    sessionId = (session != null) ? session.getId() : null;
    server = request.getLocalAddr() + ":" + request.getLocalPort();
    doPopulateAdditionalInformation(request);
  }

  /**
   * Provided so that subclasses can populate additional information.
   * 
   * @param request
   *          that the authentication request was received from
   */
  protected void doPopulateAdditionalInformation(HttpServletRequest request) {
  }

  /**
   * Indicates the <code>HttpSession</code> id the authentication request was
   * received from.
   * 
   * @return the session ID
   */
  public String getSessionId() {
    return sessionId;
  }

  public Useragent getAgent() {
    return agent;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append(super.toString() + ": ");
    sb.append("SessionId: ").append(getSessionId()).append("; ");
    sb.append("RemoteIpAddress: ").append(agent.getIp()).append("; ");
    sb.append("Operation System: ").append(agent.getOs()).append("; ");
    sb.append("Browser: ").append(agent.getBrowser());
    return sb.toString();
  }

  public String getLastAccessUri() {
    return lastAccessUri;
  }

  public void setLastAccessUri(String lastAccessUri) {
    this.lastAccessUri = lastAccessUri;
  }

  public String getServer() {
    return server;
  }

}
