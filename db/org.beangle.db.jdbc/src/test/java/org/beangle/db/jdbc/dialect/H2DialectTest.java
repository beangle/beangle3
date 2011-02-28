/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.jdbc.dialect;

import javax.sql.DataSource;

import org.beangle.db.jdbc.ds.PoolingDataSourceFactory;
import org.beangle.db.jdbc.meta.Database;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test
public class H2DialectTest extends DialectTestCase {
	@BeforeClass
	public void setUp() throws Exception {
		DataSource ds = new PoolingDataSourceFactory("org.h2.Driver",
				"jdbc:h2:/tmp/beangle;AUTO_SERVER=TRUE", "sa", "").getObject();
		database = new Database(ds.getConnection().getMetaData(), new H2Dialect(), null, "PUBLIC");
		database.loadTables(false);
		database.loadSequences();
	}

	public void testlistMetadata() {
		listTableAndSequences();
	}
}
