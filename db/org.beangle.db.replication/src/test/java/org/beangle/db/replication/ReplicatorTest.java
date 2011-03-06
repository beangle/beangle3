/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.replication;

import static org.beangle.db.jdbc.util.DataSourceUtil.getDataSource;

import org.beangle.db.jdbc.dialect.Dialects;
import org.testng.annotations.Test;

@Test
public class ReplicatorTest {

	private void testHsqlToH2() {
		ReplicatorBuilder builder = new ReplicatorBuilder();
		builder.source(Dialects.HSQL18, getDataSource("exprdb")).tables("*").exclude("public.dual")
				.indexes("*").contraints("*").sequences("*").lowercase();
		
		builder.target(Dialects.MySQL, getDataSource("mysql"));
		Replicator replicator = builder.build();
		replicator.start();
	}
	
	public void test2() {
		ReplicatorBuilder builder = new ReplicatorBuilder();
		builder.source(Dialects.Oracle, getDataSource("shfc_oracle"))
		.tables("*").schema("IDC_U_JW_KF")
				.indexes("*").contraints("*").sequences("*");
		builder.target(Dialects.H2, getDataSource("shfc_h2"));
		Replicator replicator = builder.build();
		replicator.start();
	}
}
