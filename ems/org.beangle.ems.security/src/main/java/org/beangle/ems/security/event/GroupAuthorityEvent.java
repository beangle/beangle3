/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.event;

import org.beangle.ems.security.Group;

/**
 * @author chaostone
 * @version $Id: GroupAuthorityEvent.java Jul 27, 2011 10:31:48 AM chaostone $
 */
public class GroupAuthorityEvent extends GroupEvent {

	private static final long serialVersionUID = -7689220759741565094L;

	public GroupAuthorityEvent(Group group) {
		super(group);
	}

	@Override
	public String getDescription() {
		return "更改了" + getGroup().getName() + "的权限";
	}
}
