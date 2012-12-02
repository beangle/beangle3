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
package org.beangle.security.cas.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.commons.lang.Assert;
import org.beangle.security.cas.CasConfig;
import org.beangle.security.cas.auth.CasAuthentication;
import org.beangle.security.web.auth.preauth.AbstractPreauthFilter;
import org.beangle.security.web.auth.preauth.PreauthAuthentication;

/**
 * Processes a CAS service ticket.
 * <p>
 * A service ticket consists of an opaque ticket string. It arrives at this filter by the user's
 * browser successfully authenticating using CAS, and then receiving a HTTP redirect to a
 * <code>service</code>. The opaque ticket string is presented in the <code>ticket</code> request
 * parameter. This filter monitors the <code>service</code> URL so it can receive the service ticket
 * and process it.
 * </p>
 * <p>
 * Processing the service ticket involves creating a <code>PreauthAuthentication</code> which uses
 * {@link CasAuthentication#STATEFUL_ID} for the <code>principal</code> and the opaque ticket string
 * as the <code>credentials</code>.
 * </p>
 * <p>
 * The configured <code>AuthenticationManager</code> is expected to provide a provider that can
 * recognise <code>PreauthAuthentication</code>s containing this special <code>principal</code>
 * name, and process them accordingly by validation with the CAS server.
 * </p>
 * <p>
 * 
 * @author chaostone
 * @version $Id: CasPreauthFilter.java$
 */
public class CasPreauthFilter extends AbstractPreauthFilter {

  private CasConfig config;

  protected void initFilterBean() {
    super.initFilterBean();
    Assert.notNull(config, "config is a required field.");
  }

  @Override
  protected PreauthAuthentication getPreauthAuthentication(HttpServletRequest request,
      HttpServletResponse response) {
    final String username = CasAuthentication.STATEFUL_ID;
    String password = request.getParameter("ticket");
    if (password == null) {
      return null;
    } else {
      String url = CasEntryPoint.constructServiceUrl(request, response, null,
          CasConfig.getLocalServer(request), "ticket", config.isEncode());
      return new CasAuthentication(username, password, url);
    }
  }

  public void setConfig(CasConfig config) {
    this.config = config;
  }
}
