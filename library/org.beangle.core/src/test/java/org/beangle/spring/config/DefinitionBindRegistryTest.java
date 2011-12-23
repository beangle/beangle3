/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.spring.config;

import java.util.List;

import org.beangle.spring.config.processor.BindRegistry;
import org.beangle.spring.config.processor.DefinitionBindRegistry;
import org.beangle.spring.testbean.TestDao;
import org.beangle.spring.testbean.TestEntityDao;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.interceptor.TransactionProxyFactoryBean;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class DefinitionBindRegistryTest {

	public void testGet() {
		XmlBeanFactory factory = new XmlBeanFactory(new ClassPathResource("/org/beangle/spring/config/context-registry.xml"));
		BindRegistry registry = new DefinitionBindRegistry(factory);
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
