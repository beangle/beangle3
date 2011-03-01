/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.jdbc.meta;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;

/**
 * Table Constraint Metadata
 * 
 * @author chaostone
 */
public class Constraint implements Comparable<Constraint>,Cloneable {
	protected String name;
	protected List<Column> columns = CollectUtils.newArrayList();
	protected Table table;

	public void lowerCase() {
		this.name = StringUtils.lowerCase(name);
	}

	public void addColumn(Column column) {
		if (column != null) columns.add(column);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public int compareTo(Constraint o) {
		return getName().compareTo(o.getName());
	}

	public Constraint clone() {
		Constraint cloned;
		try {
			cloned = (Constraint) super.clone();
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
