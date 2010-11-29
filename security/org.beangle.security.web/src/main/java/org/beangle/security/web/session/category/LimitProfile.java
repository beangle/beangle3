/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.session.category;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 用户类别配置
 */
public class LimitProfile {
	private Object category;
	private int capacity;
	private int inactiveInterval;
	private int userMaxSessions;
	private int online = 0;

	public LimitProfile() {
		super();
	}

	public LimitProfile(Object category, int capacity, int inactiveInterval, int userMaxSessions) {
		super();
		this.category = category;
		this.capacity = capacity;
		this.inactiveInterval = inactiveInterval;
		this.userMaxSessions = userMaxSessions;
	}

	public Object getCategory() {
		return category;
	}

	public int getCapacity() {
		return capacity;
	}

	public boolean reserve() {
		if (online < capacity) {
			online++;
			return true;
		} else {
			return false;
		}
	}

	public void left() {
		if (online > 0) online--;
	}

	public boolean hasCapacity() {
		return online < capacity;
	}

	public boolean isFull() {
		return !hasCapacity();
	}

	public int getOnline() {
		return online;
	}

	public void setOnline(int online) {
		this.online = online;
	}

	public int getInactiveInterval() {
		return inactiveInterval;
	}

	public int getUserMaxSessions() {
		return userMaxSessions;
	}

	public void setCategory(Object category) {
		this.category = category;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void setInactiveInterval(int inactiveInterval) {
		this.inactiveInterval = inactiveInterval;
	}

	public void setUserMaxSessions(int userMaxSessions) {
		this.userMaxSessions = userMaxSessions;
	}

	public int hashCode() {
		return new HashCodeBuilder().append(category).toHashCode();
	}

	public boolean equals(final Object object) {
		if (!(object instanceof LimitProfile)) { return false; }
		LimitProfile rhs = (LimitProfile) object;
		return ObjectUtils.equals(category, rhs.getCategory());
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Category:").append(getCategory()).append("; ");
		sb.append("Capacity:").append(getCapacity()).append("; ");
		sb.append("InactiveInterval:").append(getInactiveInterval()).append("; ");
		sb.append("UserMaxSessions:").append(getUserMaxSessions());
		return sb.toString();
	}
}
