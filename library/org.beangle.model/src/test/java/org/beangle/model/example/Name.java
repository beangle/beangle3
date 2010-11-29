/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.example;

import java.util.List;

import org.beangle.model.Component;

public class Name implements Component {
	String firstName;

	String lastName;

	List<String> oldNames;

	public List<String> getOldNames() {
		return oldNames;
	}

	public void setOldNames(List<String> oldNames) {
		this.oldNames = oldNames;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
