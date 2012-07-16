/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
    List<User> users = entityDao.get(User.class, "name", principle);
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
      return new UserToken(user.getId(), user.getName(), user.getFullname(), user.getPassword(),
          categoryName, user.isEnabled(), user.isAccountExpired(), user.isPasswordExpired(), false,
          authorities);
    }
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  public void setSessionProfileService(SessionProfileService sessionProfileService) {
    this.sessionProfileService = sessionProfileService;
  }

}
