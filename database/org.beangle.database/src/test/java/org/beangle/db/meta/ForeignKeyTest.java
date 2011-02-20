/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.meta;

import java.sql.Types;

import org.beangle.db.dialect.Dialect;
import org.beangle.db.dialect.OracleDialect;
import org.testng.annotations.Test;

@Test
public class ForeignKeyTest {

	public void testOracle() {
		Table table = new Table("sys_tableB");
		PrimaryKey pk = new PrimaryKey("pk", new Column("id", Types.BIGINT));
		table.setPrimaryKey(pk);

		Table tableA = new Table("sys_tableA");
		ForeignKey fk = new ForeignKey("fkxyz", new Column("fkid", Types.BIGINT));
		tableA.addForeignKey(fk);
		fk.setReferencedTable(table);
		Dialect dialect = new OracleDialect();
		System.out.println(fk.sqlConstraintString(dialect));
	}

}
