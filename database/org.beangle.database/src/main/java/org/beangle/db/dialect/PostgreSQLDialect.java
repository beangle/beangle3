/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.dialect;

import static java.sql.Types.BIGINT;
import static java.sql.Types.BIT;
import static java.sql.Types.BLOB;
import static java.sql.Types.BOOLEAN;
import static java.sql.Types.CHAR;
import static java.sql.Types.CLOB;
import static java.sql.Types.DATE;
import static java.sql.Types.DOUBLE;
import static java.sql.Types.FLOAT;
import static java.sql.Types.INTEGER;
import static java.sql.Types.NUMERIC;
import static java.sql.Types.SMALLINT;
import static java.sql.Types.TIME;
import static java.sql.Types.TIMESTAMP;
import static java.sql.Types.TINYINT;
import static java.sql.Types.VARBINARY;
import static java.sql.Types.VARCHAR;

public class PostgreSQLDialect extends AbstractDialect {

	public PostgreSQLDialect() {
		super();
		ss = new SequenceSupport();
		ss.setQuerySequenceSql("select seqname from sysibm.syssequences");
		ss.setNextValSql("select nextval ('{}')");
		ss.setSelectNextValSql("nextval ('{}')");

		registerColumnType(BOOLEAN, "bool");
		registerColumnType(BIT, "bool");
		registerColumnType(BIGINT, "int8");
		registerColumnType(SMALLINT, "int2");
		registerColumnType(TINYINT, "int2");
		registerColumnType(INTEGER, "int4");
		registerColumnType(CHAR, "char(1)");
		registerColumnType(VARCHAR, "varchar($l)");
		registerColumnType(FLOAT, "float4");
		registerColumnType(DOUBLE, "float8");
		registerColumnType(DATE, "date");
		registerColumnType(TIME, "time");
		registerColumnType(TIMESTAMP, "timestamp");
		registerColumnType(VARBINARY, "bytea");
		registerColumnType(CLOB, "text");
		registerColumnType(BLOB, "oid");
		registerColumnType(NUMERIC, "numeric($p, $s)");
	}

	public String getLimitString(String sql, boolean hasOffset) {
		return new StringBuilder(sql.length() + 20).append(sql)
				.append(hasOffset ? " limit ? offset ?" : " limit ?").toString();
	}
}
