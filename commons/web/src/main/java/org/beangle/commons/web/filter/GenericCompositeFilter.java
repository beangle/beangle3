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
package org.beangle.commons.web.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
/**
 * 
 * @author chaostone
 * @since 2.4
 */
public abstract class GenericCompositeFilter extends GenericHttpFilter {
  /**
   * A <code>FilterChain</code> that records whether or not
   * {@link FilterChain#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse)} is
   * called.
   */
  protected static class VirtualFilterChain implements FilterChain {
    private final FilterChain originalChain;
    private final List<? extends Filter> additionalFilters;
    private int currentPosition = 0;

    public VirtualFilterChain(FilterChain chain, List<? extends Filter> additionalFilters) {
      this.originalChain = chain;
      this.additionalFilters = additionalFilters;
    }

    public void doFilter(ServletRequest request, ServletResponse response) throws IOException,
        ServletException {
      if (currentPosition == additionalFilters.size()) {
        originalChain.doFilter(request, response);
      } else {
        currentPosition++;
        additionalFilters.get(currentPosition - 1).doFilter(request, response, this);
      }
    }
  }

}
