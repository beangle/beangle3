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
package org.beangle.commons.orm.hibernate;

import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.orm.example.Employer;
import org.testng.annotations.Test;

@Test
public class HibernateEntityDaoTest extends ConfigurationTest {

  /**
   * Test Hql with parameters array;
   */
  public void testQueryHqlWithParamArray() {
    EntityDao entityDao = (EntityDao) applicationContext.getBean("entityDao");
    entityDao.search("from " + Employer.class.getName()
        + " as emp where emp.name.firstName = ?1 and emp.contractInfo.add1 = ?2 ", "john", "najing street");
  }

  /**
   * Test bulk update
   */
  public void testUpdate() {
    EntityDao entityDao = (EntityDao) applicationContext.getBean("entityDao");
    entityDao.update(Employer.class, "id", new Integer[] { 1 }, new String[] { "name.firstName" },
        new Object[] { "me" });
  }

  /**
   * Test Count
   */
  public void testCount() {
    EntityDao entityDao = (EntityDao) applicationContext.getBean("entityDao");
    entityDao.count(Employer.class, new String[] { "name.firstName" }, new Object[] { new String[] { "make",
        "john" } }, null);
  }

}
