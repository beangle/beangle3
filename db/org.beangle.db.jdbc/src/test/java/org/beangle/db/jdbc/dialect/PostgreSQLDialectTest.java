/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.jdbc.dialect;

import java.sql.Types;

import org.testng.annotations.Test;

@Test
public class PostgreSQLDialectTest {

	public void testGetNumeric() {
		Dialect dialect = new PostgreSQLDialect();
		int scale = 0;
		int precision = 65535;
		int size = 65535;
		System.out.println(dialect.getTypeNames().get(Types.NUMERIC, size, precision, scale));
	}
}
