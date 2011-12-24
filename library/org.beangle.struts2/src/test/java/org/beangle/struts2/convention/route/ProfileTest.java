/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.convention.route;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

@Test
public class ProfileTest {

	@Test
	public void testExactlyMatchIndex() throws Exception {
		Profile profile = new Profile();
		profile.setActionPattern("org.beangle.example.action");
		assertTrue("org.beangle.example.action".length() - 1 == profile
				.getCtlMatchInfo("org.beangle.example.action").startIndex);
	}

	@Test
	public void testMatchIndex() throws Exception {
		Profile profile = new Profile();
		profile.setActionPattern("org.beangle.example.*.web.action");
		assertTrue("org.beangle.example.d.web.action".length() - 1 == profile
				.getCtlMatchInfo("org.beangle.example.d.web.action").startIndex);

		profile = new Profile();
		profile.setActionPattern("org.beangle.example.*.web.action.");
		assertTrue("org.beangle.example.ddd.aa.web.action.".length() - 1 == profile
				.getCtlMatchInfo("org.beangle.example.ddd.aa.web.action.AAAction").startIndex);
	}

	@Test
	public void testFailMatch() throws Exception {
		Profile profile = new Profile();
		profile.setActionPattern("org.beangle.example.*.web.action");
		assertTrue(-1 == profile.getCtlMatchInfo("org.beangle.example.d.dd").startIndex);

		profile = new Profile();
		profile.setActionPattern("org.beangle.example.*.web.action.");
		assertTrue("org.beangle.example.ddd.aa.web.action.".length() - 1 == profile
				.getCtlMatchInfo("org.beangle.example.ddd.aa.web.action.AAAction").startIndex);
	}

	@Test
	public void testPressMatch() throws Exception {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			Profile profile = new Profile();
			profile.setActionPattern("org.beangle.example.*.web.action");
			assertTrue(-1 == profile.getCtlMatchInfo("org.beangle.example.d.dd").startIndex);

			profile = new Profile();
			profile.setActionPattern("org.beangle.example.ddd.aa.web.action.");
			assertTrue("org.beangle.example.ddd.aa.web.action.".length() - 1 == profile
					.getCtlMatchInfo("org.beangle.example.ddd.aa.web.action.AAAction").startIndex);
		}
		System.out.println(System.currentTimeMillis() - start);
	}

	public void testInPackage() {
		assertTrue(Profile.isInPackage("org.beangle.*ems.database.internal*.action",
				"org.beangle.ems.database.internal.action"));
	}

}
