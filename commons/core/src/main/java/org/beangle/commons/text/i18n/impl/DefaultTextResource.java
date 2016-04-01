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
package org.beangle.commons.text.i18n.impl;

import java.util.Locale;

import org.beangle.commons.text.i18n.TextBundle;
import org.beangle.commons.text.i18n.TextBundleRegistry;
import org.beangle.commons.text.i18n.TextFormater;
import org.beangle.commons.text.i18n.TextResource;

/**
 * <p>
 * Abstract BundleTextResource class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class DefaultTextResource implements TextResource {

  protected final TextBundleRegistry registry;

  protected final Locale locale;

  protected final TextFormater formater;

  protected boolean keyAsDefault = true;

  public DefaultTextResource(Locale locale, TextBundleRegistry registry, TextFormater formater) {
    super();
    this.registry = registry;
    this.locale = locale;
    this.formater = formater;
  }

  /**
   * <p>
   * getText.
   * </p>
   * 
   * @param key a {@link java.lang.String} object.
   * @param defaultValue a {@link java.lang.String} object.
   * @param args an array of {@link java.lang.Object} objects.
   * @return a {@link java.lang.String} object.
   */
  public String getText(String key, String defaultValue, Object... args) {
    String text = doGetText(key);
    if (null == text) text = (null == defaultValue && keyAsDefault) ? key : defaultValue;
    if (null != text && args.length > 0) return formater.format(text, locale, args);
    return text;
  }

  public String getText(String key) {
    String msg = doGetText(key);
    return (null == msg && keyAsDefault) ? key : msg;
  }

  protected String doGetText(String key) {
    for (TextBundle bundle : registry.getBundles(locale)) {
      String msg = bundle.getText(key);
      if (null != msg) return msg;
    }
    return null;
  }

  public boolean isKeyAsDefault() {
    return keyAsDefault;
  }

  public void setKeyAsDefault(boolean keyAsDefault) {
    this.keyAsDefault = keyAsDefault;
  }

  public Locale getLocale() {
    return locale;
  }

}
