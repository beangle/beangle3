/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.event;

import java.util.List;

import org.beangle.emsapp.security.User;

/**
 * @author chaostone
 * @version $Id: UserCreationEvent.java Jul 27, 2011 10:18:24 AM chaostone $
 */
public class UserCreationEvent extends UserEvent {

	private static final long serialVersionUID = -3314980522326237621L;

	public UserCreationEvent(List<User> users) {
		super(users);
	}

	@Override
	public String getDescription() {
		return "创建了" + getUserNames();
	}

}
