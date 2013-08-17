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
package org.beangle.commons.entity.metadata;

/**
 * <p>
 * IdentifierType class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class IdentifierType extends AbstractType {

  private Class<?> clazz;

  /**
   * <p>
   * Constructor for IdentifierType.
   * </p>
   */
  public IdentifierType() {
    super();
  }

  /**
   * <p>
   * Constructor for IdentifierType.
   * </p>
   * 
   * @param clazz a {@link java.lang.Class} object.
   */
  public IdentifierType(Class<?> clazz) {
    super();
    this.clazz = clazz;
  }

  /**
   * <p>
   * getName.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getName() {
    return clazz.toString();
  }

  /**
   * <p>
   * getReturnedClass.
   * </p>
   * 
   * @return a {@link java.lang.Class} object.
   */
  public Class<?> getReturnedClass() {
    return clazz;
  }

  /**
   * <p>
   * isCollectionType.
   * </p>
   * 
   * @return a boolean.
   */
  public boolean isCollectionType() {
    return false;
  }

  /**
   * <p>
   * isComponentType.
   * </p>
   * 
   * @return a boolean.
   */
  public boolean isComponentType() {
    return false;
  }

  /**
   * <p>
   * isEntityType.
   * </p>
   * 
   * @return a boolean.
   */
  public boolean isEntityType() {
    return false;
  }

}
