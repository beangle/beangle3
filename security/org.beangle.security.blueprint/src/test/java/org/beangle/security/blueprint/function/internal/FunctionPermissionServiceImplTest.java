/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.function.internal;

import static org.testng.Assert.assertEquals;

import org.beangle.security.blueprint.function.service.FunctionPermissionService;
import org.beangle.security.blueprint.function.service.internal.FunctionPermissionServiceImpl;
import org.testng.annotations.Test;
@Test
public class FunctionPermissionServiceImplTest {

  FunctionPermissionService functionPermissionService = new FunctionPermissionServiceImpl();

  public void testextractResource() throws Exception {
    assertEquals(functionPermissionService.extractResource("a.jsp"), "a");
    assertEquals(functionPermissionService.extractResource("/b.do"), "/b");
    assertEquals(functionPermissionService.extractResource("/c/d.action"), "/c/d");
    assertEquals(functionPermissionService.extractResource("c/d.action"), "c/d");
    assertEquals(functionPermissionService.extractResource("//c/d.action"), "//c/d");

    // assertEquals(authorityService.extractResource("  //c/d.action "), "c/d");
    // assertEquals(authorityService.extractResource("  c/d.action "), "c/d");

    assertEquals(functionPermissionService.extractResource("c/d!remove.action "), "c/d");
  }
}
