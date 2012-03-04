/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.profile;

import java.util.List;

import org.beangle.ems.security.restrict.RestrictionHolder;

/**
 * 用户组配置
 * 
 * @author chaostone
 * @version $Id: UserProfile.java Oct 21, 2011 8:43:35 AM chaostone $
 */
public interface GroupProfile extends Profile, RestrictionHolder {

	public List<GroupProperty> getProperties();

	public GroupProperty getProperty(GroupPropertyMeta meta);

}
