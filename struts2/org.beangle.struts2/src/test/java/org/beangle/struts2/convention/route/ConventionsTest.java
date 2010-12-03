/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.convention.route;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.beangle.struts2.convention.example.action.FirstAction;
import org.beangle.struts2.convention.example.action.anotherNested.ThirdAction;
import org.beangle.struts2.convention.example.action.nested.SecondAction;
import org.beangle.struts2.convention.route.impl.DefaultActionBuilder;
import org.beangle.struts2.convention.route.impl.ProfileServiceImpl;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ConventionsTest {

	ActionBuilder actionNameBuilder;
	ProfileService profileService;

	@BeforeClass
	public void init() {
		actionNameBuilder = new DefaultActionBuilder();
		profileService = new ProfileServiceImpl();
		actionNameBuilder.setProfileService(profileService);
	}

	@Test
	public void testGetActionName() throws Exception {
		assertEquals(actionNameBuilder.build(FirstAction.class.getName()).getUri(),
				"/first!index.html");
		assertEquals(actionNameBuilder.build(SecondAction.class.getName()).getUri(),
				"/second!index.action");
		assertEquals(actionNameBuilder.build(ThirdAction.class.getName()).getUri(),
				"/another-nested/third!index.action");
	}

	@Test
	public void testGetProfile() throws Exception {
		Profile profile = profileService.getProfile(FirstAction.class);
		assertNotNull(profile);
		assertEquals(profile.getActionPattern(), "org.beangle.struts2.convention.example.action");
		profile = profileService.getProfile(SecondAction.class);
		assertNotNull(profile);
		assertEquals(profile.getActionPattern(),
				"org.beangle.struts2.convention.example.action.nested");
		profile = profileService.getProfile(ThirdAction.class);
		assertNotNull(profile);
		assertEquals(profile.getActionPattern(),
				"org.beangle.struts2.convention.example.action.anotherNested");

		profile = profileService.getProfile("com.company.app.web.action.SomeAction");
		assertNotNull(profile);
		assertEquals("com.company.app.*web.action", profile.getActionPattern());
	}

	@Test
	public void testGetInfix() throws Exception {
		Profile profile = new Profile();
		profile.setActionPattern("org.beangle.struts2.convention.example.*.");
		profile.setActionSuffix("Action");
		assertEquals("action/anotherNested/third", profile.getInfix(ThirdAction.class.getName()));
	}

	@Test
	public void testGetSimpleName() {
		Profile profile = new Profile();
		profile.setActionSuffix("Action");
		assertEquals("org/beangle/struts2/convention/example/action/anotherNested/third",
				profile.getSimpleName(ThirdAction.class.getName()));

	}
}
