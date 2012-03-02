package org.beangle.web.filter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;

public abstract class OncePerRequestFilter extends GenericHttpFilter {

	private String filteredAttributeName;

	public boolean enterFilter(ServletRequest request) {
		if (null != request.getAttribute(filteredAttributeName)) return false;
		else {
			request.setAttribute(filteredAttributeName, Boolean.TRUE);
			return true;
		}
	}

	@Override
	protected void initFilterBean() throws ServletException {
		String name = getFilterName();
		if (name == null) {
			name = getClass().getName();
		}
		filteredAttributeName = name + ".FILTED";
	}

}
