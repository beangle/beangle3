/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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
package org.beangle.commons.lang.conversion.converter;

import org.beangle.commons.lang.conversion.Conversion;
import org.beangle.commons.lang.conversion.impl.DefaultConversion;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class DefaultConverterTest {

  public void testConvertNull() {
    Conversion conversion = DefaultConversion.Instance;
    Assert.assertNull(conversion.convert("", Long.class));
    Assert.assertNull(conversion.convert((String) null, Long.class));
    Assert.assertNull(conversion.convert("abc", Long.class));
    Assert.assertEquals((Object) conversion.convert("1", Long.class), (Object) 1L);
    Assert.assertEquals((Object) conversion.convert(1L, Long.class), (Object) 1L);
  }
}
