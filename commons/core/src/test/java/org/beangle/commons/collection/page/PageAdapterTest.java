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
package org.beangle.commons.collection.page;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

/**
 * @author zhouqi
 */
public class PageAdapterTest {
  @Test
  public void testD() throws Exception {
    List<String> datas = new ArrayList<String>(26);
    for (int i = 0; i < 26; i++) {
      datas.add(String.valueOf(i));
    }
    Page<String> page = new PagedList<String>(datas, 20);
    assertNotNull(page.iterator());
    assertEquals(page.iterator().next(), "0");
    page.next();
    assertEquals(page.iterator().next(), "20");
    page.moveTo(2);
    assertEquals(page.iterator().next(), "20");
    page.previous();
    assertEquals(page.iterator().next(), "0");
  }
}
