/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.userdetail;

import org.beangle.security.core.Authentication;

public interface UserDetailService<T extends Authentication> {

	public UserDetail loadDetail(T token);
}
