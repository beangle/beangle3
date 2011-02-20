/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.dialect;

import org.beangle.db.meta.Database;
import org.beangle.db.util.DataSourceUtil;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test
public class HSQLDialectTest extends DialectTestCase {
	@BeforeClass
	public void setUp() throws Exception {
		// meta = new
		// DatabaseMetadata(DataSourceUtil.getDataSource("oracle").getConnection(),
		// new OracleDialect());
		// meta.loadAllMetadata("EAMS_NEW",null,false);
		database = new Database(DataSourceUtil.getDataSource("hsqldb").getConnection().getMetaData(),
				new HSQLDialect(), null, "PUBLIC");
		database.loadTables(false);
		database.loadSequences();
	}

	public void testlistMetadata() {
		listTableAndSequences();
//		Map<String, Table> tables = database.getTables();
//		for (Map.Entry<String, Table> entry : tables.entrySet()) {
//			Table m = entry.getValue();
//			log.info(m.sqlCreateString(database.getDialect()));
//		}
	}
}
