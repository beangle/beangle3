/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.convention.route;

import java.util.List;

public interface ProfileService {

	public Profile getProfile(String className);

	public Profile getProfile(Class<?> clazz);

	public Profile getDefaultProfile();

	public List<Profile> getProfiles();

}
