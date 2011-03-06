/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.session.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.security.blueprint.Category;
import org.beangle.security.blueprint.session.CategoryProfile;
import org.beangle.security.blueprint.session.SessionProfile;

@Entity(name = "org.beangle.security.blueprint.session.CategoryProfile")
public class CategoryProfileBean extends LongIdObject implements CategoryProfile {

	private static final long serialVersionUID = 1999239598984221565L;

	@NotNull
	protected SessionProfile sessionProfile;

	@NotNull
	protected Category category;

	@NotNull
	protected int capacity;

	@NotNull
	protected int userMaxSessions = 1;

	/** minutes */
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

	public SessionProfile getSessionProfile() {
		return sessionProfile;
	}

	public void setSessionProfile(SessionProfile sessionProfile) {
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
