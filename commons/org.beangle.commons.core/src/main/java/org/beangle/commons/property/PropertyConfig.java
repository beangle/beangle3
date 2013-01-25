/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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
package org.beangle.commons.property;

import java.util.Properties;
import java.util.Set;

/**
 * 系统属性
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface PropertyConfig {

  /**
   * <p>
   * get.
   * </p>
   * 
   * @param name a {@link java.lang.String} object.
   * @return a {@link java.lang.Object} object.
   */
  Object get(String name);

  /**
   * <p>
   * set.
   * </p>
   * 
   * @param name a {@link java.lang.String} object.
   * @param value a {@link java.lang.Object} object.
   */
  void set(String name, Object value);

  /**
   * <p>
   * get.
   * </p>
   * 
   * @param clazz a {@link java.lang.Class} object.
   * @param name a {@link java.lang.String} object.
   * @param <T> a T object.
   * @return a T object.
   */
  <T> T get(Class<T> clazz, String name);

  /**
   * <p>
   * add.
   * </p>
   * 
   * @param properties a {@link java.util.Properties} object.
   */
  void add(Properties properties);

  /**
   * <p>
   * getNames.
   * </p>
   * 
   * @return a {@link java.util.Set} object.
   */
  Set<String> getNames();

  /**
   * <p>
   * addConfigListener.
   * </p>
   * 
   * @param listener a {@link org.beangle.commons.property.PropertyConfigListener} object.
   */
  void addListener(PropertyConfigListener listener);

  /**
   * <p>
   * removeConfigListener.
   * </p>
   * 
   * @param listener a {@link org.beangle.commons.property.PropertyConfigListener} object.
   */
  void removeListener(PropertyConfigListener listener);

  /**
   * <p>
   * multicast.
   * </p>
   */
  void multicast();

  /**
   * <p>
   * reload.
   * </p>
   */
  void reload();

  /**
   * <p>
   * addConfigProvider.
   * </p>
   * 
   * @param provider a {@link org.beangle.commons.property.PropertyConfig.Provider} object.
   */
  void addProvider(Provider provider);

  interface Provider {
    Properties getConfig();
  }
}
