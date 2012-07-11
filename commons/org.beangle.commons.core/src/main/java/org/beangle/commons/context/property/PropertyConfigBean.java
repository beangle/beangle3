/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.context.property;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.beanutils.ConvertUtils;
import org.beangle.commons.lang.Numbers;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;

/**
 * 系统配置
 * 
 * @author chaostone
 * @version $Id: $
 */
public class PropertyConfigBean implements PropertyConfig {

  private Map<String, Object> properties = new HashMap<String, Object>();

  private List<PropertyConfigListener> listeners = CollectUtils.newArrayList();

  /** {@inheritDoc} */
  public Object get(String name) {
    return (Object) properties.get(name);
  }

  /** {@inheritDoc} */
  public void set(String name, Object value) {
    properties.put(name, value);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  public <T> T get(Class<T> clazz, String name) {
    Object value = get(name);
    if (null == value) {
      return null;
    } else {
      return (T) ConvertUtils.convert(value, clazz);
    }
  }

  /**
   * <p>
   * getInt.
   * </p>
   * 
   * @param name a {@link java.lang.String} object.
   * @return a int.
   */
  public int getInt(String name) {
    return Numbers.toInt((String) get(name));
  }

  /**
   * <p>
   * getBool.
   * </p>
   * 
   * @param name a {@link java.lang.String} object.
   * @return a boolean.
   */
  public boolean getBool(String name) {
    return Boolean.valueOf((String) get(name)).booleanValue();
  }

  /** {@inheritDoc} */
  public void set(Properties newer) {
    for (Object key : newer.keySet()) {
      this.properties.put(key.toString(), newer.get(key));
    }
  }

  /** {@inheritDoc} */
  public void addConfigListener(PropertyConfigListener listener) {
    listeners.add(listener);
  }

  /** {@inheritDoc} */
  public void removeConfigListener(PropertyConfigListener listener) {
    listeners.remove(listener);
  }

  /**
   * <p>
   * multicast.
   * </p>
   */
  public void multicast() {
    PropertyConfigEvent e = new PropertyConfigEvent(this);
    for (PropertyConfigListener listener : listeners) {
      listener.onConfigEvent(e);
    }
  }

  /**
   * <p>
   * toString.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String toString() {
    StringBuilder sb = new StringBuilder("DefaultSystemConfig[");
    List<String> props = new ArrayList<String>(properties.keySet());
    Collections.sort(props);
    int maxlength = 0;
    for (String property : props) {
      if (property.length() > maxlength) {
        maxlength = property.length();
      }
    }
    for (String property : props) {
      sb.append('\n').append(property);
      sb.append(Strings.repeat(" ", maxlength - property.length()));
      sb.append('=').append(properties.get(property));
    }
    sb.append("\n]");
    return sb.toString();
  }

  /**
   * <p>
   * getNames.
   * </p>
   * 
   * @return a {@link java.util.Set} object.
   */
  public Set<String> getNames() {
    return CollectUtils.newHashSet(properties.keySet());
  }
}
