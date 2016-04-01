/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.beangle.inject.spring.bean.AdvancedUserLdapProvider;
import org.beangle.inject.spring.bean.UserDaoProvider;
import org.beangle.inject.spring.bean.UserLdapProvider;
import org.beangle.inject.spring.bean.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.Test;

@Test
public class ReconfigProcessorTest {

  public void testGetDefinition() {
    ApplicationContext factory = new ClassPathXmlApplicationContext(
        "/org/beangle/inject/spring/context.xml");
    // two user provider
    assertNotNull((UserDaoProvider) factory.getBean("userDaoProvider"));

    assertNotNull((UserLdapProvider) factory.getBean("userLdapProvider"));

    // userService
    UserService userService = (UserService) factory.getBean("userService");

    assertNotNull(userService);

    assertNotNull(userService.getSomeMap());

    assertEquals(userService.getProvider().getClass(), UserDaoProvider.class);

    // userLdapService
    UserService userLdapService = (UserService) factory.getBean("userLdapService");

    assertNotNull(userLdapService);

    assertEquals(UserLdapProvider.class, userLdapService.getProvider().getClass());
  }

  public void testOverride() {
    ApplicationContext factory = new ClassPathXmlApplicationContext(
        "/org/beangle/inject/spring/context-config.xml");
    // userService
    UserService userService = (UserService) factory.getBean("userService");

    assertNotNull(userService);

    // unmerged map
    assertNotNull(userService.getSomeMap());

    assertEquals(1, userService.getSomeMap().size());

    assertEquals(userService.getSomeMap().get("string"), "override string");

    // merged list
    assertEquals(userService.getSomeList().size(), 3);

    // change class
    UserLdapProvider ldapProvider = (UserLdapProvider) factory.getBean("userLdapProvider");
    assertTrue(ldapProvider instanceof AdvancedUserLdapProvider);

    UserService userLdapService = (UserService) factory.getBean("userLdapService");
    assertNotNull(userLdapService);

    assertEquals(AdvancedUserLdapProvider.class, userLdapService.getProvider().getClass());

  }

}
