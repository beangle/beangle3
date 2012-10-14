/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class DefaultActionUrlRenderTest {

  public void testRender() {
    DefaultActionUrlRender render = new DefaultActionUrlRender();
    render.setSuffix("action,,");
    Assert.assertEquals(render.render("/", "login"), "/login.action");
  }
}
