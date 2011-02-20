/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.dialect;

import java.sql.Types;

public class OracleDialect extends AbstractDialect {

	public OracleDialect() {
		super();
		ss = new SequenceSupport();
		ss.setQuerySequenceSql("select sequence_name,last_number,increment_by,cache_size from all_sequences where sequence_owner=':schema'");
		ss.setCreateSql("create sequence :name increment by :increment start with :start cache :cache");
		ss.setNextValSql("select :name.nextval from dual");
		ss.setSelectNextValSql(":name.nextval");
		registerCharacterType();
		registerNumericType();
		registerDateTimeType();
		registerLargeObjectType();
	}

	protected void registerCharacterType() {
		registerColumnType(Types.CHAR, "char(1)");
		registerColumnType(Types.VARCHAR, 4000, "varchar2($l)");
		registerColumnType(Types.VARCHAR, "long");
		registerColumnType(Types.LONGVARCHAR, "long");
	}

	protected void registerNumericType() {
		registerColumnType(Types.BOOLEAN, "number(1,0)");
		registerColumnType(Types.BIT, "number(1,0)");
		registerColumnType(Types.BIGINT, "number(19,0)");
		registerColumnType(Types.SMALLINT, "number(5,0)");
		registerColumnType(Types.TINYINT, "number(3,0)");
		registerColumnType(Types.INTEGER, "number(10,0)");

		registerColumnType(Types.FLOAT, "float");
		registerColumnType(Types.DOUBLE, "double precision");
		registerColumnType(Types.NUMERIC, "number($p,$s)");
		registerColumnType(Types.DECIMAL, "number($p,$s)");
	}

	protected void registerDateTimeType() {
		registerColumnType(Types.DATE, "date");
		registerColumnType(Types.TIME, "date");
		registerColumnType(Types.TIMESTAMP, "date");
	}

	protected void registerLargeObjectType() {
		registerColumnType(Types.VARBINARY, 2000, "raw($l)");
		registerColumnType(Types.VARBINARY, "long raw");

		registerColumnType(Types.BLOB, "blob");
		registerColumnType(Types.CLOB, "clob");
	}

	public String getLimitString(String sql, boolean hasOffset) {
		sql = sql.trim();
		boolean isForUpdate = false;
		if (sql.toLowerCase().endsWith(" for update")) {
			sql = sql.substring(0, sql.length() - 11);
			isForUpdate = true;
		}

		StringBuilder pagingSelect = new StringBuilder(sql.length() + 100);
		if (hasOffset) {
			pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		} else {
			pagingSelect.append("select * from ( ");
		}
		pagingSelect.append(sql);
		if (hasOffset) {
			pagingSelect.append(" ) row_ where rownum <= ?) where rownum_ > ?");
		} else {
			pagingSelect.append(" ) where rownum <= ?");
		}

		if (isForUpdate) {
			pagingSelect.append(" for update");
		}

		return pagingSelect.toString();
	}

}
