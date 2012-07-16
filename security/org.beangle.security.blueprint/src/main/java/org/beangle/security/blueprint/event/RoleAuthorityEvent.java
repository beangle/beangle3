/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.event;

import org.beangle.security.SecurityUtils;
import org.beangle.security.blueprint.Role;

/**
 * @author chaostone
 * @version $Id: RoleAuthorityEvent.java Jul 27, 2011 10:31:48 AM chaostone $
 */
public class RoleAuthorityEvent extends RoleEvent {

  private static final long serialVersionUID = -7689220759741565094L;

  public RoleAuthorityEvent(Role role) {
    super(role);
    setSubject(SecurityUtils.getUsername() +" 更改了" + getRole().getName() + "的权限");
  }
}
