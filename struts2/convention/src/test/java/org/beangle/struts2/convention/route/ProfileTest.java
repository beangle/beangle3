/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.struts2.convention.route;

import static org.testng.Assert.assertTrue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

@Test
public class ProfileTest {
  private static final Logger logger = LoggerFactory.getLogger(ProfileTest.class);

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
    logger.debug(String.valueOf(System.currentTimeMillis() - start));
  }

  public void testInPackage() {
    assertTrue(Profile.isInPackage("org.beangle.*ems.database.internal*.action",
        "org.beangle.ems.database.internal.action"));
  }

}
