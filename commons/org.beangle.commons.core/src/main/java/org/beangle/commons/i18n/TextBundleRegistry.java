/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.i18n;

import java.util.List;
import java.util.Locale;

/**
 * TextBundleRegistry
 * 
 * @author chaostone
 * @since 3.0.0
 */
public interface TextBundleRegistry {

  /**
   * load and cache bundle
   * 
   * @param locale
   * @param bundleName
   * @return
   */
  TextBundle load(Locale locale, String bundleName);

  /**
   * list bundles
   * 
   * @return
   */
  List<TextBundle> getBundles(Locale locale);

  /**
   * Load and cache default bundles
   * 
   * @param bundleNames
   * @return
   */
  void addDefaults(String... bundleNames);

  /**
   * Get default locale message
   * 
   * @param key
   * @param locale
   * @return
   */
  String getDefaultText(String key, Locale locale);

  /**
   * Whether cache bundles
   * @param reloadBundles
   */
  void setReloadBundles(boolean reloadBundles);

}
