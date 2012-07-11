/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
