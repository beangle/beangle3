/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.session.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.collection.CollectUtils;
import org.beangle.model.pojo.LongIdObject;

/**
 * 会话配置
 * 
 * @author chaostone
 */
@Entity
public class SessionProfileBean extends LongIdObject {

	private static final long serialVersionUID = 7877599995789627073L;

	/** 配置名称 */
	@NotNull
	@Size(max = 50)
	@Column(unique = true)
	private String name;

	/** 单用户最大session数 */
	@NotNull
	private int userMaxSessions;

	/** 过期时间(min) */
	@NotNull
	private int inactiveInterval;

	/** 用户种类特定配置 */
	@OneToMany(mappedBy = "sessionProfile")
	private List<GroupSessionProfileBean> categoryProfiles = CollectUtils.newArrayList();

	public SessionProfileBean() {
		super();
	}

	public SessionProfileBean(String name, int userMaxSessions, int inactiveInterval) {
		super();
		this.name = name;
		this.userMaxSessions = userMaxSessions;
		this.inactiveInterval = inactiveInterval;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public List<GroupSessionProfileBean> getCategoryProfiles() {
		return categoryProfiles;
	}

	public void setCategoryProfiles(List<GroupSessionProfileBean> categoryProfiles) {
		this.categoryProfiles = categoryProfiles;
	}

}
