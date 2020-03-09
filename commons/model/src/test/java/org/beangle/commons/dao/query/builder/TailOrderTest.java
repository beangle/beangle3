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

import org.testng.annotations.Test;

@Test
public class TailOrderTest {
  public void testNormal() {
    OqlBuilder builder = OqlBuilder.from(this.getClass(), "aa");
    builder.orderBy("aa.name");
    builder.tailOrder("aa.id desc");
    assert(builder.build().getStatement().endsWith(",aa.id desc"));
  }
  public void testHasGroupBy() {
    OqlBuilder builder = OqlBuilder.from(this.getClass(), "aa");
    builder.orderBy("aa.name");
    builder.groupBy("aa.name");
    builder.tailOrder("aa.id desc");
    assert(!builder.build().getStatement().endsWith(",aa.id desc"));
  }
  public void testWithoutOrderAndGroupBy() {
    OqlBuilder builder = OqlBuilder.from(this.getClass(), "aa");
    builder.tailOrder("aa.id desc");
    assert(builder.build().getStatement().endsWith("order by aa.id desc"));
  }
  public void testWithoutOrderButHasGroupBy() {
    OqlBuilder builder = OqlBuilder.from(this.getClass(), "aa");
    builder.tailOrder("aa.id desc");
    builder.groupBy("aa.name");
    assert(!builder.build().getStatement().endsWith("aa.id desc"));
  }
}
