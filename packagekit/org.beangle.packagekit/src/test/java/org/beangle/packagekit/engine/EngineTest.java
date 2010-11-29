/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.packagekit.engine;

import org.beangle.packagekit.Registry;
import org.beangle.packagekit.Resource;
import org.beangle.packagekit.internal.SimpleRegistry;
import org.beangle.packagekit.maven.MavenResolver;
import org.beangle.packagekit.maven.MavenUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class EngineTest {

	Engine engine;
	Registry registry;

	@BeforeMethod
	public void setUp() {
		engine = new Engine();
		registry = new SimpleRegistry();
		engine.setRegistry(registry);
		engine.setResolver(new MavenResolver());
	}

	public void testInstall() {
		Resource resource1 = MavenUtils.build("beangle-packagekit-1.0.0");
		Resource resource2 = MavenUtils.build("beangle-packagekit-webapp-1.0.0");
		engine.addResource(resource1, UpdateAction.INSTALL);
		engine.addResource(resource2, UpdateAction.INSTALL);
		engine.run();
	}

	public void testRemove() {
		Resource resource1 = MavenUtils.build("beangle-packagekit-1.0.0");
		engine.addResource(resource1, UpdateAction.REMOVE);
		engine.run();
	}

	public void testUpdate() {
		Resource resource2 = MavenUtils.build("beangle-packagekit-webapp-1.1.0");
		engine.addResource(resource2, UpdateAction.UPDATE);
		engine.run();
	}
}
