/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.web.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.SessionMap;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.context.AuthenticationUtils;
import org.beangle.security.core.context.SecurityContextHolder;
import org.beangle.security.web.auth.logout.LogoutHandlerStack;
import org.beangle.struts2.action.BaseAction;

import com.opensymphony.xwork2.ActionContext;

public class LogoutAction extends BaseAction {

	private LogoutHandlerStack handlerStack;

	public String index() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String result = "success";
		if (AuthenticationUtils.isValid(auth)) {
			if (null != handlerStack) handlerStack.logout(getRequest(), getResponse(), auth);
			((SessionMap<?, ?>) ActionContext.getContext().getSession()).invalidate();
			String targetUrl = determineTargetUrl(getRequest());
			if (null != targetUrl) {
				result = "redirect:" + targetUrl;
			}
		}
		return result;
	}

	protected String determineTargetUrl(HttpServletRequest request) {
		return get("logoutSuccessUrl");
	}

	public void setHandlerStack(LogoutHandlerStack handlerStack) {
		this.handlerStack = handlerStack;
	}

}
