/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.replication;

import static org.beangle.db.jdbc.util.DataSourceUtil.getDataSource;

import org.beangle.db.jdbc.dialect.Dialects;
import org.springframework.util.Assert;
import org.testng.annotations.Test;

@Test
public class ReplicatorBuilderTest {
	public void testBuilder() {
		ReplicatorBuilder builder = new ReplicatorBuilder();
		builder.source(Dialects.HSQL18, getDataSource("hsqldb")).schema("PUBLIC").tables("*")
				.exclude("public.dual").indexes("*").contraints("*").sequences("*");
		builder.target(Dialects.H2, getDataSource("h1")).schema("PUBLIC");
		Replicator replicator = builder.build();
		Assert.notNull(replicator);
	}
}
