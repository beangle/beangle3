/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
import org.beangle.web.filter.AntPathRequestMatcher;
import org.beangle.web.filter.RequestMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

public class FilterChainProxy extends GenericFilterBean {

	private static final Logger logger = LoggerFactory.getLogger(FilterChainProxy.class);

	/** Compiled pattern version of the filter chain map */
	private Map<RequestMatcher, List<Filter>> filterChainMap;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		FilterInvocation fi = new FilterInvocation(request, response, chain);
		List<Filter> filters = getFilters((HttpServletRequest) request);
		if (filters == null || filters.size() == 0) {
			if (logger.isDebugEnabled()) {
				logger.debug(fi.getRequestUrl()
						+ (filters == null ? " has no matching filters" : " has an empty filter list"));
			}
			chain.doFilter(request, response);
			return;
		}
		VirtualFilterChain virtualFilterChain = new VirtualFilterChain(fi.getRequestUrl(), chain, filters);
		virtualFilterChain.doFilter(fi.getRequest(), fi.getResponse());
	}

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
		if(null==filterChainMap){
			filterChainMap=CollectUtils.newHashMap();
		}
		filterChainMap.put(new AntPathRequestMatcher("/**"), filters);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("FilterChainProxy[").append("Filter Chains: ").append(filterChainMap).append(']');
		return sb.toString();
	}

	/**
	 * A <code>FilterChain</code> that records whether or not
	 * {@link FilterChain#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse)} is
	 * called.
	 * <p>
	 * This <code>FilterChain</code> is used by <code>FilterChainProxy</code> to determine if the
	 * next <code>Filter</code> should be called or not.
	 * </p>
	 */
	private static class VirtualFilterChain implements FilterChain {
		private final FilterChain originalChain;
		private final String url;
		private final List<Filter> additionalFilters;
		private int currentPosition = 0;

		private VirtualFilterChain(String url, FilterChain chain, List<Filter> additionalFilters) {
			this.url = url;
			this.originalChain = chain;
			this.additionalFilters = additionalFilters;
		}

		public void doFilter(ServletRequest request, ServletResponse response) throws IOException,
				ServletException {
			if (currentPosition == additionalFilters.size()) {
				if (logger.isDebugEnabled()) {
					logger.debug("{} reached end of filter chain; proceeding with original chain", url);
				}
				originalChain.doFilter(request, response);
			} else {
				currentPosition++;
				Filter nextFilter = (Filter) additionalFilters.get(currentPosition - 1);
				if (logger.isDebugEnabled()) {
					logger.debug(url + " at position " + currentPosition + " of " + additionalFilters.size()
							+ " in additional filter chain; firing Filter: '" + nextFilter + "'");
				}
				nextFilter.doFilter(request, response, this);
			}
		}
	}

}
