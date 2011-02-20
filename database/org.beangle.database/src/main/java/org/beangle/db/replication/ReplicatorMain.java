/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.replication;

import static org.beangle.db.util.DataSourceUtil.getDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.beangle.db.replication.impl.ReplicatorBuilder;
import org.beangle.db.util.DataSourceUtil;

public class ReplicatorMain {

	public static void main(String[] args) throws Exception {
		final Properties props = new Properties();
		try {
			InputStream is = DataSourceUtil.class.getResourceAsStream("/replication.properties");
			if (null == is) { throw new RuntimeException("cannot find replication.properties"); }
			props.load(is);
		} catch (IOException e) {
			throw new RuntimeException("cannot find database.properties");
		}
		ReplicatorBuilder builder = new ReplicatorBuilder();
		builder.source(props.getProperty("source.dialect"), getDataSource("source"))
				.schema(props.getProperty("source.schema")).tables("PUBLIC.sys_authorities");
		
		builder.target(props.getProperty("target.dialect"), getDataSource("target")).schema(
				props.getProperty("target.schema"));
		
		Replicator replicator = builder.build();
		replicator.start();
		System.out.println("end replicate ..... start sleep");
		Thread.sleep(1000 * 30);
	}

}
