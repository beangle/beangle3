/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.session.model;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.pojo.LongIdObject;
import org.beangle.ems.security.session.CategoryProfile;
import org.beangle.ems.security.session.SessionProfile;

/**
 * 会话配置
 * 
 * @author chaostone
 */
@Entity(name = "org.beangle.ems.security.session.SessionProfile")
public class SessionProfileBean extends LongIdObject implements SessionProfile {

	private static final long serialVersionUID = 7877599995789627073L;

	/** 配置名称 */
	@NotNull
	@Size(max = 50)
	@Column(unique = true)
	private String name;

	/** 系统最大在线人数 */
	@NotNull
	private int capacity;

	/** 单用户最大session数 */
	@NotNull
	private int userMaxSessions;

	/** 过期时间(min) */
	@NotNull
	private int inactiveInterval;

	/** 用户种类特定配置 */
	@OneToMany(mappedBy = "sessionProfile")
	@MapKeyColumn(name = "category_id")
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
