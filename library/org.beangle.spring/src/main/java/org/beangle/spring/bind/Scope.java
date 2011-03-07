/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.spring.bind;

public enum Scope {

	SINGLETON(""), PROTOTYPE("prototype"), REQUEST("request"), SESSION("session");

	String name;

	Scope(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}
}
