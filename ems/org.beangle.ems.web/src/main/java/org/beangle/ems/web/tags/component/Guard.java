/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.web.tags.component;

import java.io.Writer;

import org.beangle.ems.security.SecurityUtils;
import org.beangle.security.access.AuthorityManager;
import org.beangle.security.core.context.SecurityContextHolder;
import org.beangle.struts2.view.component.Component;
import org.beangle.web.url.UrlRender;

import com.opensymphony.xwork2.util.ValueStack;

public class Guard extends Component {

	String res;

	AuthorityManager authorityManager;

	private static UrlRender render = new UrlRender(null);

	public Guard(ValueStack stack, AuthorityManager authorityManager) {
		super(stack);
		this.authorityManager = authorityManager;
	}

	/**
	 * supprt ifAllGranted,ifAnyGranted,ifNotGranted
	 */
	public boolean start(Writer writer) {
		if (null == res) { return false; }
		if ('/' != res.charAt(0)) res = render.render(SecurityUtils.getResource(), res);
		return authorityManager.isAuthorized(SecurityContextHolder.getContext().getAuthentication(), res);
	}

	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}

}
