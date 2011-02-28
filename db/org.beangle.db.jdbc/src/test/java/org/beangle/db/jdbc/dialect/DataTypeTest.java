/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.jdbc.dialect;

import static java.sql.Types.BIGINT;
import static java.sql.Types.BINARY;
import static java.sql.Types.BIT;
import static java.sql.Types.BLOB;
import static java.sql.Types.BOOLEAN;
import static java.sql.Types.CHAR;
import static java.sql.Types.CLOB;
import static java.sql.Types.DATE;
import static java.sql.Types.DECIMAL;
import static java.sql.Types.DOUBLE;
import static java.sql.Types.FLOAT;
import static java.sql.Types.INTEGER;
import static java.sql.Types.LONGVARBINARY;
import static java.sql.Types.LONGVARCHAR;
import static java.sql.Types.NUMERIC;
import static java.sql.Types.SMALLINT;
import static java.sql.Types.TIME;
import static java.sql.Types.TIMESTAMP;
import static java.sql.Types.TINYINT;
import static java.sql.Types.VARBINARY;
import static java.sql.Types.VARCHAR;

import org.springframework.util.Assert;
import org.testng.annotations.Test;

@Test
public class DataTypeTest {

	private static final int[] types = new int[] { BOOLEAN, BIT, CHAR, INTEGER, SMALLINT, TINYINT, BIGINT,
			FLOAT, DOUBLE, DECIMAL, NUMERIC, DATE, TIME, TIMESTAMP, VARCHAR, LONGVARCHAR, BINARY, VARBINARY,
			LONGVARBINARY, BLOB, CLOB };

	// test open source db -----------------------------------------
	public void testHSQL18() {
		Dialect dialect = new HSQL18Dialect();
		testGetTypeName(dialect);
	}

	public void testHSQL2() {
		Dialect dialect = new HSQL2Dialect();
		testGetTypeName(dialect);
	}

	public void testH2() {
		Dialect dialect = new H2Dialect();
		testGetTypeName(dialect);
	}

	public void testMySQL() {
		Dialect dialect = new MySQLDialect();
		testGetTypeName(dialect);
	}

	public void testPostgreSQL() {
		Dialect dialect = new PostgreSQLDialect();
		testGetTypeName(dialect);
	}

	public void testDerby() {
		Dialect dialect = new DerbyDialect();
		testGetTypeName(dialect);
	}

	// test commacial db--------------------------------------------
	public void testOracle() {
		Dialect dialect = new OracleDialect();
		testGetTypeName(dialect);
	}

	public void testDb2() {
		Dialect dialect = new DB2Dialect();
		testGetTypeName(dialect);
	}

	public void testSQLServer2005() {
		Dialect dialect = new SQLServer2005Dialect();
		testGetTypeName(dialect);
	}

	private void testGetTypeName(Dialect dialect) {
		for (int type : types) {
			Assert.notNull(dialect.getTypeNames().get(type));
		}
	}
}
