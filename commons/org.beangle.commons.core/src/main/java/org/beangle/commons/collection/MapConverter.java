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
package org.beangle.commons.collection;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.ConvertUtilsBean;
import org.beangle.commons.lang.Strings;

/**
 * <p>
 * MapConverter class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class MapConverter {

  private ConvertUtilsBean convertUtils;

  /**
   * <p>
   * Constructor for MapConverter.
   * </p>
   */
  public MapConverter() {
    this(new ConvertUtilsBean());
  }

  /**
   * <p>
   * Constructor for MapConverter.
   * </p>
   * 
   * @param convertUtils a {@link org.apache.commons.beanutils.ConvertUtilsBean} object.
   */
  public MapConverter(ConvertUtilsBean convertUtils) {
    super();
    this.convertUtils = convertUtils;
  }

  /**
   * <p>
   * getAll.
   * </p>
   * 
   * @param data a {@link java.util.Map} object.
   * @param attr a {@link java.lang.String} object.
   * @return an array of {@link java.lang.Object} objects.
   */
  public Object[] getAll(Map<String, Object> data, String attr) {
    return (Object[]) data.get(attr);
  }

  /**
   * <p>
   * getAll.
   * </p>
   * 
   * @param data a {@link java.util.Map} object.
   * @param attr a {@link java.lang.String} object.
   * @param clazz a {@link java.lang.Class} object.
   * @param <T> a T object.
   * @return an array of T objects.
   */
  public <T> T[] getAll(Map<String, Object> data, String attr, Class<T> clazz) {
    return convert((Object[]) data.get(attr), clazz);
  }

  /**
   * get parameter named attr
   * 
   * @param attr a {@link java.lang.String} object.
   * @return single value or multivalue joined with comma
   * @param data a {@link java.util.Map} object.
   */
  public String getString(Map<String, Object> data, String attr) {
    Object value = data.get(attr);
    if (null == value) { return null; }
    if (!value.getClass().isArray()) { return value.toString(); }
    String[] values = (String[]) value;
    if (values.length == 1) {
      return values[0];
    } else {
      return Strings.join(values, ",");
    }
  }

  /**
   * get parameter named attr
   * 
   * @param data a {@link java.util.Map} object.
   * @param name a {@link java.lang.String} object.
   * @return a {@link java.lang.Object} object.
   */
  public Object get(Map<String, Object> data, String name) {
    Object value = data.get(name);
    if (null == value) return null;
    if (value.getClass().isArray()) {
      Object[] values = (Object[]) value;
      if (values.length == 1) { return values[0]; }
    }
    return value;
  }

  /**
   * <p>
   * convert.
   * </p>
   * 
   * @param value a {@link java.lang.Object} object.
   * @param clazz a {@link java.lang.Class} object.
   * @param <T> a T object.
   * @return a T object.
   */
  @SuppressWarnings("unchecked")
  public <T> T convert(Object value, Class<T> clazz) {
    if (null == value) return null;
    if (value instanceof String && Strings.isEmpty((String) value)) { return null; }
    if (value.getClass().isArray()) {
      Object[] values = (Object[]) value;
      if (values.length >= 1) {
        value = values[0];
      }
    }
    return (T) convertUtils.convert(value, clazz);
  }

  /**
   * <p>
   * convert.
   * </p>
   * 
   * @param datas an array of {@link java.lang.Object} objects.
   * @param clazz a {@link java.lang.Class} object.
   * @param <T> a T object.
   * @return an array of T objects.
   */
  public <T> T[] convert(Object[] datas, Class<T> clazz) {
    if (null == datas) { return null; }
    @SuppressWarnings("unchecked")
    T[] newDatas = (T[]) Array.newInstance(clazz, datas.length);
    for (int i = 0; i < datas.length; i++) {
      newDatas[i] = convert(datas[i], clazz);
    }
    return newDatas;
  }

  /**
   * <p>
   * get.
   * </p>
   * 
   * @param data a {@link java.util.Map} object.
   * @param name a {@link java.lang.String} object.
   * @param clazz a {@link java.lang.Class} object.
   * @param <T> a T object.
   * @return a T object.
   */
  public <T> T get(Map<String, Object> data, String name, Class<T> clazz) {
    return convert(get(data, name), clazz);
  }

  /**
   * <p>
   * getBoolean.
   * </p>
   * 
   * @param data a {@link java.util.Map} object.
   * @param name a {@link java.lang.String} object.
   * @return a {@link java.lang.Boolean} object.
   */
  public Boolean getBoolean(Map<String, Object> data, String name) {
    return get(data, name, Boolean.class);
  }

  /**
   * <p>
   * getBool.
   * </p>
   * 
   * @param data a {@link java.util.Map} object.
   * @param name a {@link java.lang.String} object.
   * @return a boolean.
   */
  public boolean getBool(Map<String, Object> data, String name) {
    Boolean value = getBoolean(data, name);
    return (null == value) ? false : value.booleanValue();
  }

  /**
   * <p>
   * getDate.
   * </p>
   * 
   * @param data a {@link java.util.Map} object.
   * @param name a {@link java.lang.String} object.
   * @return a {@link java.sql.Date} object.
   */
  public java.sql.Date getDate(Map<String, Object> data, String name) {
    return get(data, name, java.sql.Date.class);
  }

  /**
   * <p>
   * getDateTime.
   * </p>
   * 
   * @param data a {@link java.util.Map} object.
   * @param name a {@link java.lang.String} object.
   * @return a {@link java.util.Date} object.
   */
  public Date getDateTime(Map<String, Object> data, String name) {
    return get(data, name, Date.class);
  }

  /**
   * <p>
   * getFloat.
   * </p>
   * 
   * @param data a {@link java.util.Map} object.
   * @param name a {@link java.lang.String} object.
   * @return a {@link java.lang.Float} object.
   */
  public Float getFloat(Map<String, Object> data, String name) {
    return get(data, name, Float.class);
  }

  /**
   * <p>
   * getInteger.
   * </p>
   */
  public Integer getInteger(Map<String, Object> data, String name) {
    return get(data, name, Integer.class);
  }

  /**
   * Get Short.
   */
  public Short getShort(Map<String, Object> data, String name) {
    return get(data, name, Short.class);
  }

  /**
   * <p>
   * getLong.
   * </p>
   * 
   * @param data a {@link java.util.Map} object.
   * @param name a {@link java.lang.String} object.
   * @return a {@link java.lang.Long} object.
   */
  public Long getLong(Map<String, Object> data, String name) {
    return get(data, name, Long.class);
  }

  /**
   * 返回request中以prefix.开头的参数
   * 
   * @param prefix a {@link java.lang.String} object.
   * @param exclusiveAttrNames
   *          要排除的属性串
   * @param data a {@link java.util.Map} object.
   * @return a {@link java.util.Map} object.
   */
  public Map<String, Object> sub(Map<String, Object> data, String prefix, String exclusiveAttrNames) {
    return sub(data, prefix, exclusiveAttrNames, true);
  }

  /**
   * <p>
   * sub.
   * </p>
   * 
   * @param data a {@link java.util.Map} object.
   * @param prefix a {@link java.lang.String} object.
   * @return a {@link java.util.Map} object.
   */
  public Map<String, Object> sub(Map<String, Object> data, String prefix) {
    return sub(data, prefix, null, true);
  }

  /**
   * <p>
   * sub.
   * </p>
   * 
   * @param data a {@link java.util.Map} object.
   * @param prefix a {@link java.lang.String} object.
   * @param exclusiveAttrNames a {@link java.lang.String} object.
   * @param stripPrefix a boolean.
   * @return a {@link java.util.Map} object.
   */
  public Map<String, Object> sub(Map<String, Object> data, String prefix, String exclusiveAttrNames,
      boolean stripPrefix) {
    Set<String> excludes = CollectUtils.newHashSet();
    if (Strings.isNotEmpty(exclusiveAttrNames)) {
      String[] exclusiveAttrs = Strings.split(exclusiveAttrNames, ",");
      for (int i = 0; i < exclusiveAttrs.length; i++) {
        excludes.add(exclusiveAttrs[i]);
      }
    }
    Map<String, Object> newParams = CollectUtils.newHashMap();
    for (final Map.Entry<String, Object> entry : data.entrySet()) {
      final String attr = entry.getKey();
      if ((attr.indexOf(prefix + ".") == 0) && (!excludes.contains(attr))) {
        newParams.put((stripPrefix ? attr.substring(prefix.length() + 1) : attr), get(data, attr));
      }
    }
    return newParams;
  }

  /**
   * <p>
   * Getter for the field <code>convertUtils</code>.
   * </p>
   * 
   * @return a {@link org.apache.commons.beanutils.ConvertUtilsBean} object.
   */
  public ConvertUtilsBean getConvertUtils() {
    return convertUtils;
  }

  /**
   * <p>
   * Setter for the field <code>convertUtils</code>.
   * </p>
   * 
   * @param convertUtils a {@link org.apache.commons.beanutils.ConvertUtilsBean} object.
   */
  public void setConvertUtils(ConvertUtilsBean convertUtils) {
    this.convertUtils = convertUtils;
  }

}
