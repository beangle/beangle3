/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.convention.route;

public interface ActionBuilder {

  /**
   * 默认类名对应的控制器名称(含有扩展名)
   * 
   * @param className
   */
  Action build(String className);

  void setProfileService(ProfileService profileService);

  ProfileService getProfileService();

}
