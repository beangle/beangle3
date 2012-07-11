/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.i18n;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Message class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class Message {

  private String key;

  private List<Object> params = new ArrayList<Object>();

  /**
   * <p>
   * Constructor for Message.
   * </p>
   * 
   * @param key a {@link java.lang.String} object.
   * @param params a {@link java.util.List} object.
   */
  public Message(String key, List<Object> params) {
    super();
    this.key = key;
    this.params = params;
  }

  /**
   * <p>
   * Constructor for Message.
   * </p>
   * 
   * @param key a {@link java.lang.String} object.
   * @param objs an array of {@link java.lang.Object} objects.
   */
  public Message(String key, Object[] objs) {
    super();
    this.key = key;
    if (null != objs) {
      for (int i = 0; i < objs.length; i++) {
        this.params.add(objs[i]);
      }
    }

  }

  /**
   * <p>
   * Constructor for Message.
   * </p>
   * 
   * @param key a {@link java.lang.String} object.
   */
  public Message(String key) {
    super();
    this.key = key;
  }

  /**
   * <p>
   * Getter for the field <code>key</code>.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getKey() {
    return key;
  }

  /**
   * <p>
   * Getter for the field <code>params</code>.
   * </p>
   * 
   * @return a {@link java.util.List} object.
   */
  public List<Object> getParams() {
    return params;
  }

  /**
   * <p>
   * Setter for the field <code>key</code>.
   * </p>
   * 
   * @param key a {@link java.lang.String} object.
   */
  public void setKey(String key) {
    this.key = key;
  }

  /**
   * <p>
   * Setter for the field <code>params</code>.
   * </p>
   * 
   * @param params a {@link java.util.List} object.
   */
  public void setParams(List<Object> params) {
    this.params = params;
  }

}
