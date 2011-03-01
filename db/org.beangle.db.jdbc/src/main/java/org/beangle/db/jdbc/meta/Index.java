/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.jdbc.meta;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;

/**
 * JDBC index metadata
 * 
 * @author chaostone
 */
public class Index implements Cloneable{
	private String name;
	private List<Column> columns = CollectUtils.newArrayList();

	public Index() {
		super();
	}

	public Index(String name) {
		super();
		this.name = name;
	}

	public void lowerCase() {
		this.name = StringUtils.lowerCase(name);
	}

	public String getName() {
		return name;
	}

	void addColumn(Column column) {
		if (column != null) columns.add(column);
	}

	public Column[] getColumns() {
		return columns.toArray(new Column[columns.size()]);
	}

	public String toString() {
		return "IndexMatadata(" + name + ')';
	}

	public Index clone() {
		Index cloned;
		try {
			cloned = (Index) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		List<Column> newColumns = CollectUtils.newArrayList();
		for (Column column : columns) {
			newColumns.add(column.clone());
		}
		cloned.columns = newColumns;
		return cloned;
	}

}
