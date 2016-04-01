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
package org.beangle.commons.entity.comment;

import java.util.Locale;

import org.beangle.commons.text.i18n.TextBundleRegistry;
import org.beangle.commons.text.i18n.TextFormater;
import org.beangle.commons.text.i18n.impl.DefaultTextBundleRegistry;
import org.beangle.commons.text.i18n.impl.DefaultTextFormater;
import org.beangle.commons.text.i18n.impl.HierarchicalTextResource;

public final class Messages {

  private final Locale locale;
  private final TextFormater format;
  private final TextBundleRegistry registry;

  private Messages(Locale locale, TextBundleRegistry registry, TextFormater format) {
    this.locale = locale;
    this.registry = registry;
    this.format = format;
    registry.setReloadBundles(false);
  }

  public static Messages build(Locale locale) {
    return new Messages(locale, new DefaultTextBundleRegistry(), new DefaultTextFormater());
  }

  public String get(Class<?> clazz, String key) {
    return new HierarchicalTextResource(clazz, locale, registry, format).getText(key);
  }

}
