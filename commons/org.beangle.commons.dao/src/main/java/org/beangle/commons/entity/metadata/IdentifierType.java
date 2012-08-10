/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
