/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.entity.metadata;

/**
 * 对象和类型
 * 
 * @author chaostone
 * @version $Id: $
 */
public class ObjectAndType {

  private Object obj;
  private Type type;

  /**
   * <p>
   * Constructor for ObjectAndType.
   * </p>
   * 
   * @param obj a {@link java.lang.Object} object.
   * @param type a {@link org.beangle.commons.entity.metadata.Type} object.
   */
  public ObjectAndType(Object obj, Type type) {
    super();
    this.obj = obj;
    this.type = type;
  }

  /**
   * <p>
   * Getter for the field <code>obj</code>.
   * </p>
   * 
   * @return a {@link java.lang.Object} object.
   */
  public Object getObj() {
    return obj;
  }

  /**
   * <p>
   * Setter for the field <code>obj</code>.
   * </p>
   * 
   * @param obj a {@link java.lang.Object} object.
   */
  public void setObj(Object obj) {
    this.obj = obj;
  }

  /**
   * <p>
   * Getter for the field <code>type</code>.
   * </p>
   * 
   * @return a {@link org.beangle.commons.entity.metadata.Type} object.
   */
  public Type getType() {
    return type;
  }

  /**
   * <p>
   * Setter for the field <code>type</code>.
   * </p>
   * 
   * @param type a {@link org.beangle.commons.entity.metadata.Type} object.
   */
  public void setType(Type type) {
    this.type = type;
  }

}
