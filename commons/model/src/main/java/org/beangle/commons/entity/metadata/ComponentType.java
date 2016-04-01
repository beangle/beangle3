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
package org.beangle.commons.entity.metadata;

import java.util.Map;

import org.beangle.commons.bean.PropertyUtils;
import org.beangle.commons.collection.CollectUtils;

/**
 * <p>
 * ComponentType class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class ComponentType extends AbstractType {

  private Class<?> componentClass;

  private final Map<String, Type> propertyTypes = CollectUtils.newHashMap();

  /**
   * <p>
   * isComponentType.
   * </p>
   * 
   * @return a boolean.
   */
  public boolean isComponentType() {
    return true;
  }

  /**
   * <p>
   * getName.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getName() {
    return componentClass.toString();
  }

  /**
   * <p>
   * getReturnedClass.
   * </p>
   * 
   * @return a {@link java.lang.Class} object.
   */
  public Class<?> getReturnedClass() {
    return componentClass;
  }

  /**
   * <p>
   * Constructor for ComponentType.
   * </p>
   */
  public ComponentType() {
    super();
  }

  /**
   * <p>
   * Constructor for ComponentType.
   * </p>
   * 
   * @param componentClass a {@link java.lang.Class} object.
   */
  public ComponentType(Class<?> componentClass) {
    super();
    this.componentClass = componentClass;
  }

  /**
   * {@inheritDoc} Get the type of a particular (named) property
   */
  public Type getPropertyType(String propertyName) {
    Type type = (Type) propertyTypes.get(propertyName);
    if (null == type) {
      Class<?> propertyType = PropertyUtils.getPropertyType(componentClass, propertyName);
      if (null != propertyType) { return new IdentifierType(propertyType); }
    }
    return type;
  }

  /**
   * <p>
   * Getter for the field <code>propertyTypes</code>.
   * </p>
   * 
   * @return a {@link java.util.Map} object.
   */
  public Map<String, Type> getPropertyTypes() {
    return propertyTypes;
  }

}
