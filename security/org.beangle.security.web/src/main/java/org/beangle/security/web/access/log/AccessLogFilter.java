package org.beangle.security.web.access.log;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.beangle.web.filter.GenericHttpFilterBean;

public class AccessLogFilter extends GenericHttpFilterBean {

	private ResourceAccessor accessor;

	@Override
	protected void initFilterBean() throws ServletException {
		Validate.notNull(accessor, "accessor must be set");
	}

	@Override
	protected void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Accesslog log = accessor.beginAccess(request, System.currentTimeMillis());
		try {
			chain.doFilter(request, response);
		} finally {
			accessor.endAccess(log, System.currentTimeMillis());
		}
	}

	public void setAccessor(ResourceAccessor accessor) {
		this.accessor = accessor;
	}

}
