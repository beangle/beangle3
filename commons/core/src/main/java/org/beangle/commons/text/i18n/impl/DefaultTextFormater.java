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

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.text.i18n.TextFormater;

/**
 * DefaultTextFormater with cache
 * 
 * @author chaostone
 * @since 3.0.0
 */
public class DefaultTextFormater implements TextFormater {

  protected final Map<Locale, Map<String, MessageFormat>> caches = CollectUtils.newConcurrentHashMap();

  public String format(String text, Locale locale, Object... args) {
    Map<String, MessageFormat> localeCache = caches.get(locale);
    if (null == localeCache) {
      localeCache = CollectUtils.newConcurrentHashMap();
      caches.put(locale, localeCache);
    }
    MessageFormat format = localeCache.get(text);
    if (null == format) {
      format = new MessageFormat(text);
      localeCache.put(text, format);
    }
    return format.format(args);
  }

}
