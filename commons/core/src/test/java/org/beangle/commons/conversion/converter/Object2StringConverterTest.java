/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.conversion.converter;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import org.testng.annotations.Test;

/**
 * @author chaostone
 * @since 3.3.7
 */
@Test
public class Object2StringConverterTest {

  public void testConvert() {
    Object2StringConverter converter = new Object2StringConverter();
    assertNull(converter.apply(null), null);
    assertEquals(converter.apply("abc"), "abc");
    assertEquals(converter.apply(new Object[] { 1, 3, "abc" }), "1,3,abc");
    assertEquals(converter.apply(new Object[] { 1, }), "1");
  }
}
