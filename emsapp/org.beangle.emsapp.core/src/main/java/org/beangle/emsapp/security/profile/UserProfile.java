/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.profile;

import java.util.List;

import org.beangle.emsapp.security.User;

/**
 * 用户配置
 * 
 * @author chaostone
 * @version $Id: UserProfile.java Oct 21, 2011 8:43:35 AM chaostone $
 */
public interface UserProfile extends Profile {

	public List<UserProperty> getProperties();

	public UserProperty getProperty(UserPropertyMeta meta);

	public User getUser();

}
