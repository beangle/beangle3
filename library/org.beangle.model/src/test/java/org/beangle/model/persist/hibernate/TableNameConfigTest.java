/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.persist.hibernate;

import org.beangle.commons.config.ConfigResource;
import org.beangle.model.persist.hibernate.support.DefaultTableNameConfig;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TableNameConfigTest {

	@Test
	public void testGetSchemaName() {
		DefaultTableNameConfig config = new DefaultTableNameConfig();
		ConfigResource resource = new ConfigResource();
		resource.setGlobal(TableNameConfigTest.class.getClassLoader().getResource(
				"table.properties"));
		config.setResource(resource);
		Assert.assertEquals("security_online", config
				.getSchema("org.beangle.security.online.model"));
		Assert.assertEquals("sys_", config.getPrefix("org.beangle.security"));
	}

}
