/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.replication;

import org.beangle.db.dialect.HSQLDialect;
import org.beangle.db.dialect.OracleDialect;
import org.beangle.db.replication.impl.DatabaseReplicator;
import org.beangle.db.replication.wrappers.DatabaseWrapper;
import org.beangle.db.util.DataSourceUtil;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ReplicatorTest {

	DatabaseWrapper source;

	@SuppressWarnings("unused")
	@BeforeClass
	private void init() {
		source = new DatabaseWrapper();
		source.connect(DataSourceUtil.getDataSource("hsqldb"), new HSQLDialect(), null, "PUBLIC");
	}

	@Test(dataProvider = "tables")
	public void hsqlReplication(String table) {
		DatabaseWrapper target = new DatabaseWrapper();
		target.connect(DataSourceUtil.getDataSource("hsqldb_target"), new HSQLDialect(), null,
				"PUBLIC");
		Replicator replicator = new DatabaseReplicator(source, target);
		replicator.addTable(table);
		replicator.start();
	}

	public void testOracleReplication(String table) {
		DatabaseWrapper target = new DatabaseWrapper();
		target.connect(DataSourceUtil.getDataSource("oracle"), new OracleDialect(), null, "test");
		Replicator replicator = new DatabaseReplicator(source, target);
		replicator.addTable(table);
		replicator.start();
	}

	@SuppressWarnings("unused")
	@DataProvider
	private String[][] tables() {
		return new String[][] { { "xtqx_zy_t" }, { "xtqx_zy_lb_t" }, { "xtqx_yh_t" },
				{ "xtqx_js_t" } };
	}
}
