/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.ids.access;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.commons.lang.Assert;
import org.beangle.commons.web.filter.GenericHttpFilter;
import org.beangle.commons.web.security.RequestConvertor;
import org.beangle.security.access.AccessDeniedException;
import org.beangle.security.access.AuthorityManager;
import org.beangle.security.core.context.SecurityContext;

/**
 * Filter required by concurrent session handling package.
 *
 * @author chaostone
 */
public class AuthorizationFilter extends GenericHttpFilter {

  private AuthorityManager authorityManager;

  private RequestConvertor requestConvertor;

  @Override
  protected void initFilterBean() {
    Assert.notNull(authorityManager, "authorityManager required");
  }

  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;

    if (!authorityManager.isAuthorized(SecurityContext.getSession(), requestConvertor.convert(request))) {
      throw new AccessDeniedException(requestConvertor.convert(request).getResource(), "access denied");
    } else {
      chain.doFilter(request, response);
    }
  }

  public AuthorityManager getAuthorityManager() {
    return authorityManager;
  }

  public void setAuthorityManager(AuthorityManager authorityManager) {
    this.authorityManager = authorityManager;
  }

  public RequestConvertor getRequestConvertor() {
    return requestConvertor;
  }

  public void setRequestConvertor(RequestConvertor requestConvertor) {
    this.requestConvertor = requestConvertor;
  }

}
