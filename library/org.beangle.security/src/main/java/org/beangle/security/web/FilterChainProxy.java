/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web;

import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;

import org.beangle.collection.CollectUtils;
import org.beangle.web.filter.AntPathRequestMatcher;
import org.beangle.web.filter.GenericCompositeFilter;
import org.beangle.web.filter.RequestMatcher;

public class FilterChainProxy extends GenericCompositeFilter {

	/** Compiled pattern version of the filter chain map */
	private Map<RequestMatcher, List<Filter>> filterChainMap;

	/**
	 * Returns the first filter chain matching the supplied URL.
	 * 
	 * @param url
	 *            the request URL
	 * @return an ordered array of Filters defining the filter chain
	 */
	public List<Filter> getFilters(HttpServletRequest request) {
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
		if (null == filterChainMap) {
			filterChainMap = CollectUtils.newHashMap();
		}
		filterChainMap.put(new AntPathRequestMatcher("/**"), filters);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("FilterChainProxy[").append("Filter Chains: ").append(filterChainMap).append(']');
		return sb.toString();
	}


}
