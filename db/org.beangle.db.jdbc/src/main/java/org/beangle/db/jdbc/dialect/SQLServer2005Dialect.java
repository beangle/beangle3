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

public class SQLServer2005Dialect extends AbstractDialect {

	private static final String SELECT = "select";
	private static final String FROM = "from";
	private static final String DISTINCT = "distinct";

	public SQLServer2005Dialect() {
		super("[2005,2008)");
	}

	@Override
	protected SequenceGrammar buildSequenceGrammar() {
		return null;
	}

	@Override
	protected void registerType() {
		registerType(CHAR, "char(1)");
		registerType(VARCHAR, "varchar($l)");
		registerType(LONGVARCHAR, "text");

		registerType(BIT, "tinyint");
		registerType(BOOLEAN, "tinyint");
		registerType(BIGINT, "numeric(19,0)");
		registerType(SMALLINT, "smallint");
		registerType(TINYINT, "tinyint");
		registerType(INTEGER, "int");
		registerType(FLOAT, "float");
		registerType(DECIMAL, "double precision");
		registerType(DOUBLE, "double precision");
		registerType(NUMERIC, "numeric($p,$s)");

		registerType(DATE, "datetime");
		registerType(TIME, "datetime");
		registerType(TIMESTAMP, "datetime");

		registerType(BINARY, "binary");
		registerType(VARBINARY, "varbinary($l)");
		registerType(LONGVARBINARY, "varbinary($l)");
		registerType(BLOB, "image");
		registerType(CLOB, "text");
	}

	@Override
	protected LimitGrammar buildLimitGrammar() {
		return new LimitGrammarBean(null, null, false, false, true) {

			public String limit(String querySqlString, boolean hasOffset) {
				StringBuilder sb = new StringBuilder(querySqlString.trim().toLowerCase());

				int orderByIndex = sb.indexOf("order by");
				CharSequence orderby = orderByIndex > 0 ? sb.subSequence(orderByIndex, sb.length())
						: "ORDER BY CURRENT_TIMESTAMP";

				// Delete the order by clause at the end of the query
				if (orderByIndex > 0) {
					sb.delete(orderByIndex, orderByIndex + orderby.length());
				}

				// HHH-5715 bug fix
				replaceDistinctWithGroupBy(sb);

				insertRowNumberFunction(sb, orderby);

				// Wrap the query within a with statement:
				sb.insert(0, "WITH query AS (").append(") SELECT * FROM query ");
				sb.append("WHERE __hibernate_row_nr__ BETWEEN ? AND ?");

				return sb.toString();
			}
		};
	}
	protected static void replaceDistinctWithGroupBy(StringBuilder sql) {
		int distinctIndex = sql.indexOf(DISTINCT);
		if (distinctIndex > 0) {
			sql.delete(distinctIndex, distinctIndex + DISTINCT.length() + 1);
			sql.append(" group by").append(getSelectFieldsWithoutAliases(sql));
		}
	}

	protected static void insertRowNumberFunction(StringBuilder sql, CharSequence orderby) {
		// Find the end of the select statement
		int selectEndIndex = sql.indexOf(SELECT) + SELECT.length();

		// Insert after the select statement the row_number() function:
		sql.insert(selectEndIndex, " ROW_NUMBER() OVER (" + orderby + ") as __hibernate_row_nr__,");
	}

	protected static CharSequence getSelectFieldsWithoutAliases(StringBuilder sql) {
		String select = sql.substring(sql.indexOf(SELECT) + SELECT.length(), sql.indexOf(FROM));
		// Strip the as clauses
		return stripAliases(select);
	}

	protected static String stripAliases(String str) {
		return str.replaceAll("\\sas[^,]+(,?)", "$1");
	}
}
