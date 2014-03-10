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
package org.beangle.security.blueprint.session.service;

import org.beangle.security.blueprint.session.model.SessioninfoBean;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.session.Sessioninfo;
import org.beangle.security.core.session.SessioninfoBuilder;
import org.beangle.security.core.session.category.CategoryPrincipal;
import org.beangle.security.web.auth.WebAuthenticationDetails;

/**
 * @author chaostone
 * @version $Id: WebSessioninfoBuilder.java Jul 11, 2011 7:01:21 PM chaostone $
 */
public class WebSessioninfoBuilder implements SessioninfoBuilder {

  public Class<? extends Sessioninfo> getSessioninfoType() {
    return SessioninfoBean.class;
  }

  public Sessioninfo build(Authentication auth, String sessionid) {
    CategoryPrincipal principal = (CategoryPrincipal) auth.getPrincipal();
    SessioninfoBean info = new SessioninfoBean(sessionid, auth.getName(), principal.getFullname());
    info.setCategory(principal.getCategory());
    Object details = auth.getDetails();
    if (details instanceof WebAuthenticationDetails) {
      WebAuthenticationDetails webdetails = (WebAuthenticationDetails) details;
      info.setAgent(webdetails.getAgent().getBrowser().toString());
      info.setOs(webdetails.getAgent().getOs().toString());
      info.setIp(webdetails.getAgent().getIp());
      info.setServer(webdetails.getServer());
    }
    return info;
  }
}
