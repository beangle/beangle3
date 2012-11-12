/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.web.access;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.commons.web.filter.GenericHttpFilter;

/**
 * Access monitor filter
 * @author chaostone
 * @since 3.0.1
 */
public class AccessMonitorFilter extends GenericHttpFilter {

  AccessMonitor accessMonitor;

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    AccessRequest ar = null;
    try {
      ar = accessMonitor.begin((HttpServletRequest) request);
      chain.doFilter(request, response);
    } finally {
      accessMonitor.end(ar, (HttpServletResponse) response);
    }
  }

  public void setAccessMonitor(AccessMonitor accessMonitor) {
    this.accessMonitor = accessMonitor;
  }

}
