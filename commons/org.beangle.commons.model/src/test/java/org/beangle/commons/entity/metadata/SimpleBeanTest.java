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
package org.beangle.commons.entity.metadata;

import java.lang.reflect.Method;

import org.beangle.commons.entity.pojo.NumberIdObject;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class SimpleBeanTest {

  public void testGetId() throws NoSuchMethodException, SecurityException {
    int getIdCount = 0;
    for (Method m : SimpleBean.class.getMethods()) {
      if (m.getName().equals("getId")) getIdCount++;
    }
    Assert.assertEquals(getIdCount, 3);
    Method m = SimpleBean.class.getMethod("getId", new Class<?>[] {});
    Assert.assertEquals(m.getReturnType(), Long.class);

    SimpleBean bean = new SimpleBean();
    @SuppressWarnings({ "unchecked", "rawtypes" })
    NumberIdObject<Number> numberbean = (NumberIdObject) bean;
    try {
      numberbean.setId(4f);
    } catch (ClassCastException e) {
      Assert.assertEquals(null, bean.getId());
    }
  }
}
