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
package org.beangle.commons.web.util;

import javax.servlet.http.HttpServletRequest;

import org.testng.Assert;
import org.testng.annotations.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Test
public class RequestUtilsTest {

  public void testGetServletPath() {
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getContextPath()).thenReturn("/");
    when(request.getRequestURI()).thenReturn("/");
    Assert.assertEquals(RequestUtils.getServletPath(request), "");

    request = mock(HttpServletRequest.class);
    when(request.getContextPath()).thenReturn("/");
    when(request.getRequestURI()).thenReturn("/demo");
    Assert.assertEquals(RequestUtils.getServletPath(request), "/demo");

    request = mock(HttpServletRequest.class);
    when(request.getContextPath()).thenReturn("");
    when(request.getRequestURI()).thenReturn("/demo");
    Assert.assertEquals(RequestUtils.getServletPath(request), "/demo");
  }
}
