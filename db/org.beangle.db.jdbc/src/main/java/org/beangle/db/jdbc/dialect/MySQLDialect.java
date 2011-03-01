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

import org.beangle.commons.lang.StrUtils;
import org.beangle.db.jdbc.grammar.LimitGrammar;
import org.beangle.db.jdbc.grammar.LimitGrammarBean;
import org.beangle.db.jdbc.grammar.SequenceGrammar;
import org.beangle.db.jdbc.grammar.TableGrammar;
import org.beangle.db.jdbc.grammar.TableGrammarBean;

public class MySQLDialect extends AbstractDialect {

	public MySQLDialect() {
		super("[5.0,)");
		registerKeywords("index", "explain");
		caseSensitive=true;
	}

	@Override
	protected SequenceGrammar buildSequenceGrammar() {
		return null;
	}

	@Override
	protected void registerType() {
		registerType(CHAR, "char(1)");
		registerType(VARCHAR, 255, "varchar($l)");
		registerType(VARCHAR, 65535, "varchar($l)");
		registerType(VARCHAR, "longtext");
		registerType(LONGVARCHAR, "longtext");

		registerType(BOOLEAN, "bit");
		registerType(BIT, "bit");
		registerType(BIGINT, "bigint");
		registerType(SMALLINT, "smallint");
		registerType(TINYINT, "tinyint");
		registerType(INTEGER, "integer");
		
		registerType(FLOAT, "float");
		registerType(DOUBLE, "double precision");
		
		registerType(DECIMAL, "decimal($p,$s)");
		registerType(NUMERIC, 65, "decimal($p, $s)");
		registerType(NUMERIC, Integer.MAX_VALUE, "decimal(65, $s)");
		registerType(NUMERIC, "decimal($p,$s)");

		registerType(DATE, "date");
		registerType(TIME, "time");
		registerType(TIMESTAMP, "datetime");

		registerType(BINARY, "blob");
		registerType(VARBINARY, "longblob");
		registerType(VARBINARY, 16777215, "mediumblob");
		registerType(VARBINARY, 65535, "blob");
		registerType(VARBINARY, 255, "tinyblob");
		registerType(LONGVARBINARY, "longblob");
		registerType(LONGVARBINARY, 16777215, "mediumblob");
		
		registerType(BLOB, "longblob");
		registerType(CLOB, "longtext");
	}

	@Override
	protected LimitGrammar buildLimitGrammar() {
		return new LimitGrammarBean("{} limit ?", "{} limit ?, ?", false, false, false);
	}

	public String getAddForeignKeyConstraintString(String constraintName, String[] foreignKey,
			String referencedTable, String[] primaryKey, boolean referencesPrimaryKey) {
		String cols = StrUtils.join(foreignKey, ", ");
		return new StringBuffer(30).append(" add index ").append(constraintName).append(" (").append(cols)
				.append("), add constraint ").append(constraintName).append(" foreign key (").append(cols)
				.append(") references ").append(referencedTable).append(" (")
				.append(StrUtils.join(primaryKey, ", ")).append(')').toString();
	}

	@Override
	protected TableGrammar buildTableGrammar() {
		TableGrammarBean bean = new TableGrammarBean();
		bean.setColumnComent(" comment '{}'");
		bean.setComment(" comment '{}'");
		return bean;
	}

}
