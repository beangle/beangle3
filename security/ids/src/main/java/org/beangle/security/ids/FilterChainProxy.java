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
import java.time.Instant;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.beangle.commons.web.filter.GenericCompositeFilter;
import org.beangle.security.access.AccessDeniedException;
import org.beangle.security.core.AuthenticationException;
import org.beangle.security.core.context.SecurityContext;
import org.beangle.security.core.session.Session;
import org.beangle.security.core.session.SessionRepo;
import org.beangle.security.ids.access.AccessDeniedHandler;

public class FilterChainProxy extends GenericCompositeFilter {

  /** Compiled pattern version of the filter chain map */
  private List<Filter> filters;
  private SessionRepo sessionRepo;
  private AccessDeniedHandler accessDeniedHandler;
  private EntryPoint entryPoint;

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    String sessionId = "";
    if (null != sessionId) {
      Session session = sessionRepo.get(sessionId);
      if (null != session) {
        sessionRepo.access(sessionId, Instant.now());
      }
      SecurityContext.setSession(session);
    }
    try {
      if (!filters.isEmpty()) {
        new VirtualFilterChain(chain, filters).doFilter(request, response);
      }
    } catch (SecurityException bae) {
      try {
        handleException(request, response, bae);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

  }

  private void handleException(ServletRequest request, ServletResponse response, Exception exception)
      throws Exception {
    if (exception instanceof AuthenticationException) {
      sendStartAuthentication(request, response, (AuthenticationException) exception);
    } else if (exception instanceof AccessDeniedException) {
      accessDeniedHandler.handle(request, response, (AccessDeniedException) exception);
    } else {
      sendStartAuthentication(request, response, new AuthenticationException("access denied", exception));
    }
  }

  private void sendStartAuthentication(ServletRequest request, ServletResponse response,
      AuthenticationException reason) throws Exception {
    SecurityContext.clear();
    entryPoint.commence(request, response, reason);
  }

  public FilterChainProxy() {
    super();
  }

  public FilterChainProxy(List<Filter> filters) {
    super();
    this.setFilters(filters);
  }

  public void setFilters(List<Filter> filters) {
    this.filters = filters;
  }

  public void setSessionRepo(SessionRepo sessionRepo) {
    this.sessionRepo = sessionRepo;
  }

  public void setAccessDeniedHandler(AccessDeniedHandler accessDeniedHandler) {
    this.accessDeniedHandler = accessDeniedHandler;
  }

  public void setEntryPoint(EntryPoint entryPoint) {
    this.entryPoint = entryPoint;
  }

}
