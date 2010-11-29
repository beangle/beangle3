/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.replication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import org.beangle.db.dialect.Dialect;
import org.beangle.db.replication.impl.DatabaseReplicator;
import org.beangle.db.replication.impl.DefaultTableFilter;
import org.beangle.db.replication.wrappers.DatabaseWrapper;
import org.beangle.db.util.DataSourceUtil;

public class ReplicatorMain {

	public static void main(String[] args) throws Exception {
		//
		// LineNumberReader lr=new LineNumberReader(new
		// FileReader("/home/chaostone/beangle/database/org.beangle.database/src/test/resources/security_old_tables.txt"));
		// List<String > tablenames=CollectionUtil.newArrayList();
		// String oneTable=null;
		// while(null!=(oneTable=lr.readLine())){
		// tablenames.add("EAMS_USST."+oneTable);
		// }
		// lr.close();
		final Properties props = new Properties();
		try {
			InputStream is = DataSourceUtil.class.getResourceAsStream("/replication.properties");
			if (null == is) { throw new RuntimeException("cannot find replication.properties"); }
			props.load(is);
		} catch (IOException e) {
			throw new RuntimeException("cannot find database.properties");
		}
		DatabaseWrapper source = new DatabaseWrapper(props.getProperty("source.schema"));
		source.connect(DataSourceUtil.getDataSource("source"), (Dialect) (Class.forName(props
				.getProperty("source.dialect")).newInstance()));

		DatabaseWrapper target = new DatabaseWrapper(props.getProperty("target.schema"));
		target.connect(DataSourceUtil.getDataSource("target"), (Dialect) (Class.forName(props
				.getProperty("target.dialect")).newInstance()));

		Replicator replicator = new DatabaseReplicator(source, target);

		Set<String> tables = source.getMetadata().getTables().keySet();
		DefaultTableFilter filter = new DefaultTableFilter();
		filter.addInclude("EAMS_USST.CJ_T");
		replicator.addTables(filter.filter(tables));
		// replicator.addTables(tablenames);
		replicator.start();
		System.out.println("end replicate ..... start sleep");
		Thread.sleep(1000 * 30);
	}

}
