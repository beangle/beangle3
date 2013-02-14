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
package org.beangle.commons.text.i18n.impl;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.beangle.commons.text.i18n.TextBundle;

/**
 * @author chaostone
 * @since 3.0.0
 */
public class DefaultTextBundle implements TextBundle {

  final Map<String, String> texts;

  final Locale locale;

  final String resource;

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public DefaultTextBundle(Locale locale, String resource, Properties properties) {
    super();
    this.locale = locale;
    this.resource = resource;
    texts = new HashMap(properties);
  }

  public DefaultTextBundle(Locale locale, String resource, Map<String, String> texts) {
    super();
    this.locale = locale;
    this.resource = resource;
    this.texts = texts;
  }

  public String getText(String key) {
    return texts.get(key);
  }

  public Locale getLocale() {
    return locale;
  }

  public String getResource() {
    return resource;
  }

  @Override
  public String toString() {
    return resource;
  }

}
