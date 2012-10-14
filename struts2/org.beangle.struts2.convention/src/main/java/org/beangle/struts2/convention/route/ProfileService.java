/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.convention.route;

import java.util.List;

public interface ProfileService {

  Profile getProfile(String className);

  Profile getProfile(Class<?> clazz);

  Profile getDefaultProfile();

  List<Profile> getProfiles();

}
