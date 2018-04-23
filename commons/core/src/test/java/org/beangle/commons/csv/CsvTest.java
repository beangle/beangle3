/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.csv;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

@Test
public class CsvTest {

  public void testFormat() {
    CsvFormat.Builder builder = new CsvFormat.Builder();
    builder.separator(CsvConstants.COMMA).separator(CsvConstants.SEMICOLON).delimiter(CsvConstants.QUOTE);

    Csv csv = new Csv(builder.build());
    assertTrue(csv.getFormat().isSeparator(CsvConstants.COMMA));
    assertTrue(csv.getFormat().isSeparator(CsvConstants.SEMICOLON));
    assertEquals(csv.getFormat().getDelimiter(), CsvConstants.QUOTE);
  }

}
