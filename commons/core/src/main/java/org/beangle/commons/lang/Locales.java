/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.lang;

import java.util.Locale;

/**
 * Locale Utility
 * @author chaostone
 * @since 3.3.6
 */
public final class Locales {

  /**
   * Builds a {@link java.util.Locale} from a String of the form en_US_foo into a Locale
   * with language "en", country "US" and variant "foo". This will parse the output of
   * {@link java.util.Locale#toString()}.
   *
   * @param localeStr The locale String to parse.
   * @param defaultLocale The locale to use if localeStr is <tt>null</tt>.
   */
  public static Locale toLocale(String localeStr) {
    if ((localeStr == null) || (localeStr.trim().length() == 0) || ("_".equals(localeStr))) return Locale
        .getDefault();

    int index = localeStr.indexOf('_');
    if (index < 0) return new Locale(localeStr);

    String language = localeStr.substring(0, index);
    if (index == localeStr.length()) return new Locale(language);

    localeStr = localeStr.substring(index + 1);
    index = localeStr.indexOf('_');
    if (index < 0) return new Locale(language, localeStr);

    String country = localeStr.substring(0, index);
    if (index == localeStr.length()) return new Locale(language, country);

    localeStr = localeStr.substring(index + 1);
    return new Locale(language, country, localeStr);
  }

}
