/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.i18n;

import java.util.Locale;

/**
 * TextBundle
 * 
 * @author chaostone
 * @since 3.0.0
 */
public interface TextBundle {

  /**
   * Gets a message based on a message key, or null if no message is found.
   * 
   * @param key the resource bundle key that is to be searched for
   * @return null if none is found.
   */
  String getText(String key);

  /**
   * Returns the locale of this resource bundle.
   * 
   * @return the locale of this resource bundle
   */
  Locale getLocale();
  
  /**
   * bundle resource path
   * @return
   */
  String getResource();

}
