/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.data;

import java.util.List;

/**
 * 角色配置
 * 
 * @author chaostone
 * @version $Id: UserProfile.java Oct 21, 2011 8:43:35 AM chaostone $
 */
public interface RoleProfile extends Profile {

  List<RoleProperty> getProperties();

  RoleProperty getProperty(ProfileField meta);

}
