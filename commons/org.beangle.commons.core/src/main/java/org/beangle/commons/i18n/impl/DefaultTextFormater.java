/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.i18n.impl;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.i18n.TextFormater;

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
