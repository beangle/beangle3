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
import org.beangle.db.jdbc.grammar.TableGrammar;
import org.beangle.db.jdbc.grammar.TableGrammarBean;

public class PostgreSQLDialect extends AbstractDialect {

	public PostgreSQLDialect() {
		super("[8.4)");
	}

	@Override
	protected SequenceGrammar buildSequenceGrammar() {
		SequenceGrammar ss = new SequenceGrammar();
		ss.setQuerySequenceSql("select relname as sequence_name from pg_class where relkind='S'");
		ss.setNextValSql("select nextval (':name')");
		ss.setSelectNextValSql("nextval (':name')");
		return ss;
	}

	@Override
	protected void registerType() {
		registerType(CHAR, "char(1)");
		registerType(VARCHAR, "varchar($l)");
		registerType(LONGVARCHAR, "text");

		registerType(BOOLEAN, "boolean");
		registerType(BIT, "bit");
		registerType(BIGINT, "int8");
		registerType(SMALLINT, "int2");
		registerType(TINYINT, "int2");
		registerType(INTEGER, "int4");
		registerType(FLOAT, "float4");
		registerType(DOUBLE, "float8");
		registerType(DECIMAL, "numeric($p, $s)");
		registerType(NUMERIC, "numeric($p, $s)");
		registerType(NUMERIC, 1000, "numeric($p, $s)");
		registerType(NUMERIC, Integer.MAX_VALUE, "numeric(1000, $s)");

		registerType(DATE, "date");
		registerType(TIME, "time");
		registerType(TIMESTAMP, "timestamp");

		registerType(VARBINARY, "bytea");
		registerType(BINARY, "bytea");
		registerType(LONGVARBINARY, "bytea");
		registerType(CLOB, "text");
		registerType(BLOB, "oid");
	}

	@Override
	protected LimitGrammar buildLimitGrammar() {
		return new LimitGrammarBean("{} limit ?", "{} limit ? offset ?", true, false, false);
	}

	@Override
	protected TableGrammar buildTableGrammar() {
		TableGrammarBean bean = new TableGrammarBean();
		bean.setDrop("drop table {} cascade");
		return bean;
	}

	@Override
	public String defaultSchema() {
		return "public";
	}

	public boolean bindLimitParametersInReverseOrder() {
		return true;
	}
}
