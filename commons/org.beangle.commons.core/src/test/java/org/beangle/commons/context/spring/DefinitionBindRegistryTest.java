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
package org.beangle.commons.context.spring;

import java.util.List;

import org.beangle.commons.context.inject.BindRegistry;
import org.beangle.commons.context.testbean.ProxyFactoryBean;
import org.beangle.commons.context.testbean.TestDao;
import org.beangle.commons.context.testbean.TestEntityDao;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class DefinitionBindRegistryTest {

  public void testGet() {
    DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
    XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
    reader.loadBeanDefinitions(new ClassPathResource(
        "/org/beangle/commons/context/spring/context-registry.xml"));

    BindRegistry registry = new SpringBindRegistry(factory);
    List<String> names = registry.getBeanNames(TestDao.class);
    Assert.assertNotNull(names);
    Assert.assertTrue(names.size() == 1);

    Assert.assertTrue(names.contains("entityDao"));

    Assert.assertTrue(registry.contains("entityDao"));

    Assert.assertTrue(TestEntityDao.class.equals(registry.getBeanType("entityDao")));

    names = registry.getBeanNames(ProxyFactoryBean.class);
    Assert.assertNotNull(names);
    Assert.assertTrue(names.size() == 1);

    Assert.assertTrue(names.contains("&entityDao"));
  }
}
