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
package org.beangle.security.ids;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.commons.web.filter.GenericCompositeFilter;
import org.beangle.security.authz.Authorizer;
import org.beangle.security.core.AuthenticationException;
import org.beangle.security.core.context.SecurityContext;
import org.beangle.security.ids.access.AccessDeniedHandler;

public class SecurityFilterChain extends GenericCompositeFilter {

  private AccessDeniedHandler accessDeniedHandler;
  private EntryPoint entryPoint;
  private SecurityContextBuilder securityContextBuilder;
  private Authorizer authorizer;

  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;
    SecurityContext context = securityContextBuilder.build(request, response);
    SecurityContext.set(context);

    if (authorizer.isPermitted(context)) {
      chain.doFilter(request, response);
    } else {
      if (context.getSession() == null) {
        sendStartAuthentication(request, response, null);
      } else {
        accessDeniedHandler.handle(request, response, null);
      }
    }
  }

  private void sendStartAuthentication(ServletRequest request, ServletResponse response,
      AuthenticationException reason) throws IOException, ServletException {
    entryPoint.commence(request, response, reason);
  }

  public SecurityFilterChain() {
    super();
  }

  public void setAccessDeniedHandler(AccessDeniedHandler accessDeniedHandler) {
    this.accessDeniedHandler = accessDeniedHandler;
  }

  public void setEntryPoint(EntryPoint entryPoint) {
    this.entryPoint = entryPoint;
  }

  public void setAuthorizer(Authorizer authorizer) {
    this.authorizer = authorizer;
  }

  public void setSecurityContextBuilder(SecurityContextBuilder securityContextBuilder) {
    this.securityContextBuilder = securityContextBuilder;
  }

}
