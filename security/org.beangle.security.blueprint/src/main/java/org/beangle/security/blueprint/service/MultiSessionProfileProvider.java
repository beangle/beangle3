/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.service;

import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.persist.impl.AbstractBaseDao;
import org.beangle.security.blueprint.UserCategory;
import org.beangle.security.blueprint.session.CategoryProfile;
import org.beangle.security.web.session.category.LimitProfile;
import org.beangle.security.web.session.category.LimitProfileProvider;

public class MultiSessionProfileProvider extends AbstractBaseDao implements LimitProfileProvider {

	private Long sessionProfileId;

	public Map<Object, LimitProfile> getProfiles() {
		List<CategoryProfile> profiles = entityDao.getAll(CategoryProfile.class);
		Map<Object, LimitProfile> profileMap = CollectUtils.newHashMap();
		for (CategoryProfile profile : profiles) {
			UserCategory category = entityDao
					.get(UserCategory.class, profile.getCategory().getId());
			profileMap.put(category,
					new LimitProfile(category, profile.getCapacity(),
							profile.getInactiveInterval(), profile.getInactiveInterval()));
		}
		return profileMap;
	}

	public Long getSessionProfileId() {
		return sessionProfileId;
	}

	public void setSessionProfileId(Long sessionProfileId) {
		this.sessionProfileId = sessionProfileId;
	}

}
