/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.jdbc.meta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.beangle.db.jdbc.dialect.Dialect;

/**
 * JDBC column metadata
 * 
 * @author chaostone
 */
public class Column implements Comparable<Column>, Cloneable {
	private String name;
	private String typeName;
	private int typeCode;
	// charactor length or numeric precision
	private int size;
	private short scale;
	private boolean nullable;
	private String defaultValue;
	private boolean unique;
	private String comment;
	private String checkConstraint;

	private int position;

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
		position = rs.getInt("ORDINAL_POSITION");
		size = rs.getInt("COLUMN_SIZE");
		scale = rs.getShort("DECIMAL_DIGITS");
		nullable = "yes".equalsIgnoreCase(rs.getString("IS_NULLABLE"));
		typeCode = rs.getInt("DATA_TYPE");
		typeName = new StringTokenizer(rs.getString("TYPE_NAME"), "() ").nextToken();
		comment = rs.getString("REMARKS");
	}

	public Column clone() {
		try {
			return (Column) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	public void lowerCase() {
		this.name = StringUtils.lowerCase(name);
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

	public boolean isNullable() {
		return nullable;
	}

	public int getTypeCode() {
		return typeCode;
	}

	public String getSqlType(Dialect dialect) {
		return dialect.getTypeNames().get(typeCode, size, size, scale);
	}

	public int compareTo(Column other) {
		return getPosition() - other.getPosition();
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

	public short getScale() {
		return scale;
	}

	public void setScale(short scale) {
		this.scale = scale;
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

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

}
