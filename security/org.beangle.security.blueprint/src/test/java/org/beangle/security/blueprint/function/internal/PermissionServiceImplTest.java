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
package org.beangle.security.blueprint.function.internal;

import static org.testng.Assert.assertEquals;

import org.beangle.security.blueprint.function.service.FuncPermissionService;
import org.beangle.security.blueprint.function.service.internal.FuncPermissionServiceImpl;
import org.testng.annotations.Test;

@Test
public class PermissionServiceImplTest {

  FuncPermissionService permissionService = new FuncPermissionServiceImpl();

  public void testextractResource() throws Exception {
    assertEquals(permissionService.extractResource("a.jsp"), "a");
    assertEquals(permissionService.extractResource("/b.do"), "/b");
    assertEquals(permissionService.extractResource("/c/d.action"), "/c/d");
    assertEquals(permissionService.extractResource("c/d.action"), "c/d");
    assertEquals(permissionService.extractResource("//c/d.action"), "//c/d");

    // assertEquals(authorityService.extractResource("  //c/d.action "), "c/d");
    // assertEquals(authorityService.extractResource("  c/d.action "), "c/d");

    assertEquals(permissionService.extractResource("c/d!remove.action "), "c/d");
  }
}
