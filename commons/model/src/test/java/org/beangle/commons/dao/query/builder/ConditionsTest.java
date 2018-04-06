/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.dao.query.builder;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test
public class ConditionsTest {

  @BeforeClass
  protected void setUp() throws Exception {

  }

  public void testGetParamMap() throws Exception {
    List<Condition> conditions = CollectUtils.newArrayList();
    conditions.add(new Condition("std.id=:std_id", 1L));
    Map<String, Object> params = Conditions.getParamMap(conditions);
    assertEquals(params.size(), 1);
    assertEquals(params.get("std_id"), 1L);
  }

  public void testToQueryString() {
    List<Condition> conditions = CollectUtils.newArrayList();
    conditions.add(new Condition("user.id=:user_id", 1L));
    conditions.add(new Condition("user.name=:std_name", "name"));
    assertEquals(Conditions.toQueryString(conditions), "(user.id=:user_id) and (user.name=:std_name)");
  }

  public void testConcat() {
    List<Condition> conditions = CollectUtils.newArrayList();
    conditions.add(new Condition("user.id=:user_id", 1L));
    conditions.add(new Condition("user.name=:std_name", "name"));
    Condition rs = Conditions.or(conditions);
    assertEquals(rs.getContent(), "((user.id=:user_id) or (user.name=:std_name))");
  }

}
