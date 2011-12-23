/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.avatar.action;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.beangle.emsapp.avatar.Avatar;
import org.beangle.emsapp.avatar.service.AvatarBase;
import org.beangle.emsapp.web.action.SecurityActionSupport;
import org.beangle.struts2.convention.route.Action;

/**
 * 上传照片
 * 
 * @author chaostone
 */
public class MyUploadAction extends SecurityActionSupport {

	protected AvatarBase avatarBase;

	public String index() {
		Avatar avatar = avatarBase.getAvatar(getUsername());
		put("avatar", avatar);
		put("user", getUser());
		return forward();
	}

	public String upload() throws Exception {
		File[] files = (File[]) getAll("avatar");
		if (files.length > 0) {
			String type = StringUtils.substringAfter(get("avatarFileName"), ".");
			boolean passed = avatarBase.containType(type);
			if (passed) {
				avatarBase.updateAvatar(getUsername(), files[0], type);
			} else {
				return forward("upload");
			}
		}
		return redirect(new Action(MyAction.class, "info"), "info.upload.success");
	}

	public void setAvatarBase(AvatarBase avatarBase) {
		this.avatarBase = avatarBase;
	}

}
