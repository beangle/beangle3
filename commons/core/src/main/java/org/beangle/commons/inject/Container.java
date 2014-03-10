/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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
package org.beangle.commons.inject;

import java.util.Map;
import java.util.Set;

import org.beangle.commons.lang.Option;

/**
 * Bean Container.
 * 
 * @author chaostone
 * @since 3.1.0
 */
public interface Container {

  /**
   * Return true if contains
   */
  boolean contains(Object key);

  /**
   * Return type of the given key.
   */
  Option<Class<?>> getType(Object key);

  /**
   * Return an instance
   */
  <T> Option<T> getBean(Object key);

  /**
   * Gets an instance of the given dependency
   */
  <T> Option<T> getBean(Object key,Class<T> type);

  /**
   * Gets an instance of the given dependency
   */
  <T> Option<T> getBean(Class<T> type);

  /**
   * Return beans of the given type
   */
  <T> Map<?, T> getBeans(Class<T> types);

  /**
   * Return bean keys
   */
  Set<?> keys();
}
