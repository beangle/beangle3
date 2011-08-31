/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.session.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.beangle.ems.security.Category;
import org.beangle.model.pojo.LongIdObject;

/**
 * 用户种类配置
 * @author chaostone
 *
 */
@Entity
public class CategoryProfileBean extends LongIdObject {

	private static final long serialVersionUID = 1999239598984221565L;

	/**会话配置*/
	@NotNull
	protected SessionProfileBean sessionProfile;

	/**用户种类*/
	@NotNull
	protected Category category;

	/**容量*/
	@NotNull
	protected int capacity;

	/**最大会话数*/
	@NotNull
	protected int userMaxSessions = 1;

	/** 过期时间 */
	@NotNull
	protected int inactiveInterval;

	public CategoryProfileBean() {
		super();
	}

	public CategoryProfileBean(Category category, int max, int inactiveInterval) {
		super();
		this.category = category;
		this.capacity = max;
		this.inactiveInterval = inactiveInterval;
	}

	public SessionProfileBean getSessionProfile() {
		return sessionProfile;
	}

	public void setSessionProfile(SessionProfileBean sessionProfile) {
		this.sessionProfile = sessionProfile;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int max) {
		this.capacity = max;
	}

	public int getInactiveInterval() {
		return inactiveInterval;
	}

	public void setInactiveInterval(int inactiveInterval) {
		this.inactiveInterval = inactiveInterval;
	}

	public int getUserMaxSessions() {
		return userMaxSessions;
	}

	public void setUserMaxSessions(int maxSessions) {
		this.userMaxSessions = maxSessions;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(category.getName());
		sb.append(":{max=").append(capacity).append(',');
		sb.append("maxSessions=").append(userMaxSessions).append(',');
		sb.append("inactiveInterval=").append(inactiveInterval).append('}');
		return sb.toString();
	}

}
