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
package org.beangle.security.web;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
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
  private Map<RequestMatcher, List<Filter>> chainMap;

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    new VirtualFilterChain(chain, getFilters(request)).doFilter(request, response);
  }

  public FilterChainProxy() {
    super();
  }

  public FilterChainProxy(Map<String, List<Filter>> chainMap) {
    super();
    this.setFilterChainMap(chainMap);
  }

  /**
   * Returns the first filter chain matching the supplied URL.
   * 
   * @param res the request
   * @return an ordered array of Filters defining the filter chain
   */
  protected List<Filter> getFilters(ServletRequest res) {
    HttpServletRequest request = (HttpServletRequest) res;
    for (Map.Entry<RequestMatcher, List<Filter>> entry : chainMap.entrySet()) {
      RequestMatcher matcher = entry.getKey();
      if (matcher.matches(request)) return entry.getValue();
    }
    return Collections.emptyList();
  }

  public void setFilterChainMap(Map<String, List<Filter>> chainMap) {
    this.chainMap = new LinkedHashMap<RequestMatcher, List<Filter>>();
    for (Map.Entry<String, List<Filter>> entry : chainMap.entrySet()) {
      this.chainMap.put(new AntPathRequestMatcher(entry.getKey()), entry.getValue());
    }
  }

  public void setFilters(List<Filter> filters) {
    if (null == chainMap) chainMap = CollectUtils.newHashMap();
    chainMap.put(new AntPathRequestMatcher("/**"), filters);
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("FilterChainProxy[").append("Filter Chains: ").append(chainMap).append(']');
    return sb.toString();
  }

}
