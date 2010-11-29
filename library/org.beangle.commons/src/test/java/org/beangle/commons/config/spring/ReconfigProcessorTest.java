/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.config.spring;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.Test;

@Test
public class ReconfigProcessorTest {

	public void testGetDefinition() {
		ApplicationContext factory = new ClassPathXmlApplicationContext("/context.xml");
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
		ApplicationContext factory = new ClassPathXmlApplicationContext("/context-config.xml");
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
