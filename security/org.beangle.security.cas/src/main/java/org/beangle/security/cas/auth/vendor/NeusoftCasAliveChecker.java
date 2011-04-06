/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.cas.auth.vendor;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.lang.StrUtils;
import org.beangle.security.cas.CasConfig;
import org.beangle.security.cas.auth.CasAuthentication;
import org.beangle.security.core.Authentication;
import org.beangle.security.web.auth.preauth.AuthenticationAliveChecker;
import org.beangle.web.util.HttpUtils;

public class NeusoftCasAliveChecker implements AuthenticationAliveChecker {

	private CasConfig config;

	public boolean check(Authentication auth, HttpServletRequest request) {
		if (auth instanceof CasAuthentication) {
			CasAuthentication casAu = (CasAuthentication) auth;
			String key = (String) casAu.getAssertion().getAttributes().get("caKey");
			if (null != key) {
				String text = HttpUtils.getResponseText(StrUtils.concat(config.getCasServer(),
						config.getCheckAliveUri(), "?", config.getArtifactName(), "=", key));
				return StringUtils.contains(text, "true");
			}
		}
		return true;
	}

	public CasConfig getConfig() {
		return config;
	}

	public void setConfig(CasConfig config) {
		this.config = config;
	}
}
