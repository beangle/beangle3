/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class SimpleServletConfigInterceptor extends AbstractInterceptor implements StrutsStatics {
	private static final long serialVersionUID = -595817586173434580L;

	public String intercept(ActionInvocation invocation) throws Exception {
		final Object action = invocation.getAction();
		final ActionContext context = invocation.getInvocationContext();

		if (action instanceof ServletRequestAware) {
			HttpServletRequest request = (HttpServletRequest) context.get(HTTP_REQUEST);
			((ServletRequestAware) action).setServletRequest(request);
		}

		if (action instanceof ServletResponseAware) {
			HttpServletResponse response = (HttpServletResponse) context.get(HTTP_RESPONSE);
			((ServletResponseAware) action).setServletResponse(response);
		}

		return invocation.invoke();
	}
}
