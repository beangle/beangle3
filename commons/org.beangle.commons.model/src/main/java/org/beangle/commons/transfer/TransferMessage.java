/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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
package org.beangle.commons.transfer;

import java.util.ArrayList;
import java.util.List;

import org.beangle.commons.lang.Objects;

/**
 * 转换消息
 * 
 * @author chaostone
 * @version $Id: $
 */
public class TransferMessage {

  /** Constant <code>ERROR_ATTRS="error.transfer.attrs"</code> */
  public static final String ERROR_ATTRS = "error.transfer.attrs";

  /** Constant <code>ERROR_ATTRS_EXPORT="error.transfer.attrs.export"</code> */
  public static final String ERROR_ATTRS_EXPORT = "error.transfer.attrs.export";

  /**
   * 转换数据的序号
   */
  int index;

  /**
   * 消息内容
   */
  String message;

  /**
   * 消息中使用的对应值
   */
  List<Object> values = new ArrayList<Object>();

  /**
   * <p>
   * Constructor for TransferMessage.
   * </p>
   * 
   * @param index a int.
   * @param message a {@link java.lang.String} object.
   * @param value a {@link java.lang.Object} object.
   */
  public TransferMessage(int index, String message, Object value) {
    this.index = index;
    this.message = message;
    this.values.add(value);
  }

  /**
   * <p>
   * Getter for the field <code>index</code>.
   * </p>
   * 
   * @return a int.
   */
  public int getIndex() {
    return index;
  }

  /**
   * <p>
   * Setter for the field <code>index</code>.
   * </p>
   * 
   * @param index a int.
   */
  public void setIndex(int index) {
    this.index = index;
  }

  /**
   * <p>
   * Getter for the field <code>message</code>.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getMessage() {
    return message;
  }

  /**
   * <p>
   * Setter for the field <code>message</code>.
   * </p>
   * 
   * @param message a {@link java.lang.String} object.
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * <p>
   * Getter for the field <code>values</code>.
   * </p>
   * 
   * @return a {@link java.util.List} object.
   */
  public List<Object> getValues() {
    return values;
  }

  /**
   * <p>
   * Setter for the field <code>values</code>.
   * </p>
   * 
   * @param values a {@link java.util.List} object.
   */
  public void setValues(List<Object> values) {
    this.values = values;
  }

  /**
   * <p>
   * toString.
   * </p>
   * 
   * @see java.lang.Object#toString()
   * @return a {@link java.lang.String} object.
   */
  public String toString() {
    return Objects.toStringBuilder(this).add("index", this.index).add("message", this.message)
        .add("values", this.values).toString();
  }

}
