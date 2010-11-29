/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.packagekit.maven;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.beangle.packagekit.Resolver;
import org.beangle.packagekit.Resource;
import org.beangle.packagekit.internal.SimpleResource;
import org.testng.annotations.Test;

@Test
public class MavenResolverTest {

	public void testResolve() {
		Resource resource = new SimpleResource("beangle-packagekit-1.0.0");
		Resolver resolver = new MavenResolver();
		List<Resource> requires = resolver.getRequires(resource);
		assertNotNull(requires);
		assertTrue(requires.size() == 1);
		Resource require = requires.get(0);
		assertEquals(require.getId(), "beangle-commons-1.0.0");
	}
}
