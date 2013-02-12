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
package org.beangle.inject.spring.config;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.beangle.commons.lang.time.Stopwatch;
import org.beangle.inject.spring.bean.ResourcesConsumer;
import org.beangle.inject.spring.bean.SomeAction;
import org.beangle.inject.spring.bean.TestService;
import org.beangle.inject.spring.bean.UserDaoProvider;
import org.beangle.inject.spring.bean.UserLdapProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.Test;

/**
 * Test Bean definition in Java config
 * 
 * @author chaostone
 */
@Test
public class AutoConfigProcessorTest {

  private static final Logger logger = LoggerFactory.getLogger(AutoConfigProcessorTest.class);

  /**
   * Test get normal and factory bean.
   */
  public void testGet() {
    Stopwatch watch = new Stopwatch().start();
    ApplicationContext factory = new ClassPathXmlApplicationContext(
        "/org/beangle/inject/spring/context-auto.xml");
    testBean(factory);
    testFactoryBean(factory);
    logger.debug("config  context-auto completed using " + watch);
  }

  public void testAdvance() {
    Stopwatch watch = new Stopwatch().start();
    ApplicationContext factory = new ClassPathXmlApplicationContext(
        "/org/beangle/inject/spring/context-auto.xml");
    // test Alias
    assertNotNull(factory.getBean(TestService.class.getName()));
    ResourcesConsumer consumer = (ResourcesConsumer) factory.getBean(ResourcesConsumer.class.getName());
    assertNotNull(consumer);
    assertNotNull(consumer.getResources());
    logger.debug("config  advance context-auto completed using " + watch);
  }

  private void testFactoryBean(ApplicationContext factory) {
    TestService testService = factory.getBean("testService", TestService.class);
    assertNotNull(testService);
    assertNotNull(testService.getTestDao());
  }

  private void testBean(ApplicationContext factory) {
    // two user provider
    UserDaoProvider daoProvider = (UserDaoProvider) factory.getBean("userDaoProvider");
    assertNotNull(daoProvider);

    UserDaoProvider daoProvider2 = (UserDaoProvider) factory.getBean("userDaoProvider");
    assertNotNull(daoProvider2);

    assertTrue(daoProvider2 == daoProvider);

    UserLdapProvider ldapProvider = (UserLdapProvider) factory.getBean("userLdapProvider");
    assertNotNull(ldapProvider);

    // userService
    SomeAction action = (SomeAction) factory.getBean(SomeAction.class.getName());

    assertNotNull(action);

    assertTrue(action.hasDaoProvider());
    assertTrue(action.hasLdapProvider());

    assertTrue(action.getUserDaoProvider() == daoProvider);
    assertTrue(action.getLdapProvider() == ldapProvider);

    SomeAction action2 = (SomeAction) factory.getBean(SomeAction.class.getName());

    assertTrue(action2 != action);
  }
}
