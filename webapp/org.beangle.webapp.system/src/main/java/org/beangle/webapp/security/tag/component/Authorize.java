/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.security.tag.component;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.components.UIBean;
import org.beangle.security.AuthorityManager;
import org.beangle.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.util.ValueStack;

public class Authorize extends UIBean {

	public Authorize(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		super(stack, req, res);
	}

	@Override
	protected String getDefaultTemplate() {
		return "authorize";
	}

	/**
	 * supprt ifAllGranted,ifAnyGranted,ifNotGranted
	 */
	public boolean start(Writer writer) {
		String resource = (String) parameters.get("resource");
		if (StringUtils.isEmpty(resource)) {
			return true;
		} else {
			AuthorityManager authorityManager = (AuthorityManager) stack
					.findValue("authorityManager");
			return authorityManager.isAuthorized(SecurityContextHolder.getContext()
					.getAuthentication(), resource);
		}
	}
}
