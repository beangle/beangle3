/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.session.category;

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;

public class LimitProfileProviderImpl implements LimitProfileProvider {

	private Map<Object, LimitProfile> profiles = CollectUtils.newHashMap();

	public Map<Object, LimitProfile> getProfiles() {
		return profiles;
	}

	public void setProfiles(Map<Object, LimitProfile> profiles) {
		this.profiles = profiles;
	}

}
