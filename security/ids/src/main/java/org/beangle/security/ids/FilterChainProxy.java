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

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.beangle.commons.lang.Option;
import org.beangle.commons.web.filter.GenericCompositeFilter;
import org.beangle.commons.web.security.RequestConvertor;
import org.beangle.commons.web.util.CookieUtils;
import org.beangle.security.access.AuthorityManager;
import org.beangle.security.core.AuthenticationException;
import org.beangle.security.core.context.SecurityContext;
import org.beangle.security.core.session.Session;
import org.beangle.security.core.session.SessionRepo;
import org.beangle.security.ids.access.AccessDeniedHandler;
import org.beangle.security.ids.session.SessionIdReader;

public class FilterChainProxy extends GenericCompositeFilter {

  private SessionRepo sessionRepo;
  private AccessDeniedHandler accessDeniedHandler;
  private EntryPoint entryPoint;
  private RequestConvertor requestConvertor;
  private SessionIdReader sessionIdReader;
  private AuthorityManager authorityManager;

  public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    Option<String> os = sessionIdReader.getId((HttpServletRequest) request);
    Session session = null;
    if (os.isDefined()) {
      String sessionId = os.get();
      session = sessionRepo.get(sessionId);
      if (null != session) {
        sessionRepo.access(sessionId, Instant.now());
      }
    }

    boolean isRoot = false;
    if (null != session) {
      isRoot = authorityManager.isRoot(session.getPrincipal().getName());
    }
    String runAs = null;
    if (isRoot) {
      runAs = CookieUtils.getCookieValue(request, "beangle.security.runAs");
    }
    SecurityContext context = new SecurityContext(session, requestConvertor.convert(request), isRoot, runAs);
    SecurityContext.set(context);

    if (authorityManager.isAuthorized(context)) {
      chain.doFilter(request, response);
    } else {
      SecurityContext.clear();
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

  public FilterChainProxy() {
    super();
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

  public void setRequestConvertor(RequestConvertor requestConvertor) {
    this.requestConvertor = requestConvertor;
  }

  public void setSessionIdReader(SessionIdReader sessionIdReader) {
    this.sessionIdReader = sessionIdReader;
  }

  public void setAuthorityManager(AuthorityManager authorityManager) {
    this.authorityManager = authorityManager;
  }


}
