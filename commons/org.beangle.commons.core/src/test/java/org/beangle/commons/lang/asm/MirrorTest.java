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
package org.beangle.commons.lang.asm;

import static org.testng.Assert.assertEquals;

import org.beangle.commons.lang.testbean.TestBean;
import org.testng.annotations.Test;

@Test
public class MirrorTest {

  public void testProperty(String[] args) {
    Mirror access = Mirror.get(TestBean.class);
    TestBean someObject = new TestBean();
    Object value;

    value = access.invoke(someObject, "getName");
    assertEquals(null, value);
    value = access.invoke(someObject, "setName", "sweet");
    assertEquals(null, value);
    value = access.invoke(someObject, "getName");
    assertEquals("sweet", value);
    value = access.invoke(someObject, "setName", (Object) null);
    assertEquals(null, value);
    value = access.invoke(someObject, "getName");
    assertEquals(null, value);

    value = access.invoke(someObject, "setId", 1);
    assertEquals(null, value);
    value = access.invoke(someObject, "getId");
    assertEquals(1, value);

    value = access.invoke(someObject, "getIntValue");
    assertEquals(0, value);

    value = access.invoke(someObject, "getIntValue");
    assertEquals(0, value);
    value = access.invoke(someObject, "setIntValue", 1234);
    assertEquals(null, value);
    value = access.invoke(someObject, "getIntValue");
    assertEquals(1234, value);

  }

}
