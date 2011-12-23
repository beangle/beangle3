/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.auth.preauth.j2ee;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.beangle.security.web.auth.preauth.UsernameSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Source of the username supplied with pre-authenticated authentication request
 * as remote user header value. Optionally can strip prefix: "domain\\username"
 * -> "username", if <tt>stripPrefix</tt> property value is "true".
 */
public class RemoteUsernameSource implements UsernameSource {
	/**
	 * Logger for this class and subclasses
	 */
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private boolean stripPrefix = true;

	public String obtainUsername(HttpServletRequest request) {
		String username = null;
		Principal p = request.getUserPrincipal();
		if (null != p) {
			username = p.getName();
		}
		if (StringUtils.isEmpty(username)) {
			username = request.getRemoteUser();
		}
		if (null != username && isStripPrefix()) {
			username = stripPrefix(username);
		}
		if (null != username) {
			logger.debug("Obtained username=[{}] from remote user", username);
		}
		return username;
	}

	private String stripPrefix(String userName) {
		if (StringUtils.isNotBlank(userName)) {
			int index = userName.lastIndexOf("\\");
			if (index != -1) {
				userName = userName.substring(index + 1);
			}
		}
		return userName;
	}

	/**
	 * @return the stripPrefix
	 */
	public boolean isStripPrefix() {
		return stripPrefix;
	}

	/**
	 * @param stripPrefix
	 *            the stripPrefix to set
	 */
	public void setStripPrefix(boolean stripPrefix) {
		this.stripPrefix = stripPrefix;
	}
}
