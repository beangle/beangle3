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
package org.beangle.commons.web.url;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class UrlRenderTest {

  public void testRender() {
    UriRender render = new UriRender("/demo", ".html");
    String uri = "/demo/security/user!list.html";
    String result = render.render(uri, "user");
    Assert.assertEquals(result, "/demo/security/user.html");

    result = render.render(uri, "user!search");
    Assert.assertEquals(result, "/demo/security/user!search.html");

    result = render.render(uri, "!save");
    Assert.assertEquals(result, "/demo/security/user!save.html");

    result = render.render(uri, "user!search?id=1");
    Assert.assertEquals(result, "/demo/security/user!search.html?id=1");

    result = render.render(uri, "/database/query!history?id=1");
    Assert.assertEquals(result, "/demo/database/query!history.html?id=1");

  }

  public void testRenderEmptyContext() {
    UriRender render = new UriRender(null,null);
    String uri = "/user!list";
    String result = render.render("/security/user", "/security/role");
    Assert.assertEquals(result, "/security/role");

    result = render.render(uri, "user!search");
    Assert.assertEquals(result, "/user!search");

    result = render.render(uri, "!save");
    Assert.assertEquals(result, "/user!save");

    result = render.render(uri, "user!search?id=1");
    Assert.assertEquals(result, "/user!search?id=1");

    result = render.render(uri, "/database/query!history?id=1");
    Assert.assertEquals(result, "/database/query!history?id=1");

  }
}
