/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.i18n.impl;

import java.util.Locale;

import org.beangle.commons.i18n.TextBundle;
import org.beangle.commons.i18n.TextBundleRegistry;
import org.beangle.commons.i18n.TextFormater;
import org.beangle.commons.i18n.TextResource;

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
    String text = getText(key, locale);
    if (null == text) text = (null == defaultValue && keyAsDefault) ? key : defaultValue;
    if (null != text && args.length > 0) return formater.format(text, locale, args);
    return text;
  }

  public String getText(String key) {
    String msg = getText(key, locale);
    return (null == msg && keyAsDefault) ? key : msg;
  }

  protected String getText(String key, Locale locale) {
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
