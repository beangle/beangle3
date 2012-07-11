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
