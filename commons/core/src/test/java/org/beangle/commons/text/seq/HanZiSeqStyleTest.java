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
package org.beangle.commons.text.seq;

import static org.testng.Assert.assertEquals;

import org.beangle.commons.text.seq.HanZiSeqStyle;
import org.testng.annotations.Test;

public class HanZiSeqStyleTest {

  @Test
  public void testBuildText() {
    HanZiSeqStyle style = new HanZiSeqStyle();
    assertEquals("二百一十一", style.build(211));
    assertEquals("二百零一", style.build(201));
    assertEquals("三千零十一", style.build(3011));
  }
}
