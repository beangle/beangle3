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
package org.beangle.commons.lang;

public final class Enums {

  /**
   * Returns an optional enum constant for the given type, using {@link Enum#valueOf}. If the
   * constant does not exist, {@link Option#none} is returned. A common use case is for parsing
   * user input or falling back to a default enum constant. For example,
   * {@code Enums.get(Country.class, countryInput).getOrElse(Country.DEFAULT);}
   * 
   * @since 3.1
   */
  public static <T extends Enum<T>> Option<T> get(Class<T> enumClass, String value) {
    try {
      return Option.some(Enum.valueOf(enumClass, value));
    } catch (IllegalArgumentException iae) {
      return Option.none();
    }
  }
}
