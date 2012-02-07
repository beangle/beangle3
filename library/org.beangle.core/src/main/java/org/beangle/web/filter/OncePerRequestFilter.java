package org.beangle.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class OncePerRequestFilter extends GenericHttpFilter {

	private String filteredAttributeName;

	@Override
	protected void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (request.getAttribute(filteredAttributeName) != null) {
			chain.doFilter(request, response);
		} else {
			request.setAttribute(filteredAttributeName, Boolean.TRUE);
			try {
				doFilterInternal(request, response, chain);
			} finally {
				request.removeAttribute(filteredAttributeName);
			}
		}
	}

	protected abstract void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException;

	@Override
	protected void initFilterBean() throws ServletException {
		String name = getFilterName();
		if (name == null) {
			name = getClass().getName();
		}
		filteredAttributeName = name + ".FILTED";
	}

}
