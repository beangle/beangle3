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
 * 
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
