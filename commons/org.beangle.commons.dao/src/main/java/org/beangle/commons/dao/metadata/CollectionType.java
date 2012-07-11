/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.dao.metadata;

import java.lang.reflect.Array;

/**
 * <p>
 * CollectionType class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class CollectionType extends AbstractType {

  /**
   * <p>
   * isCollectionType.
   * </p>
   * 
   * @return a boolean.
   */
  public boolean isCollectionType() {
    return true;
  }

  private Class<?> collectionClass;

  private Type elementType;

  private Class<?> indexClass;

  private boolean array = false;;

  /**
   * <p>
   * getName.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getName() {
    StringBuilder buffer = new StringBuilder();
    if (null != collectionClass) {
      buffer.append(collectionClass.getName());
    }
    buffer.append(':');
    if (null != indexClass) {
      buffer.append(indexClass.getName());
    }
    buffer.append(':');
    buffer.append(elementType.getName());
    return buffer.toString();
  }

  /** {@inheritDoc} */
  public Type getPropertyType(String property) {
    return elementType;
  }

  /**
   * The collection element type
   * 
   * @return a {@link org.beangle.commons.dao.metadata.Type} object.
   */
  public Type getElementType() {
    return elementType;
  }

  /**
   * <p>
   * Setter for the field <code>elementType</code>.
   * </p>
   * 
   * @param elementType a {@link org.beangle.commons.dao.metadata.Type} object.
   */
  public void setElementType(Type elementType) {
    this.elementType = elementType;
  }

  /**
   * The collection index type (or null if the collection has no index)
   * 
   * @return a {@link java.lang.Class} object.
   */
  public Class<?> getIndexClass() {
    return indexClass;
  }

  /**
   * Is this collection indexed?
   * 
   * @return a boolean.
   */
  public boolean hasIndex() {
    return (null != indexClass) && (indexClass.equals(int.class));
  }

  /**
   * <p>
   * getReturnedClass.
   * </p>
   * 
   * @return a {@link java.lang.Class} object.
   */
  public Class<?> getReturnedClass() {
    return collectionClass;
  }

  /**
   * <p>
   * Getter for the field <code>collectionClass</code>.
   * </p>
   * 
   * @return a {@link java.lang.Class} object.
   */
  public Class<?> getCollectionClass() {
    return collectionClass;
  }

  /**
   * <p>
   * Setter for the field <code>collectionClass</code>.
   * </p>
   * 
   * @param collectionClass a {@link java.lang.Class} object.
   */
  public void setCollectionClass(Class<?> collectionClass) {
    this.collectionClass = collectionClass;
  }

  /**
   * Is the collection an array?
   * 
   * @return a boolean.
   */
  public boolean isArray() {
    return array;
  }

  /**
   * <p>
   * Setter for the field <code>array</code>.
   * </p>
   * 
   * @param isArray a boolean.
   */
  public void setArray(boolean isArray) {
    this.array = isArray;
  }

  /**
   * <p>
   * newInstance.
   * </p>
   * 
   * @return a {@link java.lang.Object} object.
   */
  public Object newInstance() {
    if (array) {
      return Array.newInstance(elementType.getReturnedClass(), 0);
    } else {
      return super.newInstance();
    }
  }
}
