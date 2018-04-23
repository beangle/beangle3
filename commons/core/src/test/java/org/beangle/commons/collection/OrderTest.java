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
package org.beangle.commons.collection;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.Test;

public class OrderTest {
  @Test
  public void testToString1() throws Exception {
    List<Order> sorts = Order.parse(null);
    if (sorts.isEmpty()) {
      sorts.add(new Order(" teachPlan.grade desc "));
      sorts.add(new Order(" teachPlan.major.code "));
      // sorts.add(new Order("teachPlan.stdType"));
    }
    assertEquals(Order.toSortString(sorts), "order by teachPlan.grade desc,teachPlan.major.code");
  }

  @Test
  public void testToString() throws Exception {
    List<Order> sorts = CollectUtils.newArrayList();
    sorts.add(new Order("id", false));
    sorts.add(Order.asc("name"));
    assertEquals(Order.toSortString(sorts), "order by id desc,name");
  }

  public void testParserOrder() throws Exception {
    List<Order> orders = Order.parse("std.code asc");
    for (final Order order : orders) {
      assertTrue(order.isAscending());
      assertEquals(order.getProperty(), "std.code");
    }
  }

  @Test
  public void testParserMutiOrder() throws Exception {
    List<Order> sorts = Order
        .parse("activity.time.year desc,activity.time.validWeeksNum,activity.time.weekId desc");
    assertEquals(sorts.size(), 3);
    Order order = (Order) sorts.get(0);
    assertEquals(order.getProperty(), "activity.time.year");
    assertFalse(order.isAscending());
    order = (Order) sorts.get(1);
    assertEquals(order.getProperty(), "activity.time.validWeeksNum");
    assertTrue(order.isAscending());
    order = (Order) sorts.get(2);
    assertEquals(order.getProperty(), "activity.time.weekId");
    assertFalse(order.isAscending());
  }

  @Test
  public void testParserComplexOrder() {
    List<Order> sorts = Order.parse("(case when ware.price is null then 0 else ware.price end) desc");
    assertEquals(sorts.size(), 1);
    Order order = (Order) sorts.get(0);
    assertEquals(order.getProperty(), "(case when ware.price is null then 0 else ware.price end)");
    assertFalse(order.isAscending());
  }
}
