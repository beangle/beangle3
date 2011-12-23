/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.event;

import java.util.List;

import org.beangle.emsapp.event.BusinessEvent;
import org.beangle.emsapp.security.User;

/**
 * 用户变动事件
 * 
 * @author chaostone
 * @version $Id: UserEvent.java Jul 27, 2011 10:11:20 AM chaostone $
 */
public class UserEvent extends BusinessEvent {
	private static final long serialVersionUID = -2213942260473001852L;

	public UserEvent(List<User> users) {
		super(users);
		this.resource = "用户管理";
	}

	@SuppressWarnings("unchecked")
	public List<User> getUsers() {
		return (List<User>) source;
	}

	public String getUserNames() {
		StringBuilder sb = new StringBuilder();
		for (User user : getUsers()) {
			sb.append(user.getName()).append('(').append(user.getFullname()).append("),");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
}
