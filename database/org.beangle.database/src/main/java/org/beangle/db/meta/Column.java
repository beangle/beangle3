/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.meta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

import org.beangle.db.dialect.Dialect;

/**
 * JDBC column metadata
 * 
 * @author chaostone
 */
public class Column implements Comparable<Column> {
	private String name;
	private String typeName;
	private int typeCode;
	private int columnSize;
	private int decimalDigits;
	private boolean nullable;
	private String defaultValue;
	private boolean unique;
	private String comment;
	private String checkConstraint;

	public Column() {
		super();
	}

	public Column(String name) {
		super();
		this.name = name;
	}

	public Column(ResultSet rs) throws SQLException {
		name = rs.getString("COLUMN_NAME");
		columnSize = rs.getInt("COLUMN_SIZE");
		decimalDigits = rs.getInt("DECIMAL_DIGITS");
		nullable = "yes".equalsIgnoreCase(rs.getString("IS_NULLABLE"));
		typeCode = rs.getInt("DATA_TYPE");
		typeName = new StringTokenizer(rs.getString("TYPE_NAME"), "() ").nextToken();
		comment = rs.getString("REMARKS");
		// String COLUMN_DEF= rs.getString("COLUMN_DEF");
		// int po=rs.getInt("ORDINAL_POSITION");
	}

	public String getName() {
		return name;
	}

	public String getTypeName() {
		return typeName;
	}

	public int getColumnSize() {
		return columnSize;
	}

	public int getDecimalDigits() {
		return decimalDigits;
	}

	public boolean isNullable() {
		return nullable;
	}

	public int getTypeCode() {
		return typeCode;
	}

	public String getSqlType(Dialect dialect) {
		return dialect.getTypeName(typeCode, columnSize, columnSize, decimalDigits);
	}

	public int compareTo(Column other) {
		return getName().compareToIgnoreCase(other.getName());
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public boolean isUnique() {
		return unique;
	}

	public String getComment() {
		return comment;
	}

	public String getCheckConstraint() {
		return checkConstraint;
	}

	public void setCheckConstraint(String checkConstraint) {
		this.checkConstraint = checkConstraint;
	}

	public boolean hasCheckConstraint() {
		return checkConstraint != null;
	}

	public String toString() {
		return "Column(" + name + ')';
	}
}
