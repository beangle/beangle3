/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.jdbc.meta;

import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.beangle.db.jdbc.dialect.Dialect;
import org.beangle.db.jdbc.dialect.H2Dialect;
import org.beangle.db.jdbc.ds.PoolingDataSourceFactory;
import org.testng.annotations.Test;

@Test
public class MetadataLoaderTest {

	public void testHsql() throws SQLException {
		DataSource datasource = new PoolingDataSourceFactory("org.h2.Driver",
				"jdbc:h2:/tmp/beangle;AUTO_SERVER=TRUE", "sa", "").getObject();
		Dialect dialect = new H2Dialect();
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
