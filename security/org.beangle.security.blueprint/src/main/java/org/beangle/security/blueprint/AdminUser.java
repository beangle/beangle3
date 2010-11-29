/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint;

import org.beangle.model.pojo.TimeEntity;

public interface AdminUser extends TimeEntity<Long> {

	public User getUser();

	public void setUser(User user);

}
