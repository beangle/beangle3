/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.event;

import java.util.List;

import org.beangle.security.SecurityUtils;
import org.beangle.security.blueprint.User;

/**
 * @author chaostone
 * @version $Id: UserRemoveEvent.java Jul 27, 2011 10:24:33 AM chaostone $
 */
public class UserRemoveEvent extends UserEvent {
  private static final long serialVersionUID = -6477958983678067472L;

  public UserRemoveEvent(List<User> users) {
    super(users);
    setSubject(SecurityUtils.getUsername() + " 删除了" + getUserNames() + " 用户");
  }

}
