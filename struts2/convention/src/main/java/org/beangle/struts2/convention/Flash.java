/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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
package org.beangle.struts2.convention;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;

public class Flash implements Map<Object, Object>, Serializable {

  private static final long serialVersionUID = -5292283953338410228L;
  public static final String MESSAGES = "messages";

  /**
   * current request
   */
  public final Map<Object, Object> now = CollectUtils.newHashMap();

  /**
   * next request
   */
  public final Map<Object, Object> next = CollectUtils.newHashMap();

  /**
   * return now and session saved
   */
  public Set<Object> keySet() {
    return now.keySet();
  }

  /**
   * return now and session saved value
   * 
   * @param key
   */
  public Object get(Object key) {
    return now.get(key);
  }

  /**
   * put value to next
   * 
   * @param key
   * @param value
   */
  public Object put(Object key, Object value) {
    return next.put(key, value);
  }

  public void putAll(Map<? extends Object, ? extends Object> values) {
    next.putAll(values);
  }

  void keep(String key) {
    next.put(key, now.get(key));
  }

  void keep() {
    next.putAll(now);
  }

  public void nextToNow() {
    now.clear();
    now.putAll(next);
    next.clear();
  }

  public void clear() {
    now.clear();
  }

  public boolean containsKey(Object key) {
    return now.containsKey(key);
  }

  public boolean containsValue(Object value) {
    return now.containsValue(value);
  }

  public Set<Map.Entry<Object, Object>> entrySet() {
    return now.entrySet();
  }

  public boolean isEmpty() {
    return now.isEmpty();
  }

  public Object remove(Object key) {
    return now.remove(key);
  }

  public int size() {
    return now.size();
  }

  public Collection<Object> values() {
    return now.values();
  }

  /**
   * 添加消息到下一次请求
   * 
   * @param message
   */
  public void addMessage(String message) {
    ActionMessages messages = (ActionMessages) next.get(Flash.MESSAGES);
    if (null == messages) {
      messages = new ActionMessages();
      put(Flash.MESSAGES, messages);
    }
    messages.getMessages().add(message);
  }

  /**
   * 添加错误消息到下一次请求
   * 
   * @param error
   */
  public void addError(String error) {
    ActionMessages messages = (ActionMessages) next.get(Flash.MESSAGES);
    if (null == messages) {
      messages = new ActionMessages();
      put(Flash.MESSAGES, messages);
    }
    messages.getErrors().add(error);
  }

  /**
   * 添加消息到本次请求
   * 
   * @param message
   */
  public void addMessageNow(String message) {
    ActionMessages messages = (ActionMessages) now.get(Flash.MESSAGES);
    if (null == messages) {
      messages = new ActionMessages();
      now.put(Flash.MESSAGES, messages);
    }
    messages.getMessages().add(message);
  }

  /**
   * 添加错误到本次请求
   * 
   * @param message
   */
  public void addErrorNow(String message) {
    ActionMessages messages = (ActionMessages) now.get(Flash.MESSAGES);
    if (null == messages) {
      messages = new ActionMessages();
      now.put(Flash.MESSAGES, messages);
    }
    messages.getErrors().add(message);
  }
}
