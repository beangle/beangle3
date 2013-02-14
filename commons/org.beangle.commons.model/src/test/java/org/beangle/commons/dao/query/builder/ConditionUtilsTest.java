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
package org.beangle.commons.dao.query.builder;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test
public class ConditionUtilsTest {

  @BeforeClass
  protected void setUp() throws Exception {

  }

  @Test
  public void testGetParamMap() throws Exception {
    List<Condition> conditions = CollectUtils.newArrayList();
    conditions.add(new Condition("std.id=:std_id", 1L));
    Map<String, Object> params = ConditionUtils.getParamMap(conditions);
    assertEquals(params.size(), 1);
    assertEquals(params.get("std_id"), 1L);
  }

  @Test
  public void testToQueryString() {
    List<Condition> conditions = CollectUtils.newArrayList();
    conditions.add(new Condition("user.id=:user_id", 1L));
    conditions.add(new Condition("user.name=:std_name", "name"));
    assertEquals("(user.id=:user_id) and (user.name=:std_name)", ConditionUtils.toQueryString(conditions));
  }

}
