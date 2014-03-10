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
package org.beangle.commons.dao.query.limit;

import static org.testng.Assert.assertNotNull;

import java.util.Iterator;

import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.dao.query.LimitQuery;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.testng.annotations.Test;

@Test
public class QueryPageTest {

  public void testMove() throws Exception {
    OqlBuilder<String> query = OqlBuilder.from(String.class, "dd");
    query.limit(new PageLimit(1, 2));
    MockQueryPage page = new MockQueryPage((LimitQuery<String>) query.build());
    for (Iterator<String> iterator = page.iterator(); iterator.hasNext();) {
      String data = iterator.next();
      assertNotNull(data);
    }
  }
}
