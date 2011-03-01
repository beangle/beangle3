/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.replication;

import javax.sql.DataSource;

import org.beangle.db.replication.ReplicatorBuilder.DatabaseSource;
import org.mockito.Mockito;
import org.testng.annotations.Test;

@Test
public class ReplicatorBuilderTest {

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testBuilder() {
		DatabaseSource source = new ReplicatorBuilder.DatabaseSource(Mockito.mock(DataSource.class), null);
		source.filterConstraints();
	}
}
