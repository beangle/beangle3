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

import java.util.List;

import org.beangle.security.blueprint.Dimension;

/**
 * @author chaostone
 * @version $Id: UserDataProvider.java Nov 9, 2010 7:18:38 PM chaostone $
 */
public interface UserDataProvider {

  /**
   * extract data from source
   *
   * @param <T>
   * @param field
   * @param source
   * @param keys
   */
  <T> List<T> getData(Dimension field, String source, Object... keys);

  /**
   * provider's unique name
   */
  String getName();
}
