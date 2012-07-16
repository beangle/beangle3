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
 * @version $Id: AccountStatusEvent.java Jun 22, 2011 8:58:14 AM chaostone $
 */
public class UserStatusEvent extends UserEvent {

  private static final long serialVersionUID = -8120260840834127793L;
  private boolean enabled;

  public UserStatusEvent(List<User> users, boolean enabled) {
    super(users);
    this.enabled = enabled;
    setSubject(SecurityUtils.getUsername() + (enabled ? "激活" : "禁用") + "了" + getUserNames());
  }

  public boolean isEnabled() {
    return enabled;
  }

}
