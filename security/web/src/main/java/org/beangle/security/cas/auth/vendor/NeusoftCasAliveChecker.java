/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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
