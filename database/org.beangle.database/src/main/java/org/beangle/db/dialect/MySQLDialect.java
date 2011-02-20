/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.dialect;

import java.sql.Types;

import org.beangle.commons.lang.StrUtils;

public class MySQLDialect extends AbstractDialect {

	public MySQLDialect() {
		super();
		registerColumnType(Types.BIT, "bit");
		registerColumnType(Types.BIGINT, "bigint");
		registerColumnType(Types.SMALLINT, "smallint");
		registerColumnType(Types.TINYINT, "tinyint");
		registerColumnType(Types.INTEGER, "integer");
		registerColumnType(Types.CHAR, "char(1)");
		registerColumnType(Types.FLOAT, "float");
		registerColumnType(Types.DOUBLE, "double precision");
		registerColumnType(Types.DATE, "date");
		registerColumnType(Types.TIME, "time");
		registerColumnType(Types.TIMESTAMP, "datetime");
		registerColumnType(Types.VARBINARY, "longblob");
		registerColumnType(Types.VARBINARY, 16777215, "mediumblob");
		registerColumnType(Types.VARBINARY, 65535, "blob");
		registerColumnType(Types.VARBINARY, 255, "tinyblob");
		registerColumnType(Types.LONGVARBINARY, "longblob");
		registerColumnType(Types.LONGVARBINARY, 16777215, "mediumblob");
		registerColumnType(Types.NUMERIC, "decimal($p,$s)");
		registerColumnType(Types.BLOB, "longblob");
		registerColumnType(Types.CLOB, "longtext");
		registerColumnType(Types.VARCHAR, "longtext");
		registerColumnType(Types.VARCHAR, 255, "varchar($l)");
		registerColumnType(Types.LONGVARCHAR, "longtext");
	}

	public String getLimitString(String sql, boolean hasOffset) {
		return new StringBuffer(sql.length() + 20).append(sql).append(hasOffset ? " limit ?, ?" : " limit ?")
				.toString();
	}

	public String getAddForeignKeyConstraintString(String constraintName, String[] foreignKey,
			String referencedTable, String[] primaryKey, boolean referencesPrimaryKey) {
		String cols = StrUtils.join(foreignKey, ", ");
		return new StringBuffer(30).append(" add index ").append(constraintName).append(" (").append(cols)
				.append("), add constraint ").append(constraintName).append(" foreign key (").append(cols)
				.append(") references ").append(referencedTable).append(" (")
				.append(StrUtils.join(primaryKey, ", ")).append(')').toString();
	}

	public String getColumnComment(String comment) {
		return " comment '" + comment + "'";
	}

	public String getTableComment(String comment) {
		return " comment='" + comment + "'";
	}

}
