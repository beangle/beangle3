/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session.category;


public class CategoryProfile {

	private final String category;

	private final int capacity;

	private final int userMaxSessions;

	private final int inactiveInterval;

	public CategoryProfile(String category, int capacity, int userMaxSessions, int inactiveInterval) {
		super();
		this.category = category;
		this.capacity = capacity;
		this.userMaxSessions = userMaxSessions;
		this.inactiveInterval = inactiveInterval;
	}

	public String getCategory() {
		return category;
	}

	public int getCapacity() {
		return capacity;
	}

	public int getUserMaxSessions() {
		return userMaxSessions;
	}

	public int getInactiveInterval() {
		return inactiveInterval;
	}

}
