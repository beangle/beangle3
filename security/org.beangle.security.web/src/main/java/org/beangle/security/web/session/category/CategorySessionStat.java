/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.session.category;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.beangle.model.pojo.LongIdObject;

/**
 * 分类会话计数状态
 * 
 * @author chaostone
 * @version $Id: CategorySessionStat.java Jun 18, 2011 2:56:08 PM chaostone $
 */
@Entity
public class CategorySessionStat extends LongIdObject {

	private static final long serialVersionUID = 8698006403892972254L;

	@NotNull
	private String serverName;

	@NotNull
	private Date statAt = new Date();

	@NotNull
	private String category;

	private int capacity;

	private int online;

	private int inactiveInterval;

	private int userMaxSessions;

	public CategorySessionStat() {
		super();
	}

	public CategorySessionStat(String serverName, String category, int capacity, int userMaxSessions) {
		super();
		this.serverName = serverName;
		this.category = category;
		this.capacity = capacity;
		this.userMaxSessions = userMaxSessions;
	}

	public boolean allocate(String sessionId) {
		if (online < capacity) {
			online++;
			return true;
		} else {
			return false;
		}
	}

	public void free(String sessionId) {
		if (online > 0) online--;
	}

	public boolean hasCapacity() {
		return online < capacity;
	}

	public boolean isFull() {
		return !hasCapacity();
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public Date getStatAt() {
		return statAt;
	}

	public void setStatAt(Date statAt) {
		this.statAt = statAt;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
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

	public void setInactiveInterval(int inactiveInterval) {
		this.inactiveInterval = inactiveInterval;
	}

	public int getUserMaxSessions() {
		return userMaxSessions;
	}

	public void setUserMaxSessions(int userMaxSessions) {
		this.userMaxSessions = userMaxSessions;
	}

	public void adjust(int newAllocated) {
		this.capacity += newAllocated;
	}

}
