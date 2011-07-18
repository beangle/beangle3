/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session.category;

import java.util.Collections;
import java.util.List;

/**
 * @author chaostone
 * @version $Id: SimpleCategoryProfileProvider.java Jul 18, 2011 10:37:47 AM chaostone $
 */
public class SimpleCategoryProfileProvider implements CategoryProfileProvider {

	public List<CategoryProfile> getCategoryProfiles() {
		return Collections.emptyList();
	}

	public String getServerName() {
		return "localhost";
	}

}
