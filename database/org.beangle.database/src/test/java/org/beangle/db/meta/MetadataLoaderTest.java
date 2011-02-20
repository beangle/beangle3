/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.meta;

import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.beangle.db.dialect.Dialect;
import org.beangle.db.dialect.HSQLDialect;
import org.beangle.db.util.DataSourceUtil;
import org.testng.annotations.Test;

@Test
public class MetadataLoaderTest {

	public void testHsql() throws SQLException {
		DataSource datasource = DataSourceUtil.getDataSource("source");
		Dialect dialect = new HSQLDialect();
		Database database = new Database(datasource.getConnection().getMetaData(), dialect, null, "PUBLIC");
		database.loadTables(true);
		Map<String, Table> tables = database.getTables();
		for (Table table : tables.values()) {
			System.out.println(table.sqlCreateString(dialect));
			for (ForeignKey fk1 : table.getForeignKeys().values()) {
				System.out.println(fk1.sqlConstraintString(dialect));
			}
		}
	}
}
