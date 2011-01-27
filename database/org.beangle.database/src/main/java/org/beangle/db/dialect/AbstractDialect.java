/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.dialect;

import org.beangle.commons.lang.StrUtils;

public abstract class AbstractDialect implements Dialect {
	protected TypeNames typeNames = new TypeNames();
	protected SequenceSupport ss = null;

	public String getCreateTableString() {
		return "create table";
	}

	protected void registerColumnType(int code, int capacity, String name) {
		typeNames.put(code, capacity, name);
	}

	protected void registerColumnType(int code, String name) {
		typeNames.put(code, name);
	}

	public SequenceSupport getSequenceSupport() {
		return ss;
	}

	public String getTypeName(int typecode, int size, int precision, int scale) {
		return typeNames.get(typecode, size, precision, scale);
	}

	public String getTypeName(int typecode) {
		return typeNames.get(typecode);
	}

	public String getAddForeignKeyConstraintString(String constraintName, String[] foreignKey,
			String referencedTable, String[] primaryKey, boolean referencesPrimaryKey) {
		StringBuffer res = new StringBuffer(30);
		res.append(" add constraint ").append(constraintName).append(" foreign key (")
				.append(StrUtils.join(foreignKey, ", ")).append(") references ")
				.append(referencedTable);
		if (!referencesPrimaryKey) {
			res.append(" (").append(StrUtils.join(primaryKey, ", ")).append(')');
		}
		return res.toString();
	}
	
	public String getNullColumnString() {
		return "";
	}

	public String getTableComment(String comment) {
		return "";
	}

	public String getColumnComment(String comment) {
		return "";
	}

	public boolean supportsUnique() {
		return true;
	}
	
	public boolean supportsNullUnique(){
		return true;
	}
	
	public boolean supportsColumnCheck() {
		return true;
	}
	
	public boolean supportsCascadeDelete() {
		return true;
	}
}
