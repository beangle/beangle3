/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.service;

import java.util.List;

import org.beangle.collection.CollectUtils;
import org.beangle.emsapp.security.Group;
import org.beangle.emsapp.security.User;
import org.beangle.emsapp.security.session.service.GroupProfileService;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.authority.GrantedAuthorityBean;
import org.beangle.security.core.userdetail.UserDetail;
import org.beangle.security.core.userdetail.UserDetailService;

public class DaoUserDetailServiceImpl extends BaseServiceImpl implements UserDetailService<Authentication> {

	protected UserService userService;

	protected GroupProfileService groupProfileService;

	public UserDetail loadDetail(Authentication auth) {
		List<User> users = entityDao.get(User.class, "name", auth.getName());
		if (users.isEmpty()) {
			return null;
		} else {
			User user = users.get(0);
			List<Group> groups = user.getGroups();
			List<GrantedAuthorityBean> authorities = CollectUtils.newArrayList(groups.size());
			Group defaultGroup = null;
			for (Group g : groups) {
				authorities.add(new GrantedAuthorityBean(g.getName()));
				if (groupProfileService.hasProfile(g)) {
					defaultGroup = g;
				}
			}
			String categoryName = (null == defaultGroup) ? "default" : defaultGroup.getName();
			return new UserToken(user.getId(), user.getName(), user.getFullname(), user.getPassword(),
					categoryName, user.isEnabled(), user.isAccountExpired(), user.isPasswordExpired(), false,
					authorities);
		}
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setGroupProfileService(GroupProfileService groupProfileService) {
		this.groupProfileService = groupProfileService;
	}

}
