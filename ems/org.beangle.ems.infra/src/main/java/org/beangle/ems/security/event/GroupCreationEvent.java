/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.event;

import org.beangle.ems.security.Group;

/**
 * @author chaostone
 * @version $Id: GroupCreationEvent.java Jul 27, 2011 10:30:24 AM chaostone $
 */
public class GroupCreationEvent extends GroupEvent {
	private static final long serialVersionUID = -60909204679372326L;

	public GroupCreationEvent(Group group) {
		super(group);
	}

	@Override
	public String getDescription() {
		return "创建了" + getGroup().getName();
	}

}
