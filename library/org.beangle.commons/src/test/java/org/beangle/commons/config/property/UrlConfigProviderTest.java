/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.config.property;

import static org.testng.Assert.assertEquals;

import java.util.Properties;

import org.beangle.commons.config.ConfigResource;
import org.testng.annotations.Test;

@Test
public class UrlConfigProviderTest {

	public void testConfig() {
		PropertyConfig config = new PropertyConfigBean();
		UrlPropertyConfigProvider provider = new UrlPropertyConfigProvider();
		ConfigResource location = new ConfigResource();
		// META-INF/system.properties
		location.setGlobal(UrlPropertyConfigProvider.class
				.getResource("/system-default.properties"));
		location.setUser(UrlPropertyConfigProvider.class.getResource("/system.properties"));
		provider.setResource(location);
		Properties properties = provider.getConfig();
		config.set(properties);
		assertEquals(Integer.valueOf(1), config.get(Integer.class, "testInt"));
		assertEquals("beangle.org", config.get("system.vendor"));
		assertEquals("http://localhost", config.get("system.url"));
	}
}
