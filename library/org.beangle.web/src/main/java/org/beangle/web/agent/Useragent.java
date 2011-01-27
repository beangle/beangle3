/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.web.agent;

import java.io.Serializable;

/**
 * @author chaostone
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
	 * Indicates the TCP/IP address the authentication request was received
	 * from.
	 * 
	 * @return
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
