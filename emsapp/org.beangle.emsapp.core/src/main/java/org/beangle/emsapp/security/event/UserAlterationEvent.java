/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.event;

import java.util.List;

import org.beangle.emsapp.security.User;

/**
 * @author chaostone
 * @version $Id: UserAlterationEvent.java Jul 27, 2011 10:18:55 AM chaostone $
 */
public class UserAlterationEvent extends UserEvent {

	private static final long serialVersionUID = 8988908030203145117L;

	public UserAlterationEvent(List<User> users) {
		super(users);
	}


	@Override
	public String getDescription() {
		return "修改了"+getUserNames();
	}
	
}
