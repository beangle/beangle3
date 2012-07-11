/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * <p>
 * BundleTextResource class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class BundleTextResource extends AbstractTextResource {

  private Locale locale;

  private ResourceBundle bundle;

  /**
   * <p>
   * Getter for the field <code>locale</code>.
   * </p>
   * 
   * @return a {@link java.util.Locale} object.
   */
  public Locale getLocale() {
    return locale;
  }

  /** {@inheritDoc} */
  public String getText(String key) {
    return bundle.getString(key);
  }

  /**
   * <p>
   * getText.
   * </p>
   * 
   * @param key a {@link java.lang.String} object.
   * @param args an array of {@link java.lang.Object} objects.
   * @return a {@link java.lang.String} object.
   */
  public String getText(String key, Object[] args) {
    String text = bundle.getString(key);
    MessageFormat format = new MessageFormat(text);
    format.setLocale(locale);
    format.applyPattern(text);
    return format.format(args);
  }

  /** {@inheritDoc} */
  public void setLocale(Locale locale) {
    this.locale = locale;
  }

  /**
   * <p>
   * Getter for the field <code>bundle</code>.
   * </p>
   * 
   * @return a {@link java.util.ResourceBundle} object.
   */
  public ResourceBundle getBundle() {
    return bundle;
  }

  /**
   * <p>
   * Setter for the field <code>bundle</code>.
   * </p>
   * 
   * @param bundle a {@link java.util.ResourceBundle} object.
   */
  public void setBundle(ResourceBundle bundle) {
    this.bundle = bundle;
  }

}
