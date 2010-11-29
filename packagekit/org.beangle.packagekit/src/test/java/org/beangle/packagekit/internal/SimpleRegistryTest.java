/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.packagekit.internal;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.net.URL;
import java.util.List;

import org.beangle.packagekit.Resource;
import org.testng.annotations.Test;

@Test
public class SimpleRegistryTest {

	public void testLoad() {
		SimpleRegistry registry = new SimpleRegistry();
		URL url = SimpleRegistryTest.class.getResource("/registry.xml");
		assertNotNull(url);
		registry.load(url);
		List<Resource> resources = registry.getResources();
		assertNotNull(resources);
		assertEquals(resources.size(), 3);
		Resource resource = resources.get(0);
		assertEquals(resource.getId(), "beangle-packagekit-webapp-1.0.0");
	}
}
