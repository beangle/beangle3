/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.transfer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
    return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append("index", this.index)
        .append("message", this.message).append("values", this.values).toString();
  }

}
