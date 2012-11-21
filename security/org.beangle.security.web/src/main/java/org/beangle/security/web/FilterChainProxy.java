/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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
package org.beangle.security.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.web.filter.AntPathRequestMatcher;
import org.beangle.commons.web.filter.GenericCompositeFilter;
import org.beangle.commons.web.filter.RequestMatcher;

public class FilterChainProxy extends GenericCompositeFilter {

  /** Compiled pattern version of the filter chain map */
  private Map<RequestMatcher, List<Filter>> filterChainMap;

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    new VirtualFilterChain(chain, getFilters(request)).doFilter(request, response);
  }

  /**
   * Returns the first filter chain matching the supplied URL.
   * 
   * @param res the request
   * @return an ordered array of Filters defining the filter chain
   */
  public List<Filter> getFilters(ServletRequest res) {
    HttpServletRequest request = (HttpServletRequest) res;
    // FIXME
    for (Map.Entry<RequestMatcher, List<Filter>> entry : filterChainMap.entrySet()) {
      RequestMatcher matcher = entry.getKey();
      boolean matched = matcher.matches(request);
      if (matched) { return entry.getValue(); }
    }
    return null;
  }

  public void setFilterChainMap(Map<RequestMatcher, List<Filter>> filterChainMap) {
    this.filterChainMap = CollectUtils.newLinkedHashMap(filterChainMap);
  }

  /**
   * Returns a copy of the underlying filter chain map. Modifications to the
   * map contents will not affect the FilterChainProxy state - to change the
   * map call <tt>setFilterChainMap</tt>.
   * 
   * @return the map of path pattern Strings to filter chain arrays (with
   *         ordering guaranteed).
   */
  public Map<RequestMatcher, List<Filter>> getFilterChainMap() {
    return CollectUtils.newLinkedHashMap(filterChainMap);
  }

  public void setFilters(List<Filter> filters) {
    if (null == filterChainMap) filterChainMap = CollectUtils.newHashMap();
    filterChainMap.put(new AntPathRequestMatcher("/**"), filters);
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("FilterChainProxy[").append("Filter Chains: ").append(filterChainMap).append(']');
    return sb.toString();
  }

}
