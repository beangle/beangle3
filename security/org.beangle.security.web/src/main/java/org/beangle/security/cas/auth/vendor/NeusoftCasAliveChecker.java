/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.cas.auth.vendor;

import javax.servlet.http.HttpServletRequest;

import org.beangle.commons.lang.Strings;
import org.beangle.commons.web.util.HttpUtils;
import org.beangle.security.cas.CasConfig;
import org.beangle.security.cas.auth.CasAuthentication;
import org.beangle.security.core.Authentication;
import org.beangle.security.web.auth.preauth.AuthenticationAliveChecker;

public class NeusoftCasAliveChecker implements AuthenticationAliveChecker {

  private CasConfig config;

  public boolean check(Authentication auth, HttpServletRequest request) {
    if (auth instanceof CasAuthentication) {
      CasAuthentication casAu = (CasAuthentication) auth;
      String key = (String) casAu.getAssertion().getTicket();
      if (null != key) {
        String text = HttpUtils.getResponseText(Strings.concat(config.getCasServer(),
            config.getCheckAliveUri(), "?", config.getArtifactName(), "=", key));
        return Strings.contains(text, "true");
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
