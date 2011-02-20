/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.replication;

import static org.beangle.db.util.DataSourceUtil.getDataSource;

import org.beangle.db.dialect.Dialects;
import org.testng.annotations.Test;

@Test
public class ReplicatorTest {

	public void testHsqlToH2() {
		ReplicatorBuilder builder = new ReplicatorBuilder();
		builder.source(Dialects.HSQL, getDataSource("hsqldb")).schema("PUBLIC").tables("*")
				.exclude("public.dual").indexes("*").contraints("*").sequences("*");
		builder.target(Dialects.H2, getDataSource("h1")).schema("PUBLIC");
		Replicator replicator = builder.build();
		replicator.start();
	}
}
