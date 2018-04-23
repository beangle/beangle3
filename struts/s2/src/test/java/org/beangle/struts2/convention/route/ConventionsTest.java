/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.struts2.convention.route;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.beangle.struts2.convention.example.action.ExampleAction;
import org.beangle.struts2.convention.example.action.FirstAction;
import org.beangle.struts2.convention.example.action.anotherNested.ThirdAction;
import org.beangle.struts2.convention.example.action.nested.SecondAction;
import org.beangle.struts2.convention.route.impl.DefaultActionBuilder;
import org.beangle.struts2.convention.route.impl.ProfileServiceImpl;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ConventionsTest {

  ActionBuilder actionBuilder;
  ProfileService profileService;

  @BeforeClass
  public void init() {
    actionBuilder = new DefaultActionBuilder();
    profileService = new ProfileServiceImpl();
    actionBuilder.setProfileService(profileService);
  }

  @Test
  public void testGetActionName() throws Exception {
    assertEquals(actionBuilder.build(FirstAction.class).getUri(), "/first!index.html");
    assertEquals(actionBuilder.build(SecondAction.class).getUri(), "/second!index.action");
    assertEquals(actionBuilder.build(ThirdAction.class).getUri(), "/another-nested/third!index.action");
    assertEquals(actionBuilder.build(ExampleAction.class).getUri(), "/annotation/define/url!index.html");
  }

  @Test
  public void testGetProfile() throws Exception {
    Profile profile = profileService.getProfile(FirstAction.class);
    assertNotNull(profile);
    assertEquals(profile.getActionPattern(), "org.beangle.struts2.convention.example.action");
    profile = profileService.getProfile(SecondAction.class);
    assertNotNull(profile);
    assertEquals(profile.getActionPattern(), "org.beangle.struts2.convention.example.action.nested");
    profile = profileService.getProfile(ThirdAction.class);
    assertNotNull(profile);
    assertEquals(profile.getActionPattern(), "org.beangle.struts2.convention.example.action.anotherNested");

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
  public void testGetFullPath() {
    Profile profile = new Profile();
    profile.setActionSuffix("Action");
    assertEquals("org/beangle/struts2/convention/example/action/anotherNested/third",
        profile.getFullPath(ThirdAction.class.getName()));

  }
}
