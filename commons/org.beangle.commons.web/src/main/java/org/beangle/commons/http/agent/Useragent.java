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
 * Container class for user-agent information with operating system and browser details.
 * Resources:<br>
 * <a href="http://www.useragentstring.com">User Agent String.Com</a><br>
 * <a href="http://www.user-agents.org">List of User-Agents</a><br>
 * <a href="http://user-agent-string.info">user-agent-string.info</a><br>
 * <a href="http://www.zytrax.com/tech/web/browser_ids.htm">Browser ID (User-Agent) Strings</a><br>
 * <a href="http://www.zytrax.com/tech/web/mobile_ids.html">Mobile Browser ID (User-Agent)
 * Strings</a><br>
 * <a href="http://www.joergkrusesweb.de/internet/browser/user-agent.html">Browser-Kennungen</a><br>
 * <a href="http://deviceatlas.com/devices">Device Atlas - Mobile Device Intelligence</a><br>
 * <a href="http://mobileopera.com/reference/ua">Mobile Opera user-agent strings</a><br>
 * <a href="http://en.wikipedia.org/wiki/S60_platform">S60 platform</a><br>
 * <a href="http://msdn.microsoft.com/en-us/library/ms537503.aspx">Understanding User-Agent
 * Strings</a><br>
 * <a href="http://developer.apple.com/internet/safari/faq.html#anchor2">What is the Safari
 * user-agent string</a><br>
 * <a href="http://www.pgts.com.au/cgi-bin/psql?agent_main">List of User Agent Strings</a><br>
 * <a href="http://blogs.msdn.com/iemobile/archive/2006/08/03/Detecting_IE_Mobile.aspx">Detecting
 * Internet Explorer Mobile's User-Agent on the server</a>
 * 
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
