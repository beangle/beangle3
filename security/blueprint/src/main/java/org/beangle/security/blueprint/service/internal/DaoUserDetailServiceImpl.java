/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.blueprint.service.internal;

import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.service.UserService;
import org.beangle.security.blueprint.service.UserToken;
import org.beangle.security.blueprint.session.service.SessionProfileService;
import org.beangle.security.core.authority.GrantedAuthorityBean;
import org.beangle.security.core.userdetail.UserDetail;
import org.beangle.security.core.userdetail.UserDetailService;

public class DaoUserDetailServiceImpl extends BaseServiceImpl implements UserDetailService {

  protected UserService userService;

  protected SessionProfileService sessionProfileService;

  public UserDetail loadDetail(String principle) {
    List<User> users = entityDao.get(User.class, "code", principle);
    if (users.isEmpty()) {
      return null;
    } else {
      User user = users.get(0);
      List<Role> roles = user.getRoles();
      List<GrantedAuthorityBean> authorities = CollectUtils.newArrayList(roles.size());
      Role defaultRole = null;
      for (Role g : roles) {
        authorities.add(new GrantedAuthorityBean(g.getId()));
        if (sessionProfileService.hasProfile(g)) {
          defaultRole = g;
        }
      }
      String categoryName = (null == defaultRole) ? "default" : defaultRole.getName();
      return new UserToken(user.getId(), user.getCode(), user.getName(), user.getPassword(), categoryName,
          user.isEnabled(), user.isAccountExpired(), user.isPasswordExpired(), false, authorities);
    }
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  public void setSessionProfileService(SessionProfileService sessionProfileService) {
    this.sessionProfileService = sessionProfileService;
  }

}
