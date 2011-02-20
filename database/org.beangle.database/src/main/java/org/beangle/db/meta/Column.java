/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.meta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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
	private int size;
	private int decimalDigits;
	private boolean nullable;
	private String defaultValue;
	private boolean unique;
	private String comment;
	private String checkConstraint;

	public Column() {
		super();
	}

	public Column(String name, int typeCode) {
		super();
		this.name = name;
		this.typeCode = typeCode;
		if (typeCode == Types.VARCHAR) {
			this.size = 255;
		}
	}

	public Column(ResultSet rs) throws SQLException {
		name = rs.getString("COLUMN_NAME");
		size = rs.getInt("COLUMN_SIZE");
		decimalDigits = rs.getInt("DECIMAL_DIGITS");
		nullable = "yes".equalsIgnoreCase(rs.getString("IS_NULLABLE"));
		typeCode = rs.getInt("DATA_TYPE");
		typeName = new StringTokenizer(rs.getString("TYPE_NAME"), "() ").nextToken();
		comment = rs.getString("REMARKS");
	}

	public String getName() {
		return name;
	}

	public String getTypeName() {
		return typeName;
	}

	public int getSize() {
		return size;
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
		return dialect.getTypeName(typeCode, size, size, decimalDigits);
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

	public void setName(String name) {
		this.name = name;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public void setTypeCode(int typeCode) {
		this.typeCode = typeCode;
	}

	public void setSize(int columnSize) {
		this.size = columnSize;
	}

	public void setDecimalDigits(int decimalDigits) {
		this.decimalDigits = decimalDigits;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
