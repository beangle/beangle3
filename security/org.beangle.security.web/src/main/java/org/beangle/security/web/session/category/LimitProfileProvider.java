/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.session.category;

import java.util.Map;

public interface LimitProfileProvider {

	/**
	 * 查询某类用户的配置
	 * 
	 * @param category
	 * @return
	 */
	public Map<Object, LimitProfile> getProfiles();

}
