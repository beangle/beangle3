/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.jdbc.dialect;

import java.sql.Types;

import org.beangle.db.jdbc.grammar.LimitGrammar;
import org.beangle.db.jdbc.grammar.LimitGrammarBean;
import org.beangle.db.jdbc.grammar.SequenceGrammar;

public class DB2Dialect extends AbstractDialect {

	public DB2Dialect() {
		super("[8.0]");
	}

	@Override
	protected SequenceGrammar buildSequenceGrammar() {
		SequenceGrammar ss = new SequenceGrammar();
		ss.setQuerySequenceSql("select name as sequence_name,start-1 as current_value,increment,cache from sysibm.syssequences where schema=':schema'");
		ss.setNextValSql("values nextval for :name");
		ss.setDropSql("drop sequence :name restrict");
		ss.setSelectNextValSql("nextval for :name");
		return ss;
	}

	@Override
	protected void registerType() {
		registerType(Types.BOOLEAN, "smallint");
		registerType(Types.BIT, "smallint");
		registerType(Types.DECIMAL, "bigint");
		registerType(Types.BIGINT, "bigint");
		registerType(Types.SMALLINT, "smallint");
		registerType(Types.TINYINT, "smallint");
		registerType(Types.INTEGER, "integer");
		registerType(Types.CHAR, "char(1)");
		registerType(Types.VARCHAR, "varchar($l)");
		registerType(Types.FLOAT, "float");
		registerType(Types.DOUBLE, "double");
		registerType(Types.DATE, "date");
		registerType(Types.TIME, "time");
		registerType(Types.TIMESTAMP, "timestamp");
		registerType(Types.NUMERIC, "numeric($p,$s)");
		registerType(Types.BLOB, "blob($l)");
		registerType(Types.CLOB, "clob($l)");

		registerType(Types.VARBINARY, "varchar($l) for bit data");
		// FIXME correct definition needed!
		registerType(Types.LONGVARCHAR, "varchar($l)");
		registerType(Types.BINARY, "raw");
		registerType(Types.LONGVARBINARY, "raw");
	}

	/**
	 * Render the <tt>rownumber() over ( .... ) as rownumber_,</tt> bit, that
	 * goes in the select list
	 */
	private String getRowNumber(String sql) {
		StringBuilder rownumber = new StringBuilder(50).append("rownumber() over(");
		int orderByIndex = sql.toLowerCase().indexOf("order by");
		if (orderByIndex > 0 && !hasDistinct(sql)) {
			rownumber.append(sql.substring(orderByIndex));
		}
		rownumber.append(") as rownumber_,");
		return rownumber.toString();
	}

	private static boolean hasDistinct(String sql) {
		return sql.toLowerCase().indexOf("select distinct") >= 0;
	}

	@Override
	protected LimitGrammar buildLimitGrammar() {
		return new LimitGrammarBean(null, null, false, false, true) {
			public String limit(String sql, boolean hasOffset) {
				int startOfSelect = sql.toLowerCase().indexOf("select");
				StringBuilder pagingSelect = new StringBuilder(sql.length() + 100)
						.append(sql.substring(0, startOfSelect)) // add the comment
						.append("select * from ( select ") // nest the main query in an
						// outer select
						.append(getRowNumber(sql)); // add the rownnumber bit into the
				// outer query select list

				if (hasDistinct(sql)) {
					// add another (inner) nested select
					pagingSelect.append(" row_.* from ( ")
					// add the main query
							.append(sql.substring(startOfSelect))
							// close off the inner nested select
							.append(" ) as row_");
				} else {
					// add the main query
					pagingSelect.append(sql.substring(startOfSelect + 6));
				}

				pagingSelect.append(" ) as temp_ where rownumber_ ");

				// add the restriction to the outer select
				if (hasOffset) {
					pagingSelect.append("between ?+1 and ?");
				} else {
					pagingSelect.append("<= ?");
				}
				return pagingSelect.toString();
			}
		};
	}

	public String defaultSchema() {
		return null;
	}

}
