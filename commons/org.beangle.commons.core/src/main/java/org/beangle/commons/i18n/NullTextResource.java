/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.i18n;

import java.util.Locale;

/**
 * <p>
 * NullTextResource class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class NullTextResource extends AbstractTextResource {

  /**
   * <p>
   * getLocale.
   * </p>
   * 
   * @return a {@link java.util.Locale} object.
   */
  public Locale getLocale() {
    return null;
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
    return key;
  }

  /** {@inheritDoc} */
  public String getText(String key) {
    return key;
  }

  /** {@inheritDoc} */
  public void setLocale(Locale locale) {
  }

}
