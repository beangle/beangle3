/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.i18n.impl;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.beangle.commons.i18n.TextBundle;

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
