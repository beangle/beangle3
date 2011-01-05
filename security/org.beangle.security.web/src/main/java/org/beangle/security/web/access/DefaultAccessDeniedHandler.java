/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */

package org.beangle.security.web.access;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.beangle.security.access.AccessDeniedException;

/**
 * Base implementation of {@link AccessDeniedHandler}.
 * <p>
 * This implementation sends a 403 (SC_FORBIDDEN) HTTP error code. In addition,
 * if a {@link #errorPage} is defined, the implementation will perform a request
 * dispatcher "forward" to the specified error page view. Being a "forward", the
 * <code>SecurityContextHolder</code> will remain populated. This is of benefit
 * if the view (or a tag library or macro) wishes to access the
 * <code>SecurityContextHolder</code>. The request scope will also be populated
 * with the exception itself, available from the key
 * {@link #ACCESS_DENIED_EXCEPTION_KEY}.
 * </p>
 * 
 * @author chaostone
 */
public class DefaultAccessDeniedHandler implements AccessDeniedHandler {

	public static final String ACCESS_DENIED_EXCEPTION_KEY = "403_EXCEPTION";
	protected static final Log logger = LogFactory.getLog(DefaultAccessDeniedHandler.class);

	private String errorPage;

	private String location;

	public void handle(ServletRequest request, ServletResponse response,
			AccessDeniedException exception) throws IOException, ServletException {
		if (null != location) {

		}
		if (errorPage != null) {
			// Put exception into request scope (perhaps of use to a view)
			((HttpServletRequest) request).setAttribute(ACCESS_DENIED_EXCEPTION_KEY, exception);
			// Perform RequestDispatcher "forward"
			RequestDispatcher rd = request.getRequestDispatcher(errorPage);
			rd.forward(request, response);
		}

		if (!response.isCommitted()) {
			// Send 403 (we do this after response has been written)
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN,
					exception.getMessage());
		}
	}

	/**
	 * The error page to use. Must begin with a "/" and is interpreted relative
	 * to the current context root.
	 * 
	 * @param errorPage
	 *            the dispatcher path to display
	 * @throws IllegalArgumentException
	 *             if the argument doesn't comply with the above limitations
	 */
	public void setErrorPage(String errorPage) {
		if ((errorPage != null) && !errorPage.startsWith("/")) { throw new IllegalArgumentException(
				"errorPage must begin with '/'"); }
		this.errorPage = errorPage;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
