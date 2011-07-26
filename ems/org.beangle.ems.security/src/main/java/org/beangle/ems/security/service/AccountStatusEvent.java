/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.service;

import java.util.List;

import org.beangle.ems.event.BusinessEvent;
import org.beangle.ems.security.User;

/**
 * @author chaostone
 * @version $Id: AccountStatusEvent.java Jun 22, 2011 8:58:14 AM chaostone $
 */
public class AccountStatusEvent extends BusinessEvent {

	private static final long serialVersionUID = -8120260840834127793L;
	private boolean enabled;

	public AccountStatusEvent(Object source) {
		super(source);
		this.resource = "用户管理";
	}

	public AccountStatusEvent(Object source, boolean enabled) {
		this(source);
		this.enabled = enabled;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isEnabled() {
		return enabled;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getDescription() {
		List<User> users = (List<User>) source;
		StringBuilder sb = new StringBuilder();
		for (User user : users) {
			sb.append(user.getName()).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		return (enabled ? "激活" : "禁用") + "了" + sb;
	}

}
