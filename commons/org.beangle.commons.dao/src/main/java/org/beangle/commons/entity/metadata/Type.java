/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
  public boolean isCollectionType();

  /**
   * Is this type a component type. If so, the implementation must be castable
   * to <tt>AbstractComponentType</tt>. A component type may own collections
   * or associations and hence must provide certain extra functionality.
   * 
   * @return boolean
   */
  public boolean isComponentType();

  /**
   * Is this type an entity type?
   * 
   * @return boolean
   */
  public boolean isEntityType();

  /**
   * <p>
   * getPropertyType.
   * </p>
   * 
   * @param property a {@link java.lang.String} object.
   * @return a {@link org.beangle.commons.entity.metadata.Type} object.
   */
  public Type getPropertyType(String property);

  /**
   * <p>
   * getName.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getName();

  /**
   * <p>
   * getReturnedClass.
   * </p>
   * 
   * @return a {@link java.lang.Class} object.
   */
  public Class<?> getReturnedClass();

  /**
   * <p>
   * newInstance.
   * </p>
   * 
   * @return a {@link java.lang.Object} object.
   */
  public Object newInstance();
}
