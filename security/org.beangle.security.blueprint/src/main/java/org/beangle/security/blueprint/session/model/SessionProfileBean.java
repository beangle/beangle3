/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.session.model;

import java.util.Map;

import javax.persistence.Entity;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.pojo.LongIdObject;
import org.beangle.security.blueprint.session.CategoryProfile;
import org.beangle.security.blueprint.session.SessionProfile;

/**
 * 会话配置
 * 
 * @author chaostone
 */
@Entity(name = "org.beangle.security.blueprint.session.SessionProfile")
public class SessionProfileBean extends LongIdObject implements SessionProfile {

	private static final long serialVersionUID = 7877599995789627073L;

	/** 配置名称 */
	private String name;

	/** 系统最大在线人数 */
	private int capacity;

	/** 单用户最大session数 */
	private int userMaxSessions;

	/** 过期时间(min) */
	private int inactiveInterval;

	/** 用户种类特定配置 */
	private Map<Long, CategoryProfile> categoryProfiles = CollectUtils.newHashMap();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int max) {
		this.capacity = max;
	}

	public int getUserMaxSessions() {
		return userMaxSessions;
	}

	public void setUserMaxSessions(int maxSessions) {
		this.userMaxSessions = maxSessions;
	}

	public int getInactiveInterval() {
		return inactiveInterval;
	}

	public void setInactiveInterval(int inactiveInterval) {
		this.inactiveInterval = inactiveInterval;
	}

	public Map<Long, CategoryProfile> getCategoryProfiles() {
		return categoryProfiles;
	}

	public void setCategoryProfiles(Map<Long, CategoryProfile> categoryProfiles) {
		this.categoryProfiles = categoryProfiles;
	}

}
