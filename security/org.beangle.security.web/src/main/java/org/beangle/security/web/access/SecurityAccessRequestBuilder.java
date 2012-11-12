/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.access;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.beangle.commons.web.access.DefaultAccessRequestBuilder;
import org.beangle.security.core.context.SecurityContext;
import org.beangle.security.web.context.HttpSessionContextFilter;

/**
 * Security access request
 * 
 * @author chaostone
 * @since 3.0.1
 */
public class SecurityAccessRequestBuilder extends DefaultAccessRequestBuilder {

  @Override
  protected String abtainUsername(HttpServletRequest request) {
    HttpSession session = request.getSession();
    SecurityContext context = (SecurityContext) session
        .getAttribute(HttpSessionContextFilter.SECURITY_CONTEXT_KEY);
    if (null == context) return null;
    else return context.getAuthentication().getName();
  }

}
