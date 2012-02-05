/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.context.spring;

import java.util.List;

import org.beangle.context.inject.BindRegistry;
import org.beangle.context.testbean.TestDao;
import org.beangle.context.testbean.TestEntityDao;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.interceptor.TransactionProxyFactoryBean;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class DefinitionBindRegistryTest {

	public void testGet() {
		XmlBeanFactory factory = new XmlBeanFactory(new ClassPathResource("/org/beangle/context/spring/context-registry.xml"));
		BindRegistry registry = new SpringBindRegistry(factory);
		List<String> names = registry.getBeanNames(TestDao.class);
		Assert.assertNotNull(names);
		Assert.assertTrue(names.size() == 1);

		Assert.assertTrue(names.contains("entityDao"));

		Assert.assertTrue(registry.contains("entityDao"));

		Assert.assertTrue(TestEntityDao.class.equals(registry.getBeanType("entityDao")));

		names = registry.getBeanNames(TransactionProxyFactoryBean.class);
		Assert.assertNotNull(names);
		Assert.assertTrue(names.size() == 1);

		Assert.assertTrue(names.contains("&entityDao"));
	}
}
