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
package org.beangle.security.ids;

import jakarta.servlet.http.HttpServletRequest;

import org.beangle.commons.bean.Initializing;
import org.beangle.commons.lang.Assert;
import org.beangle.commons.web.util.RequestUtils;

/**
 * Stores properties related to this CAS service.
 * <p>
 * Each web application capable of processing CAS tickets is known as a service. This class stores
 * the properties that are relevant to the local CAS service, being the application that is being
 * secured by Beangle Security.
 *
 * @author chaostone
 * @version $Id: ServiceProperties.java $
 */
public class CasConfig implements Initializing {

  private String casServer;

  private boolean renew = false;

  private boolean encode = true;

  private String artifactName = "ticket";

  private String loginUri = "/login";

  private String validateUri = "/serviceValidate";

  private String checkAliveUri = "/checkAlive";

  public CasConfig() {
    super();
  }

  public CasConfig(String casServer) {
    super();
    this.casServer = casServer;
  }

  public void init() throws Exception {
    Assert.notEmpty(this.casServer, "cas server must be specified.");
    Assert.isTrue(!this.casServer.endsWith("/"), "cas server should not end with /");
    Assert.notEmpty(this.loginUri, "loginUri must be specified. like /login");
    Assert.notEmpty(this.artifactName, "artifact name  must be specified.etc. ticket");
  }

  public String getCasServer() {
    return casServer;
  }

  public void setCasServer(String casServer) {
    if (casServer.endsWith("/")) this.casServer = casServer.substring(0, casServer.length() - 1);
    else this.casServer = casServer;
  }

  public static String getLocalServer(HttpServletRequest request) {
    StringBuilder sb = new StringBuilder();
    String scheme = RequestUtils.isHttps(request) ? "https" : "http";
    int port = RequestUtils.getServerPort(request);
    String serverName = request.getServerName();
    boolean includePort = true;
    if (null != scheme) {
      sb.append(scheme).append("://");
      includePort = (port != (scheme.equals("http") ? 80 : 443));
    }
    if (null != serverName) {
      sb.append(serverName);
      if (includePort && port > 0) {
        sb.append(':').append(port);
      }
    }
    return sb.toString();
  }

  /**
   * The enterprise-wide CAS login URL. Usually something like
   * <code>https://www.mycompany.com/cas/login</code>.
   *
   * @return the enterprise-wide CAS login URL
   */
  public String getLoginUrl() {
    return casServer + loginUri;
  }

  /**
   * Indicates whether the <code>renew</code> parameter should be sent to the
   * CAS login URL and CAS validation URL.
   * <p>
   * If <code>true</code>, it will force CAS to authenticate the user again (even if the user has
   * previously authenticated). During ticket validation it will require the ticket was generated as
   * a consequence of an explicit login. High security applications would probably set this to
   * <code>true</code>. Defaults to <code>false</code>, providing automated single sign on.
   *
   * @return whether to send the <code>renew</code> parameter to CAS
   */
  public boolean isRenew() {
    return renew;
  }

  public void setRenew(boolean renew) {
    this.renew = renew;
  }

  public boolean isEncode() {
    return encode;
  }

  public void setEncode(boolean encode) {
    this.encode = encode;
  }

  public String getLoginUri() {
    return loginUri;
  }

  public void setLoginUri(String loginUri) {
    this.loginUri = loginUri;
  }

  public String getArtifactName() {
    return artifactName;
  }

  public void setArtifactName(String artifactName) {
    this.artifactName = artifactName;
  }

  public String getValidateUri() {
    return validateUri;
  }

  public void setValidateUri(String validateUri) {
    this.validateUri = validateUri;
  }

  public String getCheckAliveUri() {
    return checkAliveUri;
  }

  public void setCheckAliveUri(String checkAliveUri) {
    this.checkAliveUri = checkAliveUri;
  }

}
