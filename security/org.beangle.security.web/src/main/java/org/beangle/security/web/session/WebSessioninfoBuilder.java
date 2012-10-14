/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.session;

import org.beangle.security.core.Authentication;
import org.beangle.security.core.session.Sessioninfo;
import org.beangle.security.core.session.SessioninfoBuilder;
import org.beangle.security.core.session.category.CategoryPrincipal;
import org.beangle.security.web.auth.WebAuthenticationDetails;
import org.beangle.security.web.session.model.SessioninfoBean;
import org.beangle.security.web.session.model.SessioninfoLogBean;

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
    }
    return info;
  }

  public Object buildLog(Sessioninfo info) {
    return new SessioninfoLogBean((SessioninfoBean) info);
  }

}
