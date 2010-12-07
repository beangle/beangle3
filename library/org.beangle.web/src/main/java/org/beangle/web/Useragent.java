/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.web;

/**
 * @author chaostone
 * @version $Id: Useragent.java Nov 15, 2010 7:39:29 AM chaostone $
 */
public class Useragent {

	private final String ip;

	private final String osName;

	private final String osVersion;

	private final String name;

	private final String version;

	public Useragent(String ip, String name, String version, String osName, String osVersion) {
		super();
		this.ip = ip;
		this.name = name;
		this.version = version;
		this.osName = osName;
		this.osVersion = osVersion;
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

	public String getOs() {
		if (null == osName) return null;
		return osName + ((null == osVersion) ? "" : "/" + osVersion);
	}

	public String getFullname() {
		if (null == name) return null;
		return name + ((null == version) ? "" : "/" + version);
	}

	public String getOsName() {
		return osName;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}

}
