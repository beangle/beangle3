/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.dialect;

public interface Dialect {

	public SequenceSupport getSequenceSupport();

	public String getTypeName(int typecode, int size, int precision, int scale);

	public String getTypeName(int typecode);

	public String getCreateTableString();

	public String getLimitString(String sql, boolean hasOffset);

	public String getAddForeignKeyConstraintString(String constraintName, String[] foreignKey,
			String referencedTable, String[] primaryKey, boolean referencesPrimaryKey);

	public boolean supportsCascadeDelete();

	public String getNullColumnString();

	public String getTableComment(String comment);

	public String getColumnComment(String comment);

	public boolean supportsUnique();

	public boolean supportsNullUnique();
	
	public boolean supportsColumnCheck();
}
