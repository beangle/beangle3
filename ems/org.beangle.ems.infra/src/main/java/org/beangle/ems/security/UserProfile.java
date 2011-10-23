/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security;

import java.util.List;

/**
 * 用户配置
 * 
 * @author chaostone
 * @version $Id: UserProfile.java Oct 21, 2011 8:43:35 AM chaostone $
 */
public interface UserProfile {

	public List<UserProperty> getProperties();

}
