/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.web.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class GenericCompositeFilter extends GenericHttpFilter {

	protected abstract List<? extends Filter> getFilters(HttpServletRequest request);

	@Override
	protected void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		new VirtualFilterChain(chain, getFilters(request)).doFilter(request, response);
	}
	/**
	 * A <code>FilterChain</code> that records whether or not
	 * {@link FilterChain#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse)} is
	 * called.
	 */
	private static class VirtualFilterChain implements FilterChain {
		private final FilterChain originalChain;
		private final List<? extends Filter> additionalFilters;
		private int currentPosition = 0;

		private VirtualFilterChain(FilterChain chain, List<? extends Filter> additionalFilters) {
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
