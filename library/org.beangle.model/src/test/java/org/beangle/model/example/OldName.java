/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.example;

import org.beangle.model.pojo.LongIdObject;

/**
 * 曾用名
 * 
 * @author chaostone
 */
public class OldName extends LongIdObject {

	private static final long serialVersionUID = -8986611679625767768L;
	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
