/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.meta;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.db.dialect.Dialect;

/**
 * JDBC foreign key metadata
 * 
 * @author chaostone
 */
public class ForeignKey extends Constraint {

	private Table referencedTable;
	private boolean cascadeDelete;
	private List<Column> referencedColumns = CollectUtils.newArrayList();

	public ForeignKey() {
		super();
	}

	public ForeignKey(String name, Column column) {
		super();
		setName(name);
		addColumn(column);
	}

	public String sqlConstraintString(Dialect dialect) {
		Validate.notNull(getName());
		Validate.notNull(getTable());
		Validate.notNull(referencedTable, "referencedTable must be set");
		Validate.isTrue(!isReferenceToPrimaryKey() || null != referencedTable.getPrimaryKey(),
				" reference columns is empty  so the table must has a primary key.");
		Validate.notEmpty(getColumns(), "column's size should greate than 0");

		String[] cols = new String[getColumns().size()];
		String[] refcols = new String[getColumns().size()];
		int i = 0;
		Iterator<Column> refiter = null;
		if (isReferenceToPrimaryKey()) {
			refiter = referencedTable.getPrimaryKey().getColumns().iterator();
		} else {
			refiter = referencedColumns.iterator();
		}

		Iterator<Column> iter = getColumns().iterator();
		while (iter.hasNext()) {
			cols[i] = iter.next().getName();
			refcols[i] = refiter.next().getName();
			i++;
		}

		String result = dialect.getAddForeignKeyConstraintString(
				getName(),
				cols,
				Table.qualify(getTable().getSchema(), referencedTable.getName()), refcols, isReferenceToPrimaryKey());

		return cascadeDelete && dialect.supportsCascadeDelete() ? result + " on delete cascade"
				: result;
	}
	
	public void addReferencedColumn(Column column){
		this.referencedColumns.add(column);
	}

	public List<Column> getReferencedColumns() {
		return referencedColumns;
	}

	public boolean isReferenceToPrimaryKey() {
		return referencedColumns.isEmpty();
	}

	public Table getReferencedTable() {
		return referencedTable;
	}

	public void setReferencedTable(Table referencedTable) {
		this.referencedTable = referencedTable;
	}

	public boolean isCascadeDelete() {
		return cascadeDelete;
	}

	public void setCascadeDelete(boolean cascadeDelete) {
		this.cascadeDelete = cascadeDelete;
	}

	public String toString() {
		return "Foreign key(" + getName() + ')';
	}
}
