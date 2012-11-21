/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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
package org.beangle.commons.http.agent;

import java.io.Serializable;

/**
 * @author chaostone
 * @since 2.4
 * @version $Id: Useragent.java Nov 15, 2010 7:39:29 AM chaostone $
 */
public class Useragent implements Serializable {

  private static final long serialVersionUID = 367387526753100612L;

  private final String ip;

  private final Os os;

  private final Browser browser;

  public Useragent(String ip, Browser browser, Os os) {
    super();
    this.ip = ip;
    this.browser = browser;
    this.os = os;
  }

  /**
   * Returns the TCP/IP address the authentication request was received from.
   */
  public String getIp() {
    return ip;
  }

  public Os getOs() {
    return os;
  }

  public Browser getBrowser() {
    return browser;
  }

}
