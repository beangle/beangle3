/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.dialect;

import java.util.Map;

import org.beangle.db.meta.DatabaseMetadata;
import org.beangle.db.meta.TableMetadata;
import org.beangle.db.util.DataSourceUtil;
import org.testng.annotations.BeforeClass;

public class HSQLDialectTest extends DialectTestCase {
	@BeforeClass
	public void setUp() throws Exception {
		// meta = new
		// DatabaseMetadata(DataSourceUtil.getDataSource("oracle").getConnection(),
		// new OracleDialect());
		// meta.loadAllMetadata("EAMS_NEW",null,false);
		meta = new DatabaseMetadata(DataSourceUtil.getDataSource("hsqldb").getConnection(),
				new HSQLDialect());
		meta.loadAllMetadata(null, null, false);
	}

	public void testlistMetadata() {
		listMetadata();
		Map<String, TableMetadata> tables = meta.getTables();
		for (Map.Entry<String, TableMetadata> entry : tables.entrySet()) {
			TableMetadata m = entry.getValue();
			log.info(m.sqlCreateString(meta.getDialect()));
		}
	}
}
