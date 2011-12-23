/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * A simple filter that the test case can delegate to.
 * 
 * @author chaostone
 * @version $Id: MockFilter.java 2217 2007-10-27 00:45:30Z $
 */
public class MockFilter implements Filter {

	private boolean destroyed = false;
	private boolean doFiltered = false;
	private boolean initialized = false;

	public void destroy() {
		destroyed = true;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		doFiltered = true;
		chain.doFilter(request, response);
	}

	public void init(FilterConfig config) throws ServletException {
		initialized = true;
	}

	public boolean isDestroyed() {
		return destroyed;
	}

	public boolean isDoFiltered() {
		return doFiltered;
	}

	public boolean isInitialized() {
		return initialized;
	}
}
