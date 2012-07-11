/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.dao.metadata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Abstract AbstractType class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public abstract class AbstractType implements Type {

  /** Constant <code>logger</code> */
  protected static final Logger logger = LoggerFactory.getLogger(AbstractType.class);

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

  /** {@inheritDoc} */
  public Type getPropertyType(String property) {
    return null;
  }

  /** {@inheritDoc} */
  public boolean equals(Object obj) {
    if (!(obj instanceof Type)) { return false; }
    return getName().equals(((Type) obj).getName());
  }

  /**
   * <p>
   * hashCode.
   * </p>
   * 
   * @return a int.
   */
  public int hashCode() {
    return getName().hashCode();
  }

  /**
   * <p>
   * toString.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String toString() {
    return getName();
  }

  /**
   * <p>
   * getName.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public abstract String getName();

  /**
   * <p>
   * getReturnedClass.
   * </p>
   * 
   * @return a {@link java.lang.Class} object.
   */
  public abstract Class<?> getReturnedClass();

  /**
   * <p>
   * newInstance.
   * </p>
   * 
   * @return a {@link java.lang.Object} object.
   */
  public Object newInstance() {
    try {
      return getReturnedClass().newInstance();
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }
}
