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
package org.beangle.commons.transfer.exporter;

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;

/**
 * <p>
 * Context class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class Context {

  Map<String, Object> datas = CollectUtils.newHashMap();

  /** Constant <code>KEYS="keys"</code> */
  public static final String KEYS = "keys";
  /** Constant <code>TITLES="titles"</code> */
  public static final String TITLES = "titles";
  /** Constant <code>EXTRACTOR="extractor"</code> */
  public static final String EXTRACTOR = "extractor";

  /**
   * <p>
   * Getter for the field <code>datas</code>.
   * </p>
   * 
   * @return a {@link java.util.Map} object.
   */
  public Map<String, Object> getDatas() {
    return datas;
  }

  /**
   * <p>
   * Setter for the field <code>datas</code>.
   * </p>
   * 
   * @param datas a {@link java.util.Map} object.
   */
  public void setDatas(Map<String, Object> datas) {
    this.datas = datas;
  }

  /**
   * <p>
   * put.
   * </p>
   * 
   * @param key a {@link java.lang.String} object.
   * @param obj a {@link java.lang.Object} object.
   */
  public void put(String key, Object obj) {
    datas.put(key, obj);
  }

  /**
   * <p>
   * get.
   * </p>
   * 
   * @param key a {@link java.lang.String} object.
   * @return a {@link java.lang.Object} object.
   */
  public Object get(String key) {
    return datas.get(key);
  }

  /**
   * <p>
   * get.
   * </p>
   * 
   * @param key a {@link java.lang.String} object.
   * @param clazz a {@link java.lang.Class} object.
   * @param <T> a T object.
   * @return a T object.
   */
  @SuppressWarnings("unchecked")
  public <T> T get(String key, Class<T> clazz) {
    return (T) datas.get(key);
  }

}
