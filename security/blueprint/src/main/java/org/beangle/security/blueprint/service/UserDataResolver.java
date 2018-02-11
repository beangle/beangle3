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
package org.beangle.security.blueprint.service;

import java.util.Collection;
import java.util.List;

import org.beangle.security.blueprint.Dimension;

public interface UserDataResolver {

  /**
   * Marshal list of objects to text format
   * 
   * @param field
   * @param items
   */
  String marshal(Dimension field, Collection<?> items);

  /**
   * Convert text to list of objects
   * 
   * @param <T>
   * @param field
   * @param text
   */
  <T> List<T> unmarshal(Dimension field, String text);
}
