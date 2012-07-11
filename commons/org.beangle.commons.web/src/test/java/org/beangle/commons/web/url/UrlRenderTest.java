/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.web.url;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class UrlRenderTest {

  public void testRender() {
    UrlRender render = new UrlRender(".html");
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
    UrlRender render = new UrlRender();
    String uri = "/user!list";
    String result = render.render(uri, "user");
    Assert.assertEquals(result, "/user");

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
