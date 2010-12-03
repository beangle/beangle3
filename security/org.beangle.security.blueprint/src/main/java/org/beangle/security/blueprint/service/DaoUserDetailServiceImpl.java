/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.service;

import java.util.List;

import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.UserCategory;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.authority.GrantedAuthorityBean;
import org.beangle.security.core.userdetail.UserDetail;
import org.beangle.security.core.userdetail.UserDetailService;

public class DaoUserDetailServiceImpl extends BaseServiceImpl implements UserDetailService {

	public UserDetail loadDetail(Authentication auth) {
		List<User> users = entityDao.get(User.class, "name", auth.getName());
		if (users.isEmpty()) {
			return null;
		} else {
			User user = users.get(0);
			String hql = "select g.group.name from User u join u.groups as g where u.id=?";
			List<String> groupNames = entityDao.searchHQLQuery(hql, user.getId());
			GrantedAuthorityBean[] authorities = new GrantedAuthorityBean[groupNames.size()];
			int i = 0;
			for (String group : groupNames) {
				authorities[i] = new GrantedAuthorityBean(group);
				i++;
			}
			return new UserToken(user.getId(), user.getName(), user.getFullname(),
					user.getPassword(), entityDao.get(UserCategory.class, user.getDefaultCategory()
							.getId()), user.getStatus() > 0, false, false, false, authorities);
		}
	}

}
