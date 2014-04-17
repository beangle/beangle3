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
package org.beangle.commons.dao.query.builder;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import org.beangle.commons.dao.query.LimitQuery;
import org.beangle.commons.dao.query.TestModel;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class OqlBuilderTest {

  public void testToCountString1() throws Exception {
    OqlBuilder<TestModel> queryBuilder = OqlBuilder.from(TestModel.class, "model");
    LimitQuery<?> query = (LimitQuery<?>) queryBuilder.build();
    assertEquals("select count(*) from org.beangle.commons.dao.query.TestModel model", query.getCountQuery()
        .getStatement());
    queryBuilder.where(new Condition("name like :name", "testName"));
    query = (LimitQuery<?>) queryBuilder.build();
    assertTrue(query.getCountQuery().getStatement().endsWith("(name like :name)"));
  }

  public void testToCountString2() throws Exception {
    OqlBuilder<Object> queryBuilder = OqlBuilder
        .hql("from Ware where price is not null order by releaseDate desc "
            + " union all from Ware where price is null order by releaseDate desc");
    LimitQuery<Object> query = (LimitQuery<Object>) queryBuilder.build();
    assertNull(query.getCountQuery());
  }

  public void testToCountString3() throws Exception {
    OqlBuilder<Object> queryBuilder = OqlBuilder.from("Ware", "w");
    queryBuilder.select("(select count(*) from Product p where p.ware=w) pcount,w.id");
    queryBuilder.where("w.price is not null and exists (select * from Orders o where o.product=p)").orderBy(
        "w.releaseDate desc");
    LimitQuery<Object> query = (LimitQuery<Object>) queryBuilder.build();
    assertEquals(
        query.getStatement(),
        "select (select count(*) from Product p where p.ware=w) pcount,w.id from Ware w "
            + "where (w.price is not null and exists (select * from Orders o where o.product=p)) order by w.releaseDate desc");
    assertEquals(
        query.getCountQuery().getStatement(),
        "select count(*) from Ware w where (w.price is not null and exists (select * from Orders o where o.product=p))");
  }

  public void testToCountString4() throws Exception {
    OqlBuilder<Object> queryBuilder = OqlBuilder
        .hql("from Ware where price is not null order by releaseDate desc ");
    LimitQuery<Object> query = (LimitQuery<Object>) queryBuilder.build();
    assertEquals("select count(*) from Ware where price is not null", query.getCountQuery().getStatement());
  }

  public void testToCountString5() throws Exception {
    OqlBuilder<Object> queryBuilder = OqlBuilder.hql("from Ware where price is not null");
    LimitQuery<Object> query = (LimitQuery<Object>) queryBuilder.build();
    assertEquals("select count(*) from Ware where price is not null", query.getCountQuery().getStatement());
  }

  public void testHaving() throws Exception {
    OqlBuilder<Object> queryBuilder = OqlBuilder.from("SomeClass a ").groupBy("a.name").having("sum(a.id)>0");
    Assert.assertEquals(queryBuilder.build().getStatement(),
        "from SomeClass a  group by a.name having sum(a.id)>0");
  }

  public void testArrayCondition() throws Exception {
    Integer[] ids = new Integer[]{1,2,3};
    OqlBuilder query = OqlBuilder
        .from("SomeClass a")
        .where("a.id in (:ids)", ids);
    Assert.assertTrue(query.build().getParams().get("ids").getClass().equals(Integer[].class));
  }
  
}
