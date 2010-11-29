/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.session.model;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.security.blueprint.UserCategory;
import org.beangle.security.blueprint.session.CategoryProfile;
import org.beangle.security.blueprint.session.SessionProfile;

public class CategoryProfileBean extends LongIdObject implements CategoryProfile {

	private static final long serialVersionUID = 1999239598984221565L;

	protected SessionProfile sessionProfile;

	protected UserCategory category;

	protected int capacity;

	protected int userMaxSessions = 1;

	/** minutes */
	protected int inactiveInterval;

	public CategoryProfileBean() {
		super();
	}

	public CategoryProfileBean(UserCategory category, int max, int inactiveInterval) {
		super();
		this.category = category;
		this.capacity = max;
		this.inactiveInterval = inactiveInterval;
	}

	public SessionProfile getSessionProfile() {
		return sessionProfile;
	}

	public void setSessionProfile(SessionProfile sessionProfile) {
		this.sessionProfile = sessionProfile;
	}

	public UserCategory getCategory() {
		return category;
	}

	public void setCategory(UserCategory category) {
		this.category = category;
	}

	/*
	 * (non-Javadoc)
	 * @see org.beangle.webapp.security.model.A#getCapacity()
	 */
	public int getCapacity() {
		return capacity;
	}

	/*
	 * (non-Javadoc)
	 * @see org.beangle.webapp.security.model.A#setCapacity(int)
	 */
	public void setCapacity(int max) {
		this.capacity = max;
	}

	/*
	 * (non-Javadoc)
	 * @see org.beangle.webapp.security.model.A#getInactiveInterval()
	 */
	public int getInactiveInterval() {
		return inactiveInterval;
	}

	/*
	 * (non-Javadoc)
	 * @see org.beangle.webapp.security.model.A#setInactiveInterval(int)
	 */
	public void setInactiveInterval(int inactiveInterval) {
		this.inactiveInterval = inactiveInterval;
	}

	/*
	 * (non-Javadoc)
	 * @see org.beangle.webapp.security.model.A#getUserMaxSessions()
	 */
	public int getUserMaxSessions() {
		return userMaxSessions;
	}

	/*
	 * (non-Javadoc)
	 * @see org.beangle.webapp.security.model.A#setUserMaxSessions(int)
	 */
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
