/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.web.tags.component;

import java.io.Writer;

import org.beangle.ems.security.User;
import org.beangle.lang.StrUtils;
import org.beangle.security.access.AuthorityManager;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author chaostone
 * @version $Id: Userinfo.java Jul 2, 2011 5:46:44 PM chaostone $
 */
public class Userinfo extends SecurityUIBean {

	private User user;

	private String href;

	public Userinfo(ValueStack stack, AuthorityManager authorityManager) {
		super(stack, authorityManager);
	}

	public boolean start(Writer writer) {
		return true;
	}

	@Override
	protected String getResource() {
		if (null == href) {
			this.href = StrUtils.concat("/security/user!dashboard?user.id=", user.getId().toString());
		}
		return this.href;
	}

	public boolean isDashboardAuthorized() {
		return isAuthorize(getResource());
	}

	public String getDashboardUrl() {
		return render(getResource());
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setHref(String href) {
		this.href = href;
	}

}
