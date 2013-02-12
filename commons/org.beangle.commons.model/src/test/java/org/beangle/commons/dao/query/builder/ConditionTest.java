/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.Test;

@Test
public class ConditionTest {

  public void testGetNamedParams() {
    Condition condition = new Condition("std.id =:stAd_id1 and std.name like :name");
    List<String> names = condition.getParamNames();
    assertEquals(names.size(), 2);
    assertEquals(names.get(0), "stAd_id1");
    assertEquals(names.get(1), "name");
  }

  public void testVarArgs() {
    Condition c = new Condition("entity.code =:code  entity.id in (:ids)", "aa", new Long[] { 1L });
    assertEquals(2, ConditionUtils.getParamMap(c).size());

    Condition c1 = new Condition("entity.id in (:ids)", new Long[] { 1L, 2L });
    assertEquals(1, ConditionUtils.getParamMap(c1).size());
    assertTrue((ConditionUtils.getParamMap(c1).get("ids").getClass().isArray()));
  }

}
