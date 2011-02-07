/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.system.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.struts2.action.BaseAction;

public class IndexAction extends BaseAction {

	public String index() {
		Map<String, Object> clientProps = CollectUtils.newHashMap();
		clientProps.put("client.ip", getRemoteAddr());
		HttpServletRequest request = getRequest();
		clientProps.put("client.useragent", request.getHeader("USER-AGENT"));
		clientProps.put("client.scheme", request.getScheme());
		clientProps.put("client.secure", String.valueOf(request.isSecure()));
		put("clientProps", clientProps);
		return forward();
	}

}
