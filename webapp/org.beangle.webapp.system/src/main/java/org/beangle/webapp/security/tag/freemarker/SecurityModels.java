/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.security.tag.freemarker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.util.ValueStack;

public class SecurityModels {
	private ValueStack stack;
	private HttpServletRequest req;
	private HttpServletResponse res;
	private AuthorizeModel authorize;

	public SecurityModels(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		this.stack = stack;
		this.req = req;
		this.res = res;
	}

	public AuthorizeModel getAuthorize() {
		if (authorize == null) {
			authorize = new AuthorizeModel(stack, req, res);
		}
		return authorize;
	}

}
