/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.portal.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.context.AuthenticationUtils;
import org.beangle.security.core.context.SecurityContextHolder;
import org.beangle.security.web.auth.logout.LogoutHandlerStack;
import org.beangle.struts2.action.BaseAction;

import com.opensymphony.xwork2.ActionContext;

public class LogoutAction extends BaseAction implements ServletRequestAware, ServletResponseAware {

	private LogoutHandlerStack handlerStack;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public String index() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String result = "success";
		if (AuthenticationUtils.isValid(auth)) {
			handlerStack.logout(request, response, auth);
			((SessionMap<?, ?>) ActionContext.getContext().getSession()).invalidate();
			String targetUrl = determineTargetUrl(request);
			if (null != targetUrl) {
				result = "redirect:" + targetUrl;
			}
		}
		return result;
	}

	protected String determineTargetUrl(HttpServletRequest request) {
		return request.getParameter("logoutSuccessUrl");
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setHandlerStack(LogoutHandlerStack handlerStack) {
		this.handlerStack = handlerStack;
	}

	
}
