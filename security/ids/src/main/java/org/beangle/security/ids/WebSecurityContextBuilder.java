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

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.commons.lang.Option;
import org.beangle.commons.web.security.RequestConvertor;
import org.beangle.commons.web.util.CookieUtils;
import org.beangle.security.authz.Authorizer;
import org.beangle.security.core.context.SecurityContext;
import org.beangle.security.core.session.Session;
import org.beangle.security.core.session.SessionRepo;
import org.beangle.security.ids.session.SessionIdReader;

public class WebSecurityContextBuilder implements SecurityContextBuilder {

  private SessionIdReader sessionIdReader;

  private Authorizer authorizer;

  private SessionRepo sessionRepo;

  private RequestConvertor requestConvertor;

  @Override
  public SecurityContext build(HttpServletRequest request, HttpServletResponse response) {
    Option<String> os = sessionIdReader.getId(request, response);
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
      isRoot = authorizer.isRoot(session.getPrincipal().getName());
    }
    String runAs = null;
    if (isRoot) {
      runAs = CookieUtils.getCookieValue(request, "beangle.security.runAs");
    }
    return new SecurityContext(session, requestConvertor.convert(request), isRoot, runAs);
  }

  public void setSessionIdReader(SessionIdReader sessionIdReader) {
    this.sessionIdReader = sessionIdReader;
  }

  public void setAuthorizer(Authorizer authorizer) {
    this.authorizer = authorizer;
  }

  public void setSessionRepo(SessionRepo sessionRepo) {
    this.sessionRepo = sessionRepo;
  }

  public void setRequestConvertor(RequestConvertor requestConvertor) {
    this.requestConvertor = requestConvertor;
  }

}
