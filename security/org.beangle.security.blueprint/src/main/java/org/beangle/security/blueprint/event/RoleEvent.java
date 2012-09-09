/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.event;

import org.beangle.commons.context.event.Event;
import org.beangle.security.blueprint.Role;

/**
 * @author chaostone
 * @version $Id: RoleEvent.java Jul 27, 2011 10:29:31 AM chaostone $
 */
public class RoleEvent extends Event {

  private static final long serialVersionUID = 2633756457351411934L;

  public RoleEvent(Role role) {
    super(role);
    setResource("角色管理");
  }

  protected Role getRole() {
    return (Role) source;
  }
}
