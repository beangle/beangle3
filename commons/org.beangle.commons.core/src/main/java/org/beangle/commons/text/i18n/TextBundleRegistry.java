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
package org.beangle.commons.text.i18n;

import java.util.List;
import java.util.Locale;

import org.beangle.commons.lang.Option;

/**
 * TextBundleRegistry
 * 
 * @author chaostone
 * @since 3.0.0
 */
public interface TextBundleRegistry {

  /**
   * Load and cache bundle
   * 
   * @param locale
   * @param bundleName
   * @return Option.None when not found
   */
  Option<TextBundle> load(Locale locale, String bundleName);

  /**
   * List locale bundles
   * 
   * @return empty list when not found
   */
  List<TextBundle> getBundles(Locale locale);

  /**
   * Load and cache default bundles
   * 
   * @param bundleNames
   */
  void addDefaults(String... bundleNames);

  /**
   * Get default locale message
   * 
   * @param key
   * @param locale
   * @return null when not found
   */
  String getDefaultText(String key, Locale locale);

  /**
   * Whether cache bundles
   * 
   * @param reloadBundles
   */
  void setReloadBundles(boolean reloadBundles);

}
