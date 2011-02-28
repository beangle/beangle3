/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.jdbc.meta;

import java.util.Iterator;

public class PrimaryKey extends Constraint {

	public PrimaryKey() {
		super();
	}

	public PrimaryKey(String name, Column column) {
		super();
		setName(name);
		addColumn(column);
	}

	public String sqlConstraintString() {
		StringBuffer buf = new StringBuffer("primary key (");
		Iterator<Column> iter = getColumns().iterator();
		while (iter.hasNext()) {
			buf.append(iter.next().getName());
			if (iter.hasNext()) buf.append(", ");
		}
		return buf.append(')').toString();
	}
}
