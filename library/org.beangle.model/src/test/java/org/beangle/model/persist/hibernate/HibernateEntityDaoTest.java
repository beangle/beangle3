/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.persist.hibernate;

import org.beangle.model.example.Employer;
import org.beangle.model.persist.EntityDao;
import org.testng.annotations.Test;

@Test
public class HibernateEntityDaoTest extends ConfigurationTest {

	/**
	 * Test Hql with parameters array;
	 */
	public void testQueryHqlWithParamArray() {
		EntityDao entityDao = (EntityDao) applicationContext.getBean("entityDao");
		entityDao.searchHQLQuery("from Employer where name.firstName=? and contractInfo.add1=?",
				new Object[] { "john", "najing street" });
	}

	/**
	 * Test bulk update
	 */
	public void testUpdate() {
		EntityDao entityDao = (EntityDao) applicationContext.getBean("entityDao");
		entityDao.update(Employer.class, "id", new Long[] { 1L },
				new String[] { "name.firstName" }, new Object[] { "me" });
	}
}
