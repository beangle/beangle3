/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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
package org.beangle.commons.config.property;

import java.util.*;

import org.beangle.commons.bean.Initializing;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Numbers;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.conversion.impl.DefaultConversion;

/**
 * 系统配置
 * 
 * @author chaostone
 * @version $Id: $
 */
public class MultiProviderPropertyConfig implements PropertyConfig, Initializing {

  private Map<String, Object> properties = new HashMap<String, Object>();

  private List<PropertyConfigListener> listeners = CollectUtils.newArrayList();

  private List<PropertyConfig.Provider> providers = new ArrayList<PropertyConfig.Provider>();

  public void init() throws Exception {
    reload();
  }

  /**
   * Get value according to name
   */
  public Object get(String name) {
    return (Object) properties.get(name);
  }

  /**
   * Insert or Update name's value
   */
  public void set(String name, Object value) {
    properties.put(name, value);
  }

  public <T> T get(Class<T> clazz, String name) {
    Object value = get(name);
    if (null == value) {
      return null;
    } else {
      return DefaultConversion.Instance.convert(value, clazz);
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
  public void add(Properties newer) {
    for (Object key : newer.keySet()) {
      this.properties.put(key.toString(), newer.get(key));
    }
  }

  /** {@inheritDoc} */
  public void addListener(PropertyConfigListener listener) {
    listeners.add(listener);
  }

  /** {@inheritDoc} */
  public void removeListener(PropertyConfigListener listener) {
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

  public void addProvider(PropertyConfig.Provider provider) {
    providers.add(provider);
  }

  /**
   * <p>
   * reload.
   * </p>
   */
  public synchronized void reload() {
    for (PropertyConfig.Provider provider : providers)
      add(provider.getConfig());
    multicast();
  }

  /**
   * <p>
   * Setter for the field <code>providers</code>.
   * </p>
   * 
   * @param providers a {@link java.util.List} object.
   */
  public void setProviders(List<PropertyConfig.Provider> providers) {
    this.providers = providers;
  }

}
