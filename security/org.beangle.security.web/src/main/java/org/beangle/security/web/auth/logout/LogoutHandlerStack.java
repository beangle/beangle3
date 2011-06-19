/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.auth.logout;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.security.core.Authentication;

/**
 * @author chaostone
 * @version $Id: LogoutHandlerStack.java Nov 22, 2010 7:34:38 PM chaostone $
 */
public class LogoutHandlerStack {

	private List<LogoutHandler> handlers = CollectUtils.newArrayList();

	public LogoutHandlerStack() {
		super();
	}

	public LogoutHandlerStack(LogoutHandler... initHandlers) {
		for (LogoutHandler handler : initHandlers) {
			handlers.add(handler);
		}
	}

	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication auth) {
		for (LogoutHandler handler : handlers) {
			handler.logout(request, response, auth);
		}
	}

	public List<LogoutHandler> getHandlers() {
		return handlers;
	}

	public void setHandlers(List<LogoutHandler> handlers) {
		this.handlers = handlers;
	}

}
