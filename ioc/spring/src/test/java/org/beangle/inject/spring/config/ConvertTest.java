/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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
package org.beangle.inject.spring.config;

import java.math.BigDecimal;

import org.springframework.core.convert.support.DefaultConversionService;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class ConvertTest {

  public void testSpring() {
    DefaultConversionService conversion = new DefaultConversionService();
    Assert.assertEquals(conversion.convert("4.5", Number.class), new BigDecimal("4.5"));
    
    Assert.assertEquals((boolean)conversion.convert("true", boolean.class), true);
  }
}
