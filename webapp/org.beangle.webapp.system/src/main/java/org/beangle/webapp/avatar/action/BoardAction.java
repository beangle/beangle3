/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.avatar.action;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.archiver.ZipUtils;
import org.beangle.commons.collection.page.Page;
import org.beangle.security.blueprint.User;
import org.beangle.struts2.convention.route.Action;
import org.beangle.webapp.security.action.SecurityActionSupport;
import org.beangle.webapp.service.avatar.Avatar;
import org.beangle.webapp.service.avatar.AvatarBase;

/**
 * 用户头像管理
 * 
 * @author chaostone
 */
public class BoardAction extends SecurityActionSupport {

	protected AvatarBase avatarBase;

	public String index() {
		Page<?> names = avatarBase.getAvatarNames(getPageLimit());
		put("names", names);
		put("avatarBase", avatarBase);
		return forward();
	}

	/**
	 * 查看照片信息
	 */
	public String info() {
		String userName = get("user.name");
		if (StringUtils.isEmpty(userName)) { return null; }
		List<User> users = entityDao.get(User.class, "name", userName);
		User user = null;
		if (users.isEmpty()) {
			return null;
		} else {
			user = users.get(0);
		}
		Avatar avatar = avatarBase.getAvatar(user.getName());
		put("avatar", avatar);
		put("user", user);
		return forward();
	}

	public String upload() throws Exception {
		File[] files = (File[]) getAll("avatar");
		String userName = get("user.name");
		if (files.length > 0) {
			String type = StringUtils.substringAfter(get("avatarFileName"), ".");
			boolean passed = avatarBase.containType(type);
			if (passed) {
				avatarBase.updateAvatar(userName, files[0], type);
			} else {
				return forward("upload");
			}
		}
		return redirect(new Action(UserAction.class, "info", "&user.name=" + userName), "info.save.success");
	}

	public String uploadBatch() throws Exception {
		File file = get("avatar", File.class);
		String msg = "info.save.success";
		if (null == file) {
			msg = "error.nofile";
		} else {
			if (ZipUtils.isZipFile(file)) {
				avatarBase.updateAvatarBatch(file);
			} else {
				msg = "error.wrongzipfile";
			}
		}
		return redirect("index", msg);
	}

	public void setAvatarBase(AvatarBase avatarBase) {
		this.avatarBase = avatarBase;
	}

}
