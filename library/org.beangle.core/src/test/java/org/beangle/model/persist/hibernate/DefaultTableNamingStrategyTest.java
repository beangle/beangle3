/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.persist.hibernate;

import org.beangle.model.persist.DefaultTableNamingStrategy;
import org.beangle.spring.config.ConfigResource;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DefaultTableNamingStrategyTest {

	@Test
	public void testGetSchemaName() {
		DefaultTableNamingStrategy config = new DefaultTableNamingStrategy();
		ConfigResource resource = new ConfigResource();
		resource.setGlobal(DefaultTableNamingStrategyTest.class.getClassLoader().getResource(
				"META-INF/beangle/table.properties"));
		config.setResource(resource);
		Assert.assertEquals("security_online", config.getSchema("org.beangle.security.online.model"));
		Assert.assertEquals("sys_", config.getPrefix("org.beangle.security"));
	}

}
