/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.jdbc.meta;

import java.sql.Types;

import org.beangle.db.jdbc.dialect.H2Dialect;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test
public class TableTest {

	Table table;

	@BeforeClass
	public void setUp() {
		table = new Table("test", "user");
		Column column = new Column("name", Types.VARCHAR);
		column.setComment("login name");
		table.addColumn(column);
		Column pkColumn = new Column("id", Types.BIGINT);
		PrimaryKey pk = new PrimaryKey("pk", pkColumn);
		table.addColumn(pkColumn);
		table.setPrimaryKey(pk);
	}

	public void testH2Create() {
		String createSql = table.sqlCreateString(new H2Dialect());
		System.out.println(createSql);
	}
}
