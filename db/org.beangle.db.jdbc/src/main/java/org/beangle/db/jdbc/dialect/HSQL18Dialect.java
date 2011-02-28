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

public class HSQL18Dialect extends AbstractDialect {

	public HSQL18Dialect() {
		super("(,1.8)");
	}

	protected SequenceGrammar buildSequenceGrammar() {
		SequenceGrammar ss = new SequenceGrammar();
		ss.setQuerySequenceSql("select sequence_name,start_with as current_value,increment from information_schema.system_sequences where sequence_schema=':schema'");
		ss.setNextValSql("call next value for :name");
		ss.setSelectNextValSql("next value for :name");
		ss.setCreateSql("create sequence :name start with :start increment by :increment");
		ss.setDropSql("drop sequence if exists :name");
		return ss;
	}

	protected void registerType() {
		registerType(CHAR, "char(1)");
		registerType(VARCHAR, "varchar($l)");

		registerType(BOOLEAN, "boolean");
		registerType(BIT, "bit");
		registerType(INTEGER, "integer");
		registerType(SMALLINT, "smallint");
		registerType(TINYINT, "tinyint");
		registerType(BIGINT, "bigint");
		registerType(DECIMAL, "decimal");
		registerType(DOUBLE, "double");
		registerType(FLOAT, "float");
		registerType(NUMERIC, "numeric");

		registerType(DATE, "date");
		registerType(TIME, "time");
		registerType(TIMESTAMP, "timestamp");

		registerType(BINARY, "binary");
		registerType(VARBINARY, "varbinary($l)");
		registerType(LONGVARBINARY, "longvarbinary");
		registerType(LONGVARCHAR, "longvarchar");
		// HSQL has no Blob/Clob support .... but just put these here for now!
		registerType(BLOB, "longvarbinary");
		registerType(CLOB, "longvarchar");
	}

	@Override
	protected TableGrammar buildTableGrammar() {
		TableGrammarBean bean = new TableGrammarBean();
		bean.setColumnComent(" comment '{}'");
		return bean;
	}

	@Override
	protected LimitGrammar buildLimitGrammar() {
		return new LimitGrammarBean(null, null, false, true, false) {
			@Override
			public String limit(String sql, boolean hasOffset) {
				return new StringBuilder(sql.length() + 10).append(sql)
						.insert(sql.toLowerCase().indexOf("select") + 6, hasOffset ? " limit ? ?" : " top ?")
						.toString();
			}
		};
	}

	@Override
	public String defaultSchema() {
		return "PUBLIC";
	}

}
