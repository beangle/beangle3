/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.i18n;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.Locale;

import org.beangle.commons.i18n.impl.DefaultTextBundleRegistry;
import org.beangle.commons.i18n.impl.DefaultTextFormater;
import org.beangle.commons.i18n.impl.DefaultTextResource;
import org.testng.annotations.Test;

public class BundleTextResourceTest {

  @Test
  public void testGetText() {
    Locale locale = new Locale("zh", "CN");
    DefaultTextBundleRegistry registry = new DefaultTextBundleRegistry();
    TextBundle bundle = registry.load(locale, "message");
    TextBundle bundle2 = registry.load(locale, "message2");
    assertNotNull(bundle);
    assertNotNull(bundle2);
    assertEquals(bundle.getText("hello.world"), "你好");
    DefaultTextResource tr = new DefaultTextResource(locale, registry, new DefaultTextFormater());
    assertEquals(tr.getText("hello.world"), "你好");
    assertEquals(tr.getText("china"), "中国");
  }
}
