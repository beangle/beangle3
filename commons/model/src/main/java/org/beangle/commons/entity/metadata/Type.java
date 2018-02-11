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
package org.beangle.commons.entity.metadata;

/**
 * <p>
 * Type interface.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface Type {
  /**
   * Is this type a collection type.
   * 
   * @return a boolean.
   */
  boolean isCollectionType();

  /**
   * Is this type a component type. If so, the implementation must be castable
   * to <tt>AbstractComponentType</tt>. A component type may own collections
   * or associations and hence must provide certain extra functionality.
   * 
   * @return boolean
   */
  boolean isComponentType();

  /**
   * Is this type an entity type?
   * 
   * @return boolean
   */
  boolean isEntityType();

  /**
   * <p>
   * getPropertyType.
   * </p>
   * 
   * @param property a {@link java.lang.String} object.
   * @return a {@link org.beangle.commons.entity.metadata.Type} object.
   */
  Type getPropertyType(String property);

  /**
   * <p>
   * getName.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  String getName();

  /**
   * <p>
   * getReturnedClass.
   * </p>
   * 
   * @return a {@link java.lang.Class} object.
   */
  Class<?> getReturnedClass();

  /**
   * <p>
   * newInstance.
   * </p>
   * 
   * @return a {@link java.lang.Object} object.
   */
  Object newInstance();
}
