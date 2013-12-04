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
package org.beangle.commons.bean;

import static org.testng.Assert.assertEquals;

import org.beangle.commons.lang.Assert;
import org.beangle.commons.lang.testbean.TestBean;
import org.testng.annotations.Test;

@Test
public class PropertyUtilsTest {

  public void testSet() {
    TestBean bean = new TestBean();
    PropertyUtils.setProperty(bean, "intValue", 2);
    assertEquals(bean.getIntValue(), 2);

    PropertyUtils.setProperty(bean, "nested.datas(key)", "value");
    assertEquals(PropertyUtils.getProperty(bean, "nested.datas(key)"), "value");

    PropertyUtils.setProperty(bean, "nested.id", 4L);
    assertEquals(PropertyUtils.getProperty(bean, "nested.id"), 4L);
  }

  public static void main(String[] args) {
    TestBean bean = new TestBean();
    PropertyUtils.setProperty(bean, "nested.datas(key)", "value");
    Assert.isTrue("value".equals(PropertyUtils.getProperty(bean, "nested.datas(key)")));

    PropertyUtils.setProperty(bean, "nested.id", 4L);
    Assert.isTrue(PropertyUtils.getProperty(bean, "nested.id").equals(4L));
  }
}
