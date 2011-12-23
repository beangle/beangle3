/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.util.csv;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import java.io.StringReader;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test
public class CsvReaderTest {

	CsvReader reader = null;

	@BeforeClass
	public void builderReader() {
		StringBuilder sb = new StringBuilder();
		sb.append("a,b,c").append("\n"); // standard case
		sb.append("a,\"b,b,b\",c").append("\n"); // quoted elements
		sb.append(",,").append("\n"); // empty elements
		sb.append("a,\"PO Box 123,\nKippax,ACT. 2615.\nAustralia\",d.\n");
		// Test quoted quote chars
		sb.append("\"Glen \"\"The Man\"\" Smith\",Athlete,Developer\n");
		// """""","test" representing: "",test
		sb.append("\"\"\"\"\"\",\"test\"\n");
		sb.append("\"a\nb\",b,\"\nd\",e\n");
		reader = new CsvReader(new StringReader(sb.toString()));
	}

	/**
	 * Tests iterating over a reader.
	 */
	@Test
	public void testRead() {
		// test normal case
		String[] nextLine = reader.readNext();
		assertEquals("a", nextLine[0]);
		assertEquals("b", nextLine[1]);
		assertEquals("c", nextLine[2]);

		// test quoted commas
		nextLine = reader.readNext();
		assertEquals("a", nextLine[0]);
		assertEquals("b,b,b", nextLine[1]);
		assertEquals("c", nextLine[2]);

		// test empty elements
		nextLine = reader.readNext();
		assertEquals(3, nextLine.length);

		// test multiline quoted
		nextLine = reader.readNext();
		assertEquals(3, nextLine.length);

		// test quoted quote chars
		nextLine = reader.readNext();
		assertEquals("Glen \"The Man\" Smith", nextLine[0]);

		nextLine = reader.readNext();
		assertEquals("\"\"", nextLine[0]); // check the tricky situation
		assertEquals("test", nextLine[1]); // make sure we didn't ruin the next
		// field..

		nextLine = reader.readNext();
		assertEquals(4, nextLine.length);

		// test end of stream
		assertNull(reader.readNext());

	}

}
