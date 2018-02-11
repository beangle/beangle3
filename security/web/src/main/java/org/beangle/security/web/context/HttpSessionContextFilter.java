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
package org.beangle.security.web.context;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

import org.beangle.commons.web.filter.GenericHttpFilter;
import org.beangle.security.core.context.SecurityContext;
import org.beangle.security.core.context.SecurityContextBean;
import org.beangle.security.core.context.SecurityContextHolder;

/**
 * Populates the {@link SecurityContextHolder} with information obtained from
 * the <code>HttpSession</code>.
 * <p/>
 * The <code>HttpSession</code> will be queried to retrieve the <code>SecurityContext</code> that
 * should be stored against the <code>SecurityContextHolder</code> for the duration of the web
 * request. At the end of the web request, any updates made to the
 * <code>SecurityContextHolder</code> will be persisted back to the <code>HttpSession</code> by this
 * filter.
 * <p/>
 * If a valid <code>SecurityContext</code> cannot be obtained from the <code>HttpSession</code> for
 * whatever reason, a fresh <code>SecurityContext</code> will be created and used instead. The
 * created object will be of the instance defined by the {@link #setContextClass(Class)} method.
 * <p/>
 * No <code>HttpSession</code> will be created by this filter if one does not already exist. If at
 * the end of the web request the <code>HttpSession</code> does not exist, a
 * <code>HttpSession</code> will <b>only</b> be created if the current contents of the
 * <code>SecurityContextHolder</code> are not {@link java.lang.Object#equals(java.lang.Object)} to a
 * <code>new</code> instance of SecurityContextBean. This avoids needless <code>HttpSession</code>
 * creation, but automates the storage of changes made to the <code>SecurityContextHolder</code>.
 * <p/>
 * This filter MUST be executed BEFORE any authentication processing mechanisms. Authentication
 * processing mechanisms (eg BASIC, CAS processing filters etc) expect the
 * <code>SecurityContextHolder</code> to contain a valid <code>SecurityContext</code> by the time
 * they execute.
 * 
 * @author chaostone
 */

public class HttpSessionContextFilter extends GenericHttpFilter {
  static final String FILTER_APPLIED = "__beangle_security_session_context_filter_applied";

  public static final String SECURITY_CONTEXT_KEY = "BEANGLE_SECURITY_CONTEXT";

  private SecurityContextBean defaultContext = new SecurityContextBean();

  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
      ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;

    // ensure that filter is only applied once per request
    if (request.getAttribute(FILTER_APPLIED) != null) {
      chain.doFilter(request, response);
      return;
    }
    request.setAttribute(FILTER_APPLIED, Boolean.TRUE);

    HttpSession session = safeGetSession(request, false);
    boolean sessionExists = session != null;
    SecurityContext contextBefore = (session == null) ? null : (SecurityContext) session
        .getAttribute(SECURITY_CONTEXT_KEY);

    if (contextBefore == null) contextBefore = generateNewContext();
    int contextHashBefore = contextBefore.hashCode();
    // Create a wrapper that will eagerly update the session with the
    // security context if anything in the chain does a sendError() or sendRedirect(). See SEC-398
    OnRedirectUpdateSessionResponseWrapper responseWrapper = new OnRedirectUpdateSessionResponseWrapper(
        response, request, sessionExists, contextHashBefore);
    try {
      // This is the only place in this class where SecurityContextHolder.setContext() is called
      SecurityContextHolder.setContext(contextBefore);
      chain.doFilter(request, responseWrapper);
    } finally {
      SecurityContext contextAfter = SecurityContextHolder.getContext();
      SecurityContextHolder.clearContext();
      request.removeAttribute(FILTER_APPLIED);
      if (!responseWrapper.isSessionUpdateDone()) {
        storeContextInSession(contextAfter, request, sessionExists, contextHashBefore);
      }
    }
  }

  /**
   * Stores the supplied security context in the session (if available) and if
   * it has changed since it was set at the start of the request.
   */
  private void storeContextInSession(SecurityContext context, HttpServletRequest request,
      boolean sessionExists, int contextHashBefore) {
    HttpSession httpSession = safeGetSession(request, false);

    if (httpSession == null && !sessionExists) {
      if (!defaultContext.equals(context)) httpSession = safeGetSession(request, true);
    }

    if (httpSession != null && context.hashCode() != contextHashBefore) {
      httpSession.setAttribute(SECURITY_CONTEXT_KEY, context);
    }
  }

  private HttpSession safeGetSession(HttpServletRequest request, boolean allowCreate) {
    try {
      return request.getSession(allowCreate);
    } catch (IllegalStateException ignored) {
      return null;
    }
  }

  protected SecurityContext generateNewContext() throws ServletException {
    return new SecurityContextBean();
  }

  /**
   * Wrapper that is applied to every request to update the <code>HttpSession<code> with
   * the <code>SecurityContext</code> when a <code>sendError()</code> or <code>sendRedirect</code>
   * happens. See SEC-398. The class contains the
   * fields needed to call <code>storeSecurityContextInSession()</code>
   */
  private class OnRedirectUpdateSessionResponseWrapper extends HttpServletResponseWrapper {

    final HttpServletRequest request;
    final boolean sessionExists;
    final int contextHashBefore;

    // Used to ensure storeSecurityContextInSession() is only called once.
    boolean sessionUpdateDone = false;

    public OnRedirectUpdateSessionResponseWrapper(HttpServletResponse response, HttpServletRequest request,
        boolean sessionExists, int contextHashBefore) {
      super(response);
      this.request = request;
      this.sessionExists = sessionExists;
      this.contextHashBefore = contextHashBefore;
    }

    public void sendError(int sc) throws IOException {
      doSessionUpdate();
      super.sendError(sc);
    }

    public void sendError(int sc, String msg) throws IOException {
      doSessionUpdate();
      super.sendError(sc, msg);
    }

    public void sendRedirect(String location) throws IOException {
      doSessionUpdate();
      super.sendRedirect(location);
    }

    private void doSessionUpdate() {
      if (sessionUpdateDone) return;
      storeContextInSession(SecurityContextHolder.getContext(), request, sessionExists, contextHashBefore);
      sessionUpdateDone = true;
    }

    public boolean isSessionUpdateDone() {
      return sessionUpdateDone;
    }
  }

}
