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
