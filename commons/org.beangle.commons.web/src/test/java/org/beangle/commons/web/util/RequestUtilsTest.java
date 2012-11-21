/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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
package org.beangle.commons.web.util;

import org.springframework.mock.web.MockHttpServletRequest;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class RequestUtilsTest {

  public void testGetServletPath() {
    MockHttpServletRequest request = new MockHttpServletRequest(null, "/");
    request.setContextPath("/");
    Assert.assertEquals(RequestUtils.getServletPath(request), "");
    request.setContextPath("/");
    request.setRequestURI("/demo");
    Assert.assertEquals(RequestUtils.getServletPath(request), "/demo");

    request.setContextPath("");
    request.setRequestURI("/demo");
    Assert.assertEquals(RequestUtils.getServletPath(request), "/demo");
  }
}
