/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.jdbc.dialect;

import static java.sql.Types.BIGINT;
import static java.sql.Types.BINARY;
import static java.sql.Types.BIT;
import static java.sql.Types.BLOB;
import static java.sql.Types.BOOLEAN;
import static java.sql.Types.CHAR;
import static java.sql.Types.CLOB;
import static java.sql.Types.DATE;
import static java.sql.Types.DECIMAL;
import static java.sql.Types.DOUBLE;
import static java.sql.Types.FLOAT;
import static java.sql.Types.INTEGER;
import static java.sql.Types.LONGVARBINARY;
import static java.sql.Types.LONGVARCHAR;
import static java.sql.Types.NUMERIC;
import static java.sql.Types.SMALLINT;
import static java.sql.Types.TIME;
import static java.sql.Types.TIMESTAMP;
import static java.sql.Types.TINYINT;
import static java.sql.Types.VARBINARY;
import static java.sql.Types.VARCHAR;

import org.beangle.db.jdbc.grammar.LimitGrammar;
import org.beangle.db.jdbc.grammar.LimitGrammarBean;
import org.beangle.db.jdbc.grammar.SequenceGrammar;

public class OracleDialect extends AbstractDialect {

	public OracleDialect() {
		super("[10.1)");
	}

	@Override
	protected SequenceGrammar buildSequenceGrammar() {
		SequenceGrammar ss = new SequenceGrammar();
		ss.setQuerySequenceSql("select sequence_name,last_number,increment_by,cache_size from all_sequences where sequence_owner=':schema'");
		ss.setCreateSql("create sequence :name increment by :increment start with :start cache :cache");
		ss.setNextValSql("select :name.nextval from dual");
		ss.setSelectNextValSql(":name.nextval");
		return ss;
	}

	@Override
	protected void registerType() {
		registerType(CHAR, "char(1)");
		registerType(VARCHAR, 4000, "varchar2($l)");
		registerType(VARCHAR, "long");
		registerType(LONGVARCHAR, "long");

		registerType(BOOLEAN, "number(1,0)");
		registerType(BIT, "number(1,0)");
		registerType(BIGINT, "number(19,0)");
		registerType(SMALLINT, "number(5,0)");
		registerType(TINYINT, "number(3,0)");
		registerType(INTEGER, "number(10,0)");

		registerType(FLOAT, "float");
		registerType(DOUBLE, "double precision");
		registerType(DECIMAL, "number($p,$s)");
		registerType(NUMERIC, "number($p,$s)");
		registerType(NUMERIC, 38, "number($p,$s)");
		registerType(NUMERIC, Integer.MAX_VALUE, "number(38,$s)");

		registerType(DATE, "date");
		registerType(TIME, "date");
		registerType(TIMESTAMP, "date");

		registerType(VARBINARY, 2000, "raw($l)");
		registerType(VARBINARY, "long raw");
		registerType(LONGVARCHAR, "long");
		registerType(LONGVARBINARY, "long raw");
		registerType(BLOB, "blob");
		registerType(CLOB, "clob");
		registerType(BINARY, "raw");
	}

	@Override
	protected LimitGrammar buildLimitGrammar() {
		return new LimitGrammarBean(null, null, true, false, true) {
			public String limit(String sql, boolean hasOffset) {
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
		};
	}

	public boolean bindLimitParametersInReverseOrder() {
		return true;
	}
}
