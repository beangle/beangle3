/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.service;

import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.GroupMember;
import org.beangle.ems.security.User;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.authority.GrantedAuthorityBean;
import org.beangle.security.core.userdetail.UserDetail;
import org.beangle.security.core.userdetail.UserDetailService;

public class DaoUserDetailServiceImpl extends BaseServiceImpl implements UserDetailService<Authentication> {

	protected UserService userService;

	public UserDetail loadDetail(Authentication auth) {
		List<User> users = entityDao.get(User.class, "name", auth.getName());
		if (users.isEmpty()) {
			return null;
		} else {
			User user = users.get(0);
			List<Group> groups = userService.getGroups(user, GroupMember.Ship.MEMBER);
			List<GrantedAuthorityBean> authorities = CollectUtils.newArrayList(groups.size());
			for (Group g : groups) {
				authorities.add(new GrantedAuthorityBean(g.getName()));
			}
			return new UserToken(user.getId(), user.getName(), user.getFullname(), user.getPassword(),
					entityDao.initialize(user.getDefaultCategory()).getTitle(), user.isEnabled(),
					user.isAccountExpired(), user.isPasswordExpired(), false, authorities);
		}
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
