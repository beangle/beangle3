/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import org.testng.annotations.Test;

@Test
public class ActionTest {

  public void testGetNamespaceAndName() throws Exception {
    Action action = new Action("method");
    Action controllerAction = new Action("/home/person", "search");

    assertNull(action.getNamespace());
    assertEquals(controllerAction.getNamespace(), "/home");
  }

  public void testUri() {
    Action action = new Action("first/second", "add", "&id=1");
    assertEquals(action.getUri(), "/first/second!add?id=1");

    Action action1 = new Action("first/second", "add", "&id=1");
    assertEquals("/first/second!add?id=1", action1.getUri());
  }

  public void testParse() {
    String[] data = Action.parse("/security/user!search.action");
    assertEquals(data[0], "/security");
    assertEquals(data[1], "user");

    data = Action.parse("/security.action");
    assertEquals(data[0], "/");
    assertEquals(data[1], "security");

    data = Action.parse("security.action");
    assertEquals(data[0], "/");
    assertEquals(data[1], "security");

    data = Action.parse("security/user.action");
    assertEquals(data[0], "/security");
    assertEquals(data[1], "user");

  }
}
