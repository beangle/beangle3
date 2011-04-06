/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.session.category;

import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.security.core.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chaostone
 * @version $Id: MemCategorySessionController.java Nov 21, 2010 3:46:25 PM
 *          chaostone $
 */
public class MemCategorySessionController implements CategorySessionController {

	private final Logger logger = LoggerFactory.getLogger(MemCategorySessionController.class);

	/** 用户类型配置提供服务 */
	protected LimitProfileProvider profileProvider;

	/** 各类监测类型的监测数据 */
	protected Map<Object, LimitProfile> profileMap = CollectUtils.newHashMap();

	/** 是否加载了用户配置 */
	private boolean loaded = false;

	/**
	 * @return max session for given auth
	 */
	public int getMaximumSessions(Authentication auth) {
		loadProfilesWhenNecessary();
		Object category = ((CategoryPrincipal) auth.getPrincipal()).getCategory();
		LimitProfile profile = getProfile(category);
		if (null == profile) {
			logger.warn("cannot find profile for {},using default", category);
			profile = new LimitProfile(category, 2000, 15, 1);
			profileMap.put(category, profile);
		}
		return profile.getUserMaxSessions();
	}

	protected void loadProfilesWhenNecessary() {
		if (!loaded) {
			synchronized (this) {
				if (loaded) return;
				loadProfiles();
			}
		}
	}

	// FIXME 动态更新配置
	public void loadProfiles() {
		if (null == profileProvider) {
			loaded = true;
			logger.warn("Cannot load profile for  profileProvider not found!");
			return;
		}
		for (LimitProfile cp : profileProvider.getProfiles().values()) {
			int initOnline = 0;
			LimitProfile existed = profileMap.get(cp.getCategory());
			if (null != existed) {
				initOnline = existed.getOnline();
			}
			cp.setOnline(initOnline);
			profileMap.put(cp.getCategory(), cp);
			logger.info(String.valueOf(cp));
		}
		loaded = !(profileMap.isEmpty());
	}

	public List<LimitProfile> getProfiles() {
		return CollectUtils.newArrayList(profileMap.values());
	}

	public LimitProfile getProfile(Object category) {
		return profileMap.get(category);
	}

	public boolean reserve(Object category, String sessionid) {
		LimitProfile profile = getProfile(category);
		synchronized (profile) {
			return profile.reserve();
		}
	}

	public void left(Object category) {
		LimitProfile profile = profileMap.get(category);
		synchronized (profile) {
			profile.left();
		}
	}

	public boolean changeCategory(Object from, Object to) {
		LimitProfile fromProfile = profileMap.get(from);
		boolean reserved = false;
		synchronized (fromProfile) {
			reserved = fromProfile.reserve();
		}
		if (!reserved) { return false; }
		LimitProfile toProfile = profileMap.get(to);
		synchronized (toProfile) {
			toProfile.left();
		}
		return true;
	}

	public int getMax() {
		int max = 0;
		for (LimitProfile element : profileMap.values()) {
			max += element.getCapacity();
		}
		return max;
	}

	public int getSessionCount() {
		int count = 0;
		for (LimitProfile element : profileMap.values()) {
			count += element.getCapacity();
		}
		return count;
	}

	public LimitProfileProvider getProfileProvider() {
		return profileProvider;
	}

	public void setProfileProvider(LimitProfileProvider profileProvider) {
		this.profileProvider = profileProvider;
	}

}
