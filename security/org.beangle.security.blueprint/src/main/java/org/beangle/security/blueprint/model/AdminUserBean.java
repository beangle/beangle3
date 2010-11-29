/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.model;

import org.beangle.model.pojo.LongIdTimeObject;
import org.beangle.security.blueprint.AdminUser;
import org.beangle.security.blueprint.User;

public class AdminUserBean extends LongIdTimeObject implements AdminUser {

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
