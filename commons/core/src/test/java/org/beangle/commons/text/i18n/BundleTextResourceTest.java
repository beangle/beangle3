/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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
package org.beangle.commons.text.i18n;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Locale;

import org.beangle.commons.lang.Option;
import org.beangle.commons.text.i18n.impl.DefaultTextBundleRegistry;
import org.beangle.commons.text.i18n.impl.DefaultTextFormater;
import org.beangle.commons.text.i18n.impl.DefaultTextResource;
import org.testng.annotations.Test;

public class BundleTextResourceTest {

  @Test
  public void testGetText() {
    Locale locale = new Locale("zh", "CN");
    DefaultTextBundleRegistry registry = new DefaultTextBundleRegistry();
    Option<TextBundle> bundle = registry.load(locale, "message");
    Option<TextBundle> bundle2 = registry.load(locale, "message2");
    assertTrue(bundle.isDefined());
    assertTrue(bundle2.isDefined());
    assertEquals(bundle.get().getText("hello.world"), "你好");
    DefaultTextResource tr = new DefaultTextResource(locale, registry, new DefaultTextFormater());
    assertEquals(tr.getText("hello.world"), "你好");
    assertEquals(tr.getText("china"), "中国");
  }
}
