/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
 * and process it. The CAS server knows which <code>service</code> URL to use via the
 * {@link CasConfig#getService()} method.
 * </p>
 * <p>
 * Processing the service ticket involves creating a <code>PreauthAuthentication</code> which uses
 * {@link #STATEFUL_ID} for the <code>principal</code> and the opaque ticket string as the
 * <code>credentials</code>.
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
