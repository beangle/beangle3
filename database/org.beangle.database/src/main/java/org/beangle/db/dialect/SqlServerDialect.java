/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.dialect;

import java.sql.Types;

public class SqlServerDialect extends AbstractDialect {

	public SqlServerDialect() {
		super();
		registerColumnType(Types.BIT, "tinyint"); // Sybase BIT type does not
		// support null values
		registerColumnType(Types.BIGINT, "numeric(19,0)");
		registerColumnType(Types.SMALLINT, "smallint");
		registerColumnType(Types.TINYINT, "tinyint");
		registerColumnType(Types.INTEGER, "int");
		registerColumnType(Types.CHAR, "char(1)");
		registerColumnType(Types.VARCHAR, "varchar($l)");
		registerColumnType(Types.FLOAT, "float");
		registerColumnType(Types.DECIMAL, "double precision");
		registerColumnType(Types.DOUBLE, "double precision");
		registerColumnType(Types.DATE, "datetime");
		registerColumnType(Types.TIME, "datetime");
		registerColumnType(Types.TIMESTAMP, "datetime");
		registerColumnType(Types.VARBINARY, "varbinary($l)");
		registerColumnType(Types.NUMERIC, "numeric($p,$s)");
		registerColumnType(Types.BLOB, "image");
		registerColumnType(Types.CLOB, "text");
		registerColumnType(Types.LONGVARCHAR, "text");
	}

	static int getAfterSelectInsertPoint(String sql) {
		int selectIndex = sql.toLowerCase().indexOf("select");
		final int selectDistinctIndex = sql.toLowerCase().indexOf("select distinct");
		return selectIndex + (selectDistinctIndex == selectIndex ? 15 : 6);
	}

	public String getLimitString(String sql, boolean hasOffset) {
		if (hasOffset) { throw new UnsupportedOperationException("sql server has no offset"); }
		return new StringBuilder(sql.length() + 8).append(sql)
				.insert(getAfterSelectInsertPoint(sql), " top ?").toString();
	}
}
