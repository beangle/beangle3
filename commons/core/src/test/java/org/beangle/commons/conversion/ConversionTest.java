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
package org.beangle.commons.conversion;

import org.beangle.commons.conversion.impl.DefaultConversion;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class ConversionTest {

  public void testConvert() {
    DefaultConversion con = new DefaultConversion();
    Assert.assertEquals(con.convert(2.5f, Integer.class), Integer.valueOf(2));
    Assert.assertEquals(con.convert("", Boolean.class),null);
    Assert.assertEquals((boolean)con.convert(null, boolean.class),false);
  }

  public void testConvertArray() {
    DefaultConversion con = new DefaultConversion();
    Assert.assertEquals(con.convert(new String[] { "2", "3" }, Integer[].class), new Integer[] { 2, 3 });
  }

  public void testConvertPrimitive() {
    DefaultConversion con = new DefaultConversion();
    Assert.assertEquals((int) con.convert("2", int.class), 2);

    Assert.assertEquals(con.convert(3, Integer.class), Integer.valueOf(3));
  }

  public void testConvertPrimitiveArray() {
    DefaultConversion con = new DefaultConversion();
    Assert.assertEquals((float[]) con.convert(new String[] { "2", "3.4" }, float[].class), new float[] { 2f,
        3.4f });
  }
}
