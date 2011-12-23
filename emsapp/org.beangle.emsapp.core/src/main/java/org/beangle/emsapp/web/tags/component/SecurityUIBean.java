/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.web.tags.component;

import java.io.Writer;

import org.beangle.emsapp.security.SecurityUtils;
import org.beangle.security.access.AuthorityManager;
import org.beangle.security.core.context.SecurityContextHolder;
import org.beangle.struts2.view.component.UIBean;
import org.beangle.web.url.UrlRender;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * 基于授权访问的bean
 * 
 * @author chaostone
 * @version $Id: SecurityUIBean.java Jul 2, 2011 9:24:56 AM chaostone $
 */
public abstract class SecurityUIBean extends UIBean {

	private AuthorityManager authorityManager;

	private static UrlRender render = new UrlRender(null);

	public SecurityUIBean(ValueStack stack, AuthorityManager authorityManager) {
		super(stack);
		this.authorityManager = authorityManager;
	}

	public boolean start(Writer writer) {
		return isAuthorize(getResource());
	}

	protected boolean isAuthorize(String res) {
		if (null == res) { return false; }
		int queryIndex = res.indexOf('?');
		if (-1 != queryIndex) res = res.substring(0, queryIndex);
		if ('/' != res.charAt(0)) res = render.render(SecurityUtils.getResource(), res);
		return authorityManager.isAuthorized(SecurityContextHolder.getContext().getAuthentication(), res);
	}

	protected abstract String getResource();
}
