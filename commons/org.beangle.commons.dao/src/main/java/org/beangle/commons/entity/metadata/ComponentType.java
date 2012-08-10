/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.entity.metadata;

import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.beanutils.MethodUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;

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
      Method getMethod = MethodUtils.getAccessibleMethod(componentClass,
          "get" + Strings.capitalize(propertyName), (Class[]) null);
      if (null != getMethod) { return new IdentifierType(getMethod.getReturnType()); }
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
