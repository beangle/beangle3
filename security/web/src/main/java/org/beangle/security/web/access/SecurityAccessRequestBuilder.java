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
    if (null == context || null == context.getAuthentication()) return null;
    else return context.getAuthentication().getName();
  }

}
