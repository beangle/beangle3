/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.web.tags.component;

import java.security.Principal;

import org.beangle.commons.lang.StrUtils;
import org.beangle.security.access.AuthorityManager;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * 用户头像
 * 
 * @author chaostone
 * @version $Id: AvatarImage.java Jul 2, 2011 9:01:20 AM chaostone $
 */
public class AvatarImage extends SecurityUIBean {

	private String username;

	public static final String AvatarResource = "/avatar/user";

	public AvatarImage(ValueStack stack, AuthorityManager authorityManager) {
		super(stack, authorityManager);
	}

	public String getAvatarUrl() {
		return render(StrUtils.concat(AvatarResource, "?user.name=", username));
	}

	@Override
	protected String getResource() {
		return AvatarResource;
	}

	public void setUser(Object user) {
		if (null == user) {
			this.username = null;
			return;
		}
		if (user instanceof Principal) {
			this.username = ((Principal) user).getName();
		} else {
			this.username = user.toString();
		}
	}

}
