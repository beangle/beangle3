/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.csv;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

@Test
public class CsvTest {

	public void testFormat() {
		CsvFormat.Builder builder = new CsvFormat.Builder();
		builder.separator(CsvConstants.COMMA).separator(CsvConstants.SEMICOLON)
				.delimiter(CsvConstants.QUOTE);

		Csv csv = new Csv(builder.build());
		assertTrue(csv.getFormat().isSeparator(CsvConstants.COMMA));
		assertTrue(csv.getFormat().isSeparator(CsvConstants.SEMICOLON));
		assertEquals(csv.getFormat().getDelimiter(), CsvConstants.QUOTE);
	}

}
