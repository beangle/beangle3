/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.jdbc.meta;

/**
 * Unique Key
 * 
 * @author chaostone
 */
public class UniqueKey extends Constraint {

	public UniqueKey() {
		super();
	}

	public UniqueKey(String name) {
		super();
		setName(name);
	}

}
