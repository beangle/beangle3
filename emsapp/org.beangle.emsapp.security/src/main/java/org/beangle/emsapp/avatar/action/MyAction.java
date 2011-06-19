/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.avatar.action;

import java.io.IOException;

import org.apache.struts2.ServletActionContext;
import org.beangle.ems.avatar.Avatar;
import org.beangle.ems.avatar.service.AvatarBase;
import org.beangle.ems.web.action.SecurityActionSupport;

/**
 * 查看下载自己的照片
 * 
 * @author chaostone
 */
public class MyAction extends SecurityActionSupport {

	protected AvatarBase avatarBase;

	public String index() throws IOException {
		String userName = getUsername();
		Avatar avatar = avatarBase.getAvatar(userName);
		if (null == avatar) {
			ServletActionContext.getResponse().getWriter().write("without you avatar [" + userName + "]");
		} else {
			AvatarUtil.copyTo(avatar, ServletActionContext.getResponse());
		}
		return null;
	}

	public String info() {
		Avatar avatar = avatarBase.getAvatar(getUsername());
		put("avatar", avatar);
		put("user", getUser());
		return forward();
	}

	public void setAvatarBase(AvatarBase avatarBase) {
		this.avatarBase = avatarBase;
	}

}
