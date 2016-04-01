/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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
package org.beangle.security.web.access.intercept;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.beangle.security.access.intercept.AbstractSecurityInterceptor;
import org.beangle.security.access.intercept.InterceptorStatusToken;
import org.beangle.security.web.FilterInvocation;

/**
 * Performs security handling of HTTP resources via a filter implementation.
 * <p>
 * Refer to {@link AbstractSecurityInterceptor} for details on the workflow.
 * </p>
 */
public class FilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

  private static final String FILTER_APPLIED = "__beangle_security_filterSecurityInterceptor_applied";

  private boolean observeOncePerRequest = true;

  /**
   * Not used (we rely on IoC container lifecycle services instead)
   * 
   * @param arg0 ignored
   * @throws ServletException never thrown
   */
  public void init(FilterConfig arg0) throws ServletException {
  }

  /**
   * Not used (we rely on IoC container lifecycle services instead)
   */
  public void destroy() {
  }

  /**
   * Method that is actually called by the filter chain. Simply delegates to
   * the {@link #invoke(FilterInvocation)} method.
   * 
   * @param request the servlet request
   * @param response the servlet response
   * @param chain the filter chain
   * @throws IOException if the filter chain fails
   * @throws ServletException if the filter chain fails
   */
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    FilterInvocation fi = new FilterInvocation(request, response, chain);
    invoke(fi);
  }

  public Class<?> getSecureObjectClass() {
    return FilterInvocation.class;
  }

  public void invoke(FilterInvocation fi) throws IOException, ServletException {
    if ((fi.getRequest() != null) && (fi.getRequest().getAttribute(FILTER_APPLIED) != null)
        && observeOncePerRequest) {
      // filter already applied to this request and user wants us to
      // observce
      // once-per-request handling, so don't re-do security checking
      fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
    } else {
      // first time this request being called, so perform security checking
      if (fi.getRequest() != null) fi.getRequest().setAttribute(FILTER_APPLIED, Boolean.TRUE);

      InterceptorStatusToken token = super.beforeInvocation(fi);

      try {
        fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
      } finally {
        super.afterInvocation(token, null);
      }
    }
  }

  /**
   * Indicates whether once-per-request handling will be observed. By default
   * this is <code>true</code>, meaning the <code>FilterSecurityInterceptor</code> will only
   * execute
   * once-per-request. Sometimes users may wish it to execute more than once
   * per request, such as when JSP forwards are being used and filter security
   * is desired on each included fragment of the HTTP request.
   * 
   * @return <code>true</code> (the default) if once-per-request is honoured,
   *         otherwise <code>false</code> if <code>FilterSecurityInterceptor</code> will enforce
   *         authorizations for each and every fragment of the HTTP request.
   */
  public boolean isObserveOncePerRequest() {
    return observeOncePerRequest;
  }

  public void setObserveOncePerRequest(boolean observeOncePerRequest) {
    this.observeOncePerRequest = observeOncePerRequest;
  }
}
