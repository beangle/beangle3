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
package org.beangle.commons.text.i18n;

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
